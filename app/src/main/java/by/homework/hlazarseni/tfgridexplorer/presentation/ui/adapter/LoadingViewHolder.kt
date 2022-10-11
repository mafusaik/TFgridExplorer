package by.homework.hlazarseni.tfgridexplorer.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import by.homework.hlazarseni.tfgridexplorer.databinding.ItemErrorBinding
import by.homework.hlazarseni.tfgridexplorer.databinding.ItemLoadingBinding

class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

class ErrorViewHolder(
    binding: ItemErrorBinding,
    onRepeatClicked: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.errorButton.setOnClickListener {
            onRepeatClicked()
        }
    }
}