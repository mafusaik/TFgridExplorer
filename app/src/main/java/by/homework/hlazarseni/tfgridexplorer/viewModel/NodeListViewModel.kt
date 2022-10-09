package by.homework.hlazarseni.tfgridexplorer.viewModel

import androidx.lifecycle.*
import by.homework.hlazarseni.tfgridexplorer.database.NodeRepository
import by.homework.hlazarseni.tfgridexplorer.entity.Node
import by.homework.hlazarseni.tfgridexplorer.lce.Lce
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import java.util.Locale.filter


class NodeListViewModel(
    private val nodeRepository: NodeRepository
) : ViewModel() {

    private var isLoading = false
    private var currentPage = 1

    private val _queryFlow = MutableStateFlow("")
    private val queryFlow = _queryFlow.asSharedFlow()

    private val loadItemsFlow = MutableSharedFlow<LoadItemsType>(
        replay = 1,
        //  extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val dataFlow =
        queryFlow
            .combine(loadDataFlow()) { query, nodes ->
                nodes.fold(
                    onSuccess = { list ->
                        list.filter { node -> node.nodeId.contains(query, ignoreCase = true) }
                    },
                    onFailure = {
                        emptyList()
                    }
                )

            }
            .runningReduce { items, loadedItems ->
                 items.union(loadedItems).toList()
               // items + loadedItems
            }
            .onStart {
                val listDB = nodeRepository.getNodesDB()
                val state = if (listDB.isNotEmpty()) {
                    nodeRepository.getNodesDB()
                } else {
                    error("Upload DB Failure")
                }
                emit(state)
            }
            .shareIn(
                viewModelScope,
                SharingStarted.Eagerly,
                replay = 1
            )

    private fun loadDataFlow(): Flow<Result<List<Node>>> {
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
                nodeRepository.getNodes(currentPage)
//                    .onSuccess {
//                        nodeRepository.clearDB()
//                        nodeRepository.insertNodesDB(it)
//                    }
                //         .onFailure { nodeRepository.getNodesDB() }
//                    .fold(
//                        onSuccess = { it },
//                        onFailure = {
//                            error("Upload Failure")
//                        }
//                    )
            }
            .onEach {
                //  nodeRepository.insertNodesDB(it)
                isLoading = false
            }
//            .runningReduce { items, loadedItems ->
//                // items.union(loadedItems).toList()
//                items + loadedItems
//            }
//            .onStart {
//                val listDB = nodeRepository.getNodesDB()
//                val state = if (listDB.isNotEmpty()) {
//                    nodeRepository.getNodesDB()
//                } else {
//                    error("Upload DB Failure")
//                }
//                emit(state)
////                emit(
////                    nodeRepository.getNodesDB()
////                        .fold(
////                            onSuccess = {it },
////                            onFailure = { error("Upload DB Failure") }
////                        )
////                )
//            }
    }


//        private val loadItemsFlow = MutableSharedFlow<LoadItemsType>(
//        replay = 1,
//        extraBufferCapacity = 0,
//        onBufferOverflow = BufferOverflow.DROP_OLDEST
//    )
//
//        private val lceFlow = MutableStateFlow<Lce<List<Node>>>(
//            Lce.Loading
//        )
//
//    private fun loadDataFlow(): Flow<List<Node>> {
//        return loadItemsFlow
//            .filter { !isLoading }
//            .onEach { isLoading = true }
//            .onStart { emit(LoadItemsType.REFRESH) }
//            .map { loadType ->
//                when (loadType) {
//                    LoadItemsType.REFRESH -> {
//                        currentPage = 1
//                    }
//                    LoadItemsType.LOAD_MORE -> {
//                        currentPage++
//                    }
//                }
//            }
//            .map {
//                nodeRepository.getNodes(currentPage)
//                    .onSuccess{nodeRepository.insertNodesDB(it)}
//                    .fold(
//                        onSuccess = { it },
//                        onFailure = { error("Upload Failure") }
//                    )
//            }
//            .onEach {
//                //             nodeRepository.insertNodesDB(it)
//                isLoading = false
//            }
//            .runningReduce { items, loadedItems ->
//                items.union(loadedItems).toList()
//            }
//            .onStart {
//                emit(
//                    nodeRepository.getNodesDB()
//                        .fold(
//                            onSuccess = {it },
//                            onFailure = { error("Upload DB Failure") }
//                        )
//                )
//            }
//    }

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