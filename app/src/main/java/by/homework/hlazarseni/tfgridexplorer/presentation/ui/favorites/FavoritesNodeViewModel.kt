package by.homework.hlazarseni.tfgridexplorer.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeFavoriteDatabaseRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.domain.model.Node
import kotlinx.coroutines.launch

class FavoritesNodeViewModel(
    private val dataRepository: NodeFavoriteDatabaseRepositoryImpl
) : ViewModel() {

    val allFavoritesNode: LiveData<List<Node>> = dataRepository.getFavoritesNodesDB().asLiveData()

   fun deletingNode(node: Node) {
        viewModelScope.launch {
            dataRepository.deleteFavoritesNode(node)
        }
    }

    fun addingNode(node: Node) {
        viewModelScope.launch {
            dataRepository.addFavoritesNodeDB(node)
        }
    }
}