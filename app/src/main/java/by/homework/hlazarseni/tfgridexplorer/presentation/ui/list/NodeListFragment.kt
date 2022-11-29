package by.homework.hlazarseni.tfgridexplorer.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.homework.hlazarseni.tfgridexplorer.*
import by.homework.hlazarseni.tfgridexplorer.data.mapper.toDomain
import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.adapter.NodeAdapter

import by.homework.hlazarseni.tfgridexplorer.databinding.NodeListFragmentBinding
import by.homework.hlazarseni.tfgridexplorer.domain.model.DetailNode
import by.homework.hlazarseni.tfgridexplorer.presentation.model.PagingData
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.favorites.FavoritesNodeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class NodeListFragment : Fragment() {
    private var _binding: NodeListFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val listViewModel by viewModel<NodeListViewModel>()
    private val favoritesViewModel by viewModel<FavoritesNodeViewModel>()

    private val adapter by lazy {
        NodeAdapter(
            context = requireContext(),
            onNodeClicked = {
                showConfirmationDialog(it)
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
                        listViewModel.onQueryChanged(query)
                        return true
                    }
                })

            toolbarList.setupWithNavController(findNavController())

            swipeRefreshList.setOnRefreshListener {
                listViewModel.onRefreshed()
            }

            val linearLayoutManager = LinearLayoutManager(requireContext())
            recyclerviewList.layoutManager = linearLayoutManager
            recyclerviewList.adapter = adapter
            recyclerviewList.addVerticalGaps()
            recyclerviewList.addPaginationListener(linearLayoutManager, R.string.count_to_load) {
                listViewModel.onLoadMore()
            }
        }
        listViewModel
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showConfirmationDialog(it: Node) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.choice_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.details)) { _, _ ->
                findNavController().navigate(
                    NodeListFragmentDirections.toNodeDetailFragment(
                        DetailNode(it.toDomain())
                    )
                )
            }
            .setPositiveButton(getString(R.string.favorites)) { _, _ ->
                favoritesViewModel.addingNode(it)
            }
            .show()
    }
}