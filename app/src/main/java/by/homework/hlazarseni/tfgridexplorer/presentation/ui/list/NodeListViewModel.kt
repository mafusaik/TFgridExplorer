package by.homework.hlazarseni.tfgridexplorer.presentation.ui.list

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.domain.model.Node
import by.homework.hlazarseni.tfgridexplorer.presentation.model.Lce
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import java.lang.Exception


class NodeListViewModel(
    private val nodeRepositoryImpl: NodeRepositoryImpl
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
                    .onFailure { nodeRepositoryImpl.getNodesDB() }
                    .fold(
                        onSuccess = { it },
                        onFailure = {
                            error("Upload Failure")
                        }
                    )
            }
            .onEach {
                //  nodeRepository.insertNodesDB(it)
                isLoading = false
            }
            .runningReduce { items, loadedItems ->
                items.union(loadedItems).toList()
                //items + loadedItems
            }
            .onStart {
                val listDB = nodeRepositoryImpl.getNodesDB()
                    .fold(
                        onSuccess = { it },
                        onFailure = { error("Upload DB Failure") }
                    )
                val state = listDB.ifEmpty {
                    error("Upload DB Failure")
                }
                emit(state)
            }

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