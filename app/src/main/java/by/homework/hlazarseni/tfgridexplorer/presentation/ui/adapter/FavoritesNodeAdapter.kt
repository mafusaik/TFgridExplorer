package by.homework.hlazarseni.tfgridexplorer.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import by.homework.hlazarseni.tfgridexplorer.databinding.ItemNodeBinding


class FavoritesNodeAdapter(
    context: Context,
    private val onNodeClicked: (Node) -> Unit

) : ListAdapter<Node, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NodeViewHolder(
            binding = ItemNodeBinding.inflate(layoutInflater, parent, false),
            onNodeClicked = onNodeClicked
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentNode = getItem(position)
        holder.itemView.setOnClickListener {
            onNodeClicked(currentNode)
        }
        checkNotNull(holder as NodeViewHolder) { "incorrect viewholder $currentNode" }
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