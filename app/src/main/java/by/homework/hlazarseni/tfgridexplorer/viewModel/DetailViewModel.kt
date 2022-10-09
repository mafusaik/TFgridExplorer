package by.homework.hlazarseni.tfgridexplorer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.homework.hlazarseni.tfgridexplorer.database.NodeDatabase
import by.homework.hlazarseni.tfgridexplorer.database.NodeRepository
import by.homework.hlazarseni.tfgridexplorer.entity.DetailNode
import by.homework.hlazarseni.tfgridexplorer.entity.Node
import by.homework.hlazarseni.tfgridexplorer.lce.Lce
import by.homework.hlazarseni.tfgridexplorer.services.GridProxyService
import kotlinx.coroutines.flow.*


class DetailViewModel(
    private val currentNode: DetailNode,
    //private val gridProxy: GridProxyService
    private val nodeDatabase: NodeDatabase,
   // private val nodeRepository: NodeRepository
) :
    ViewModel() {

    private val _lceFlow = MutableStateFlow< Lce<Node>>(
        Lce.Loading
    )

    val dataDetailFlow = flow {
       //val currentNode = nodeRepository.getCurrentNode().nodeId.toInt()

        val state = kotlin.runCatching {
           // gridProxy.api.getNode(currentNode.nodeId.toInt())
              //  nodeRepository.getNodeDB(currentNode.nodeId.toInt())

           nodeDatabase.nodesDao.getNode(currentNode.nodeId.toInt())
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