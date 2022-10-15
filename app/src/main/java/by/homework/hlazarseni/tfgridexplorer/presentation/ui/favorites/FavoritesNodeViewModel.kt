package by.homework.hlazarseni.tfgridexplorer.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesNodeViewModel(
    private val nodeRepositoryImpl: NodeRepositoryImpl
) : ViewModel() {

    private val _queryFlow = MutableStateFlow("")
    private val queryFlow = _queryFlow.asSharedFlow()

    private val _dataFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val dataFlow = _dataFlow.asSharedFlow()

    val favoritesDataFlow =
        queryFlow
            .combine(favoritesNodeFlow()) { query, nodes ->
                nodes.filter { node ->
                    node.nodeId.contains(query, ignoreCase = true)
                }
            }
            .shareIn(
                viewModelScope,
                SharingStarted.Eagerly,
                replay = 1
            )


   private fun favoritesNodeFlow(): Flow<List<Node>> {
        return dataFlow
            .onStart {
                emit(Unit)
            }
            .flatMapLatest {
                nodeRepositoryImpl.getFavoritesNodesDB()
            }
            .runningReduce { items, loadedItems ->
                items + loadedItems
            }

            .shareIn(
                viewModelScope,
                SharingStarted.Eagerly,
                replay = 1
            )
    }

    fun deleteNode(node: Node) {
        viewModelScope.launch {
            nodeRepositoryImpl.deleteFavoritesNode(node)
        }
    }

    fun insertNode(node: Node) {
        viewModelScope.launch {
            nodeRepositoryImpl.addFavoritesNodeDB(node)
        }
    }

    fun onQueryChanged(query: String) {
        _queryFlow.value = query
    }
}