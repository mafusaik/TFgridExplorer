package by.homework.hlazarseni.tfgridexplorer

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.homework.hlazarseni.tfgridexplorer.databinding.ItemErrorBinding
import by.homework.hlazarseni.tfgridexplorer.databinding.ItemLoadingBinding
import by.homework.hlazarseni.tfgridexplorer.databinding.ItemNodeBinding

class NodeAdapter(
    context: Context,
    private val onNodeClicked: (Node) -> Unit,
    private val onRepeatClicked: () -> Unit

) : ListAdapter<PagingData<Node>, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PagingData.Item -> TYPE_NODE
            PagingData.Loading -> TYPE_LOADING
            PagingData.Error -> TYPE_ERROR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_NODE -> {
                NodeViewHolder(
                    binding = ItemNodeBinding.inflate(layoutInflater, parent, false),
                    onNodeClicked = onNodeClicked
                )
            }
            TYPE_LOADING -> {
                LoadingViewHolder(
                    binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                )
            }
            TYPE_ERROR ->{
                ErrorViewHolder(
                    binding = ItemErrorBinding.inflate(layoutInflater, parent, false),
                    onRepeatClicked = onRepeatClicked
                )
            }
            else -> error("unsupported viewtype $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is PagingData.Item -> {
                checkNotNull(holder as NodeViewHolder) { "incorrect viewholder $item" }
                holder.bind(item.data)
            }
            PagingData.Loading -> { }
            PagingData.Error -> { }

        }
    }

    companion object {

        private const val TYPE_NODE = 0
        private const val TYPE_LOADING = 1
        private const val TYPE_ERROR = 2

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PagingData<Node>>() {
            override fun areItemsTheSame(
                oldItem: PagingData<Node>,
                newItem: PagingData<Node>
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PagingData<Node>,
                newItem: PagingData<Node>
            ): Boolean {
                val oldUser = oldItem as? PagingData.Item
                val newUser = newItem as? PagingData.Item
                return oldUser?.data == newUser?.data
            }
        }
    }
}

class NodeViewHolder(
    private val binding: ItemNodeBinding,
    private val onNodeClicked: (Node) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Node) {
        with(binding) {
            nodeId.text = item.nodeId
            farmId.text = item.farmId
            statusId.text = item.status
            // country.text = item.country

            root.setOnClickListener {
                onNodeClicked(item)
            }
        }
    }
}

class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

class ErrorViewHolder(
    binding: ItemErrorBinding,
    onRepeatClicked: () -> Unit
) : RecyclerView.ViewHolder(binding.root){

    init {
        binding.errorButton.setOnClickListener {
            onRepeatClicked()
        }
    }
}