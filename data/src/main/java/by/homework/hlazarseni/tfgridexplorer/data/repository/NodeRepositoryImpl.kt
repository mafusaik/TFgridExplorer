package by.homework.hlazarseni.tfgridexplorer.data.repository

import by.homework.NodeByIdQuery
import by.homework.hlazarseni.tfgridexplorer.data.api.GridProxy
import by.homework.hlazarseni.tfgridexplorer.data.mapper.toDomain
import by.homework.hlazarseni.tfgridexplorer.data.mapper.toDomainModels
import by.homework.hlazarseni.tfgridexplorer.data.model.DetailNode
import by.homework.hlazarseni.tfgridexplorer.domain.repository.NodeRepository
import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NodeRepositoryImpl(
    private val gridProxy: GridProxy,
    private val apolloClient: ApolloClient
) : NodeRepository {

    override suspend fun getNodes(page: Int) = withContext(Dispatchers.IO) {
        runCatching {
            gridProxy.getNodes(page).toDomainModels()
        }
    }

    override suspend fun getNode(id: Int) = withContext(Dispatchers.IO) {
        runCatching { gridProxy.getNode(id).toDomain() }
    }

    override suspend fun getStats() = withContext(Dispatchers.IO) {
        runCatching {
            gridProxy.getStats()
        }
    }

    suspend fun getLatitude(currentNode: DetailNode) = withContext(Dispatchers.IO) {
        val response = apolloClient.query(NodeByIdQuery(currentNode.id)).execute()
        return@withContext response.data?.nodeById?.location?.latitude.toString()
    }

    suspend fun getLongitude(currentNode: DetailNode) = withContext(Dispatchers.IO) {
        val response = apolloClient.query(NodeByIdQuery(currentNode.id)).execute()
        return@withContext response.data?.nodeById?.location?.longitude.toString()
    }
}