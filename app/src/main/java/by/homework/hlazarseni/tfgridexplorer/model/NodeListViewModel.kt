package by.homework.hlazarseni.tfgridexplorer.model

import androidx.lifecycle.*
import by.homework.hlazarseni.tfgridexplorer.entity.Node
import by.homework.hlazarseni.tfgridexplorer.entity.PagingData
import by.homework.hlazarseni.tfgridexplorer.lce.Lce
import by.homework.hlazarseni.tfgridexplorer.services.GridProxyService
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class NodeListViewModel(
    private val gridProxy: GridProxyService
) : ViewModel() {

    private var isLoading = false
    private var currentPage = 0

    private val _queryFlow = MutableStateFlow("")
    private val queryFlow = _queryFlow.asSharedFlow()

//    private val _queryFlow = MutableStateFlow<Lce<List<Node>>>(Lce.Loading)
//    private val queryFlow = _queryFlow.asStateFlow()

    private val loadItemsFlow = MutableSharedFlow<LoadItemsType>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun onQueryChanged(query: String) {
        _queryFlow.value = query
        //  _queryFlow.value = Lce.Content(query)
    }

    val dataFlow: Flow<List<Node>> =
        queryFlow
            .combine(loadDataFlow()) { query, nodes ->
                nodes.filter {
                     it.nodeId.contains(query, ignoreCase = true)
                    //Lce.Content(it).data.nodeId.contains(query.toString(), ignoreCase = true)
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
  //                  .runCatching {
                    gridProxy
                        .api
                        .getNodes(currentPage)
//                }
//                    .onSuccess {
//                        _queryFlow.value = Lce.Content(it)
//                    }
//                    .onFailure {
//                        _queryFlow.value = Lce.Error(it)
//                    }

            }
            //.map { it.getOrDefault(gridProxy.api.getNodes(currentPage)) }
            .onEach { isLoading = false }
            .runningReduce { items, loadedItems ->
                items.union(loadedItems).toList()
            }
    }

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

}