package by.homework.hlazarseni.tfgridexplorer.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.homework.NodeByIdQuery
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.domain.model.DetailNode
import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import by.homework.hlazarseni.tfgridexplorer.presentation.model.Lce
import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext


class DetailViewModel(
    private val currentNode: DetailNode,
    private val nodeRepositoryImpl: NodeRepositoryImpl
) :
    ViewModel() {

    private val _lceFlow = MutableStateFlow<Lce<Node>>(
        Lce.Loading
    )

    val dataDetailFlow = _lceFlow
        .onStart {
            emit(Lce.Loading)
        }
        .map {
            nodeRepositoryImpl.getNodeDB(currentNode.nodeId.toInt())
                .fold(
                    onSuccess = {
                        Lce.Content(it)
                    },
                    onFailure = {
                        Lce.Error(it)
                    }
                )
        }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1
        )

    //пока костыль для координат, потом надо весь проект перенести на это
    private val apolloClient =
        ApolloClient.Builder().serverUrl("https://graphql.grid.tf/graphql").build()

    suspend fun getLatitude() = withContext(Dispatchers.IO) {
        val response = apolloClient.query(NodeByIdQuery(currentNode.id)).execute()
        return@withContext response.data?.nodeById?.location?.latitude.toString()
    }

    suspend fun getLongitude() = withContext(Dispatchers.IO) {
        val response = apolloClient.query(NodeByIdQuery(currentNode.id)).execute()
        return@withContext response.data?.nodeById?.location?.longitude.toString()
    }
}