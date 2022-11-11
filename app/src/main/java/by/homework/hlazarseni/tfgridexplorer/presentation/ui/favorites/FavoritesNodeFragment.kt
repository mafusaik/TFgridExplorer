package by.homework.hlazarseni.tfgridexplorer.presentation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.homework.hlazarseni.tfgridexplorer.R
import by.homework.hlazarseni.tfgridexplorer.addVerticalGaps
import by.homework.hlazarseni.tfgridexplorer.databinding.FavoritesNodeFragmentBinding
import by.homework.hlazarseni.tfgridexplorer.domain.model.DetailNode
import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.adapter.FavoritesNodeAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesNodeFragment : Fragment() {

    private var _binding: FavoritesNodeFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val favoritesViewModel by viewModel<FavoritesNodeViewModel>()

    private val adapter by lazy {
        FavoritesNodeAdapter(
            context = requireContext(),
            onNodeClicked = {
                findNavController().navigate(
                    FavoritesNodeFragmentDirections.toNodeDetailFragment(
                        DetailNode(it)
                    )
                )
            }
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

            toolbarList.setupWithNavController(findNavController())

            swipeRefreshFavorites.setOnRefreshListener {
                updateFavoritesList()
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
                    val position = viewHolder.absoluteAdapterPosition
                    val currentNode = adapter.currentList[position]
                    showConfirmationDeleteDialog(view, currentNode, position)

                }
            }
            ItemTouchHelper(itemTouchHelperCallback).apply {
                attachToRecyclerView(binding.recyclerviewFavorites)
            }
        }
        updateFavoritesList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateFavoritesList() {
        favoritesViewModel.allFavoritesNode.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
    }

    private fun showConfirmationDeleteDialog(view: View, node: Node, position: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                adapter.notifyItemChanged(position)
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                favoritesViewModel.deletingNode(node)
                updateFavoritesList()
                Snackbar.make(
                    view,
                    getString(R.string.remove_message),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            .show()
    }
}