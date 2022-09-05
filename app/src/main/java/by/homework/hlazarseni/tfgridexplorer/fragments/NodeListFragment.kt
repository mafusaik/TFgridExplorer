package by.homework.hlazarseni.tfgridexplorer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.homework.hlazarseni.tfgridexplorer.*
import by.homework.hlazarseni.tfgridexplorer.databinding.NodeListFragmentBinding
import by.homework.hlazarseni.tfgridexplorer.entity.PagingData
import by.homework.hlazarseni.tfgridexplorer.services.GridProxyService
import retrofit2.*


class NodeListFragment : Fragment() {
    private var _binding: NodeListFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val currentNodes = mutableListOf<Node>()

    private val adapter by lazy {
        NodeAdapter(
            context = requireContext(),
            onNodeClicked = {
                findNavController().navigate(
                    NodeListFragmentDirections.toNodeDetailFragment(
                        it.uptime,
                        it.total_resources.cru,
                        it.used_resources.cru,
                        it.total_resources.sru,
                        it.used_resources.sru,
                        it.total_resources.hru,
                        it.used_resources.hru,
                        it.total_resources.mru,
                        it.used_resources.mru,
                        it.nodeId,
                        it.farmId
                    )
                )
            },
            onRepeatClicked = {}
        )
    }

    private var isLoading = false
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return NodeListFragmentBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        executeRequest()

        with(binding) {

            toolbarList
                .menu
                .findItem(R.id.action_search)
                .actionView
                .let { it as SearchView }
                .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(query: String): Boolean {
                        val list = currentNodes.filter { it.nodeId == (query) }.map {
                            PagingData.Item(it)
                        }
                        adapter.submitList(list)
                        return true
                    }
                })

            toolbarList.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            swipeRefreshList.setOnRefreshListener {
                adapter.submitList(emptyList())
                currentPage = 1

                executeRequest {
                    swipeRefreshList.isRefreshing = false
                }
            }
            val linearLayoutManager = LinearLayoutManager(
                view.context, LinearLayoutManager.VERTICAL, false
            )
            recyclerviewList.adapter = adapter
            recyclerviewList.layoutManager = linearLayoutManager
            recyclerviewList.addVerticalGaps()
            recyclerviewList.addPaginationListener(linearLayoutManager, COUNT_TO_LOAD) {
                executeRequest()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun executeRequest(
        onRequestFinished: () -> Unit = {}
    ) {
        if (isLoading) return
        isLoading = true

        val finishRequest = {
            isLoading = false
            onRequestFinished()
        }

        val size = currentPage * PAGE
        GridProxyService.api
            .getNodes(size, currentPage)
            .enqueue(object : Callback<List<Node>> {
                override fun onResponse(
                    call: Call<List<Node>>,
                    response: Response<List<Node>>
                ) {
                    if (response.isSuccessful) {
                        val newList = adapter.currentList
                            .dropLastWhile { it == PagingData.Loading }
                            .plus(response.body()?.map { PagingData.Item(it) }.orEmpty())
                            .plus(PagingData.Loading)
                        adapter.submitList(newList)
                        currentPage++
                    } else {
                        handleException(HttpException(response))
                    }
                    finishRequest()
                }

                override fun onFailure(call: Call<List<Node>>, t: Throwable) {
                    if (!call.isCanceled) {
                        handleException(t)
                    }
                    finishRequest()
                }
            })
    }

    companion object {
        private const val PAGE = 50
        private const val COUNT_TO_LOAD = 15
        private const val ERROR_MESSAGE = "unknown error"
    }

    private fun handleException(e: Throwable) {
        Toast.makeText(requireContext(), e.message ?: ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
    }
}

//    private fun loadData(){
//        if (isLoading) return
//        isLoading = true
//
//        load(lastNode, PAGE){items ->
//            lastNode = items.last()
//            val recentItems = adapter.currentList.filterIsInstance<PagingData.Item<Node>>()
//            val newItems = recentItems + items.map { PagingData.Item.(it) } + PagingData.Loading
//            adapter.submitList(newItems)
//            isLoading = false
//        }
//    }