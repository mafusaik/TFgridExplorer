package by.homework.hlazarseni.tfgridexplorer.data.repository


import by.homework.hlazarseni.tfgridexplorer.data.database.NodeDao
import by.homework.hlazarseni.tfgridexplorer.data.database.NodeDatabase
import by.homework.hlazarseni.tfgridexplorer.domain.model.Node
import by.homework.hlazarseni.tfgridexplorer.data.mapper.toDomain
import by.homework.hlazarseni.tfgridexplorer.domain.repository.NodeDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NodeDatabaseRepositoryImpl(
    private val nodesDao: NodeDao,
    private val nodeDatabase: NodeDatabase
) : NodeDatabaseRepository {


    override suspend fun getNodeDB(id: Int) = withContext(Dispatchers.IO) {
        runCatching {
            nodesDao.getNode(id).toDomain()
        }
    }

    override suspend fun getNodesDB() = withContext(Dispatchers.IO) {
        nodesDao.getNodes().map { it.toDomain() }
    }

    override suspend fun insertNodesDB(nodes: List<Node>) = withContext(Dispatchers.IO) {
        nodesDao.insertNodes(nodes.map { it.toDomain() })
    }

    override suspend fun deleteNodesDB(nodes: List<Node>) = withContext(Dispatchers.IO) {
        nodesDao.deleteNodes(nodes.map { it.toDomain() })
    }

    override suspend fun cleanDB(): Unit = withContext(Dispatchers.IO) {
        runCatching { nodeDatabase.clearAllTables() }
    }
}