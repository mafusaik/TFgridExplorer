package by.homework.hlazarseni.tfgridexplorer.data.repository

import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import by.homework.hlazarseni.tfgridexplorer.data.api.GridProxy
import by.homework.hlazarseni.tfgridexplorer.data.database.FavoritesNodeDao
import by.homework.hlazarseni.tfgridexplorer.data.database.NodeDao
import by.homework.hlazarseni.tfgridexplorer.data.mapper.toDomain
import by.homework.hlazarseni.tfgridexplorer.data.mapper.toDomainModels
import by.homework.hlazarseni.tfgridexplorer.domain.repository.NodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NodeRepositoryImpl(
    private val gridProxy: GridProxy,
    private val nodesDao: NodeDao,
    private val favoritesNodeDao: FavoritesNodeDao
) : NodeRepository {

    override suspend fun getNodes(page: Int) = withContext(Dispatchers.IO) {
        runCatching {
            gridProxy.getNodes(page).toDomainModels()
        }
    }

    override suspend fun getNode(id: Int) = withContext(Dispatchers.IO) {
        runCatching { gridProxy.getNode(id).toDomain() }
    }

    override suspend fun getNodeDB(id: Int) = withContext(Dispatchers.IO) {
        runCatching {
            nodesDao.getNode(id)
        }
    }

    override suspend fun getNodesDB() = withContext(Dispatchers.IO) {
        runCatching {
            nodesDao.getNodes()
        }
    }

    override suspend fun insertNodesDB(nodes: List<Node>) = withContext(Dispatchers.IO) {
        nodesDao.insertNodes(nodes)
    }

    override suspend fun clearDB() {
       nodesDao.insertNodes(emptyList())
    }

    override suspend fun getStats() = withContext(Dispatchers.IO) {
        runCatching {
            gridProxy.getStats()
        }
    }

    override suspend fun addFavoritesNodeDB(node: Node) = withContext(Dispatchers.IO) {
        favoritesNodeDao.insertFavoritesNode(node)
    }

    override fun getFavoritesNodesDB() =
        favoritesNodeDao.getFavoritesNodes()


    override suspend fun deleteFavoritesNode(node: Node) = withContext(Dispatchers.IO) {
        favoritesNodeDao.deleteFavoritesNode(node)
    }
}