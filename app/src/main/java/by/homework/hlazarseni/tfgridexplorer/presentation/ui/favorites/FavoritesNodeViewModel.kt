package by.homework.hlazarseni.tfgridexplorer.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeRepositoryImpl
import kotlinx.coroutines.launch

class FavoritesNodeViewModel(
    private val nodeRepositoryImpl: NodeRepositoryImpl
) : ViewModel() {

    val allFavoritesNode: LiveData<List<Node>> = nodeRepositoryImpl.getFavoritesNodesDB().asLiveData()

   fun deletingNode(node: Node) {
        viewModelScope.launch {
            nodeRepositoryImpl.deleteFavoritesNode(node)
        }
    }

    fun addingNode(node: Node) {
        viewModelScope.launch {
            nodeRepositoryImpl.addFavoritesNodeDB(node)
        }
    }
}