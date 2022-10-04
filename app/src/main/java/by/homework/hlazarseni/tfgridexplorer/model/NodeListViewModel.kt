package by.homework.hlazarseni.tfgridexplorer.model

import androidx.lifecycle.*

import by.homework.hlazarseni.tfgridexplorer.entity.Node
import by.homework.hlazarseni.tfgridexplorer.services.GridProxyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext


class NodeListViewModel(
    private val gridProxy: GridProxyService,
  //  private val nodeDatabase: NodeDatabase
) : ViewModel() {

    private var isLoading = false
    private var currentPage = 0

    private val _queryFlow = MutableStateFlow("")
    private val queryFlow = _queryFlow.asSharedFlow()


    private val loadItemsFlow = MutableSharedFlow<LoadItemsType>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun onQueryChanged(query: String) {
        _queryFlow.value = query
    }

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
            .onStart { emit(LoadItemsType.REFRESH) }
            .onEach { isLoading = true }
            .map { loadType ->
                when (loadType) {
                    LoadItemsType.REFRESH -> {
                        currentPage = 1
                    }
                    LoadItemsType.LOAD_MORE -> {
                        currentPage++
                    }
                }
                    gridProxy
                        .api
                        .getNodes(currentPage)
            }
            .onEach { isLoading = false }
            .runningReduce { items, loadedItems ->
                items.union(loadedItems).toList()
            }
    }
//
    enum class LoadItemsType {
        REFRESH, LOAD_MORE
    }

    fun onRefreshed() {
        loadItemsFlow.tryEmit(LoadItemsType.REFRESH)
    }

    fun onLoadMore() {
        if (!isLoading) {
            loadItemsFlow.tryEmit(LoadItemsType.LOAD_MORE)
        }
    }

    //------------------------------------------------------------------


//    private val loadMoreFlow = MutableSharedFlow<LoadItemsType>(
//        replay = 1,
//       // extraBufferCapacity = 0,
//        onBufferOverflow = BufferOverflow.DROP_OLDEST
//    )
//
//    init {
//        onLoadMore()
//    }
//
//    val dataFlow = loadMoreFlow
//        .filter { !isLoading }
//        .onEach { isLoading = true }
//        .map {
//            runCatching {
//                withContext(Dispatchers.IO) {
//                    gridProxy.api.getNodes(currentPage)
//                }
//            }
//                .fold(
//                    onSuccess = { it },
//                    onFailure = { error("Upload Failure") }
//                )
//        }
//        .onEach {
//            nodeDatabase.nodesDao.insertNodes(it)
//            isLoading = false
//            currentPage++
//        }
//        .runningReduce { accumulator, value -> accumulator + value }
//        .onStart { emit(nodeDatabase.nodesDao.getNodes()) }
//        .shareIn(
//            scope = viewModelScope,
//            started = SharingStarted.Eagerly,
//            replay = 1
//        )
//
//    fun onLoadMore() {
//        if (!isLoading) {
//            loadMoreFlow.tryEmit(LoadItemsType.LOAD_MORE)
//        }
//    }
//
//    fun onRefreshed() {
//        loadMoreFlow.tryEmit(LoadItemsType.REFRESH)
//    }

//    fun onQueryChanged(query: String) {
//        _queryFlow.value = query
//    }
}