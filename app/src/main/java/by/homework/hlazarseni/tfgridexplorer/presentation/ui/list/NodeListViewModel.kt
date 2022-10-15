package by.homework.hlazarseni.tfgridexplorer.presentation.ui.list

import androidx.lifecycle.*
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*


class NodeListViewModel(
    private val nodeRepositoryImpl: NodeRepositoryImpl
) : ViewModel() {

    private var isLoading = false
    private var currentPage = 1

    private val _queryFlow = MutableStateFlow("")
    private val queryFlow = _queryFlow.asSharedFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()
    private val errorFlow = _errorFlow.asSharedFlow()


    private val loadItemsFlow = MutableSharedFlow<LoadItemsType>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val dataFlow =
        queryFlow
            .combine(loadDataFlow()) { query, nodes ->
                nodes.filter { node ->
                    node.nodeId.contains(query, ignoreCase = true)
                }
            }
            .shareIn(
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
                nodeRepositoryImpl.getNodes(currentPage)
                    .onSuccess {
                        nodeRepositoryImpl.clearDB()
                        nodeRepositoryImpl.insertNodesDB(it)
                    }
                    .onFailure {  }
                    .fold(
                        onSuccess = { it },
                        onFailure = {
                          emptyList()
                        }
                    )
            }
            .onEach {
                isLoading = false
            }
            .runningReduce { items, loadedItems ->
                items.union(loadedItems).toList()
            }
            .onStart {
                val listDB = nodeRepositoryImpl.getNodesDB()
                    .fold(
                        onSuccess = { it },
                        onFailure = { emptyList() }
                    )
                val state = listDB.ifEmpty {
                    emptyList()
                }
                emit(state)
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

   private enum class LoadItemsType {
        REFRESH, LOAD_MORE
    }
}