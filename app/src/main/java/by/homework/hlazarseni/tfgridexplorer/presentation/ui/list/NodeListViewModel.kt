package by.homework.hlazarseni.tfgridexplorer.presentation.ui.list

import androidx.lifecycle.*
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeDatabaseRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.domain.model.Node
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*


class NodeListViewModel(
    private val dataRepository: NodeDatabaseRepositoryImpl,
    private val apiRepository: NodeRepositoryImpl
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
                        currentPage = 0
                    }
                    LoadItemsType.LOAD_MORE -> {
                        currentPage++
                    }
                }
            }
            .onStart {
                if (apiRepository.getNodes(currentPage).isSuccess) {
                    dataRepository.cleanDB()
                }
            }
            .map {
                apiRepository.getNodes(currentPage)
                    .fold(
                        onSuccess = {
                            dataRepository.insertNodesDB(it)
                            it
                        },
                        onFailure = { dataRepository.getNodesDB().sortedBy { it.id } }
                    )
            }
            .onEach { isLoading = false }
            .runningReduce { items, loadedItems ->
                items.union(loadedItems).toList()
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