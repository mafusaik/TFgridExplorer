package by.homework.hlazarseni.tfgridexplorer.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import by.homework.hlazarseni.tfgridexplorer.domain.model.Node
import by.homework.hlazarseni.tfgridexplorer.databinding.ItemNodeBinding


class FavoritesNodeAdapter(
    context: Context,
    private val onNodeClicked: (Node) -> Unit

) : ListAdapter<Node, NodeViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeViewHolder {
        return NodeViewHolder(
            binding = ItemNodeBinding.inflate(layoutInflater, parent, false),
            onNodeClicked = onNodeClicked
        )
    }

    override fun onBindViewHolder(holder: NodeViewHolder, position: Int) {
        val currentNode = getItem(position)
        holder.bind(currentNode)
    }

    companion object {

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Node>() {
            override fun areItemsTheSame(
                oldItem: Node,
                newItem: Node
            ): Boolean {
                return oldItem.nodeId == newItem.nodeId && oldItem.farmId == newItem.farmId
            }

            override fun areContentsTheSame(
                oldItem: Node,
                newItem: Node
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}