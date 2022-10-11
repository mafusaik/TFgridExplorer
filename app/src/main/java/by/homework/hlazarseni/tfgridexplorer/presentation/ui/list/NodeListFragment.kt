package by.homework.hlazarseni.tfgridexplorer.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.homework.hlazarseni.tfgridexplorer.*
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.adapter.NodeAdapter

import by.homework.hlazarseni.tfgridexplorer.databinding.NodeListFragmentBinding
import by.homework.hlazarseni.tfgridexplorer.domain.model.DetailNode
import by.homework.hlazarseni.tfgridexplorer.domain.model.PagingData

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import kotlin.Exception


class NodeListFragment : Fragment() {
    private var _binding: NodeListFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by inject<NodeListViewModel>()

    private val adapter by lazy {
        NodeAdapter(
            context = requireContext(),
            onNodeClicked = {
                findNavController().navigate(
                    NodeListFragmentDirections.toNodeDetailFragment(
                        DetailNode(it)
                    )
                )
            },
            onRepeatClicked = {}
        )
    }

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

        with(binding) {

            toolbarList
                .menu
                .findItem(R.id.action_search)
                .actionView
                .let { it as SearchView }
                .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean = false

                    override fun onQueryTextChange(query: String): Boolean {
                        viewModel.onQueryChanged(query)
                        return true
                    }
                })

            toolbarList.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            swipeRefreshList.setOnRefreshListener {
                viewModel.onRefreshed()
            }
//            val linearLayoutManager = LinearLayoutManager(
//                view.context, LinearLayoutManager.VERTICAL, false
//            )
            val linearLayoutManager = LinearLayoutManager(requireContext())
            recyclerviewList.layoutManager = linearLayoutManager
            recyclerviewList.adapter = adapter
            recyclerviewList.addVerticalGaps()
            recyclerviewList.addPaginationListener(linearLayoutManager, COUNT_TO_LOAD) {
                viewModel.onLoadMore()
            }
        }
        try {
            viewModel
                .dataFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .onEach { binding.swipeRefreshList.isRefreshing = false }
                .onEach { listNode ->
                    adapter.submitList(
                        listNode.map {
                            PagingData.Item(it)
                        }
                            .plus(PagingData.Loading)
                    )
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        } catch (e: Exception) {
           // handleException(e)
            viewModel.onRefreshed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val COUNT_TO_LOAD = 10
        private const val ERROR_MESSAGE = "unknown error"
    }

    private fun handleException(e: Error) {
        Toast.makeText(requireContext(), e.message ?: ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
    }
}