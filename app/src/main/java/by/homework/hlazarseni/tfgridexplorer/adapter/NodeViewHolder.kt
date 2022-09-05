package by.homework.hlazarseni.tfgridexplorer.adapter

import androidx.recyclerview.widget.RecyclerView
import by.homework.hlazarseni.tfgridexplorer.Node
import by.homework.hlazarseni.tfgridexplorer.databinding.ItemNodeBinding

class NodeViewHolder(
    private val binding: ItemNodeBinding,
    private val onNodeClicked: (Node) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Node) {
        with(binding) {
            nodeId.text = item.nodeId
            farmId.text = item.farmId
            statusId.text = item.status

            root.setOnClickListener {
                onNodeClicked(item)
            }
        }
    }
}