package by.homework.hlazarseni.tfgridexplorer.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.homework.hlazarseni.tfgridexplorer.data.model.DetailNode
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeDatabaseRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.domain.model.Node
import by.homework.hlazarseni.tfgridexplorer.presentation.model.Lce
import kotlinx.coroutines.flow.*


class DetailViewModel(
    private val currentNode: DetailNode,
    private val repository: NodeDatabaseRepositoryImpl,
    private val apiRepository: NodeRepositoryImpl,
) :
    ViewModel() {

    private val _lceFlow = MutableStateFlow<Lce<Node>>(
        Lce.Loading
    )

    val dataDetailFlow = _lceFlow
        .map {
            repository.getNodeDB(currentNode.nodeId.toInt())
                .fold(
                    onSuccess = {
                        Lce.Content(it)
                    },
                    onFailure = {
                        Lce.Error(it)
                    }
                )
        }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1
        )

    suspend fun getLatitude() = apiRepository.getLatitude(currentNode)
    suspend fun getLongitude() = apiRepository.getLongitude(currentNode)
}