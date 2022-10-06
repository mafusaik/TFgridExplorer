package by.homework.hlazarseni.tfgridexplorer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.homework.hlazarseni.tfgridexplorer.entity.DetailNode
import by.homework.hlazarseni.tfgridexplorer.entity.Node
import by.homework.hlazarseni.tfgridexplorer.lce.Lce
import by.homework.hlazarseni.tfgridexplorer.services.GridProxyService
import kotlinx.coroutines.flow.*


class DetailViewModel(
    private val currentNode: DetailNode,
    private val gridProxy: GridProxyService
) :
    ViewModel() {

    private val _lceFlow = MutableStateFlow<Lce<Node>>(
        Lce.Loading
    )

    val dataFlow = flow {
        val state = kotlin.runCatching {
            gridProxy.api.getNode(currentNode.nodeId.toInt())
        }
            .fold(
                onSuccess = {
                    _lceFlow.value = Lce.Content(it)
                    Lce.Content(it)
                },
                onFailure = {
                    Lce.Error(it)
                }
            )
        emit(state)
    }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1
        )
}