package by.homework.hlazarseni.tfgridexplorer.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.domain.model.DetailNode
import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import by.homework.hlazarseni.tfgridexplorer.presentation.model.Lce
import kotlinx.coroutines.flow.*


class DetailViewModel(
    private val currentNode: DetailNode,
    private val nodeRepositoryImpl: NodeRepositoryImpl
) :
    ViewModel() {

    private val _lceFlow = MutableStateFlow<Lce<Node>>(
        Lce.Loading
    )

    val dataDetailFlow = _lceFlow
        .onStart {
            emit(Lce.Loading)
        }
        .map {
            nodeRepositoryImpl.getNodeDB(currentNode.nodeId.toInt())
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
}