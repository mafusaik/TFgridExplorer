package by.homework.hlazarseni.tfgridexplorer.presentation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.homework.hlazarseni.tfgridexplorer.R
import by.homework.hlazarseni.tfgridexplorer.addVerticalGaps
import by.homework.hlazarseni.tfgridexplorer.databinding.FavoritesNodeFragmentBinding
import by.homework.hlazarseni.tfgridexplorer.domain.model.DetailNode
import by.homework.hlazarseni.tfgridexplorer.domain.model.Node
import by.homework.hlazarseni.tfgridexplorer.domain.model.PagingData
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.adapter.NodeAdapter
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.list.NodeListFragmentDirections
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class FavoritesNodeFragment: Fragment() {

    private var _binding: FavoritesNodeFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

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
        return FavoritesNodeFragmentBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val linearLayoutManager = LinearLayoutManager(
                view.context, LinearLayoutManager.VERTICAL, false
            )

            swipeRefreshFavorites.setOnRefreshListener {
               //  viewModel.onRefreshed()
                swipeRefreshFavorites.isRefreshing = false
            }

            recyclerviewFavorites.adapter = adapter
            recyclerviewFavorites.layoutManager = linearLayoutManager
            recyclerviewFavorites.addVerticalGaps()

            val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val currentNode = adapter.currentList[position]
                    adapter.notifyItemInserted(position)
                    showConfirmationDeleteDialog(view, currentNode,position)

                }
            }
            ItemTouchHelper(itemTouchHelperCallback).apply {
                attachToRecyclerView(binding.recyclerviewFavorites)
            }
        }
      //  updateList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showConfirmationDeleteDialog(view: View, node: PagingData<Node>,position:Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ ->

                adapter.notifyItemChanged(position)

            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
//                ViewModel.deleteNode(node)
//                updateList()
//                Snackbar.make(
//                    view,
//                    getString(R.string.REMOVE_MESSAGE),
//                    Snackbar.LENGTH_SHORT
//                ).show()
            }
            .show()
    }
}