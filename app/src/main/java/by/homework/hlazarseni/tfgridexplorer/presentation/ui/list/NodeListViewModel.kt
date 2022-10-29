package by.homework.hlazarseni.tfgridexplorer.presentation.ui.list

import androidx.lifecycle.*
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import by.homework.hlazarseni.tfgridexplorer.presentation.model.Lce
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
                    .fold(
                        onSuccess = { it },
                        onFailure = { emptyList() }
                    )
            }

            .onEach {
                isLoading = false
                nodeRepositoryImpl.insertNodesDB(it)
            }
            .runningReduce { items, loadedItems ->
                items.union(loadedItems).toList()
            }
            .onStart {
                loadDBFlow
            }
    }

    private fun errorFlow(throwable: Throwable) {
        errorFlow
            .map { throwable }
    }

    val loadDBFlow = flow {
        val state = nodeRepositoryImpl.getNodesDB()
            .fold(
                onSuccess = { Lce.Content(it) },
                onFailure = { Lce.Error(it) }
            )
        emit(state)
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