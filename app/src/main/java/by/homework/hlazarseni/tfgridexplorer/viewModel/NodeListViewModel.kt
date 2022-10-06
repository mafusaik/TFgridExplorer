package by.homework.hlazarseni.tfgridexplorer.viewModel

import androidx.lifecycle.*
import by.homework.hlazarseni.tfgridexplorer.database.NodeRepository
import by.homework.hlazarseni.tfgridexplorer.entity.Node

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*


class NodeListViewModel(
    private val nodeRepository: NodeRepository
) : ViewModel() {


    private var isLoading = false
    private var currentPage = 1

    private val _queryFlow = MutableStateFlow("")
    private val queryFlow = _queryFlow.asSharedFlow()

    private val loadItemsFlow = MutableSharedFlow<LoadItemsType>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val dataFlow: Flow<List<Node>> =
        queryFlow
            .combine(loadDataFlow()) { query, nodes ->
                nodes.filter {
                    it.nodeId.contains(query, ignoreCase = true)
                }
            }.shareIn(
                viewModelScope,
                SharingStarted.Eagerly,
                replay = 1
            )

    private fun loadDataFlow(): Flow<List<Node>> {
        return loadItemsFlow
            .filter { !isLoading }
            .onEach { isLoading = true }
            .onStart { emit(LoadItemsType.REFRESH) }
            .map { loadType ->
                when (loadType) {
                    LoadItemsType.REFRESH -> {
                        currentPage = 1
                    }
                    LoadItemsType.LOAD_MORE -> {
                        currentPage++
                    }
                }
            }
            .map {
                runCatching {
                    nodeRepository.getNodes(currentPage)
                }
                    .fold(
                        onSuccess = { it },
                        onFailure = {
                            error("Upload Failure")
                        }
                    )
            }
            .onEach {
                nodeRepository.insertNodesDB(it)
                isLoading = false
            }
            .runningReduce { items, loadedItems ->
                items.union(loadedItems).toList()
            }
            .onStart {
                emit(
                    nodeRepository.getNodesDB()
                        .fold(
                            onSuccess = { it },
                            onFailure = { error("Upload DB Failure") }
                        )
                )
            }
    }

    fun onLoadMore() {
        if (!isLoading) {
            loadItemsFlow.tryEmit(LoadItemsType.LOAD_MORE)
        }
    }

    fun onRefreshed() {
        loadItemsFlow.tryEmit(LoadItemsType.REFRESH)
    }

    fun onQueryChanged(query: String) {
        _queryFlow.value = query
    }

    enum class LoadItemsType {
        REFRESH, LOAD_MORE
    }
}