package by.homework.hlazarseni.tfgridexplorer.database


import by.homework.hlazarseni.tfgridexplorer.entity.Node
import by.homework.hlazarseni.tfgridexplorer.services.GridProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NodeRepository(
    private val gridProxy: GridProxy,
    private val nodeDatabase: NodeDatabase
) {

    suspend fun getNodes(page: Int) = withContext(Dispatchers.IO) {
        gridProxy.getNodes(page)
    }

    suspend fun getNode(id: Int) = withContext(Dispatchers.IO) {
        runCatching { gridProxy.getNode(id) }
    }

    suspend fun getNodesDB() = withContext(Dispatchers.IO) {
        runCatching { nodeDatabase.nodesDao.getNodes() }
    }

    suspend fun insertNodesDB(nodes: List<Node>) = withContext(Dispatchers.IO) {
        runCatching { nodeDatabase.nodesDao.insertNodes(nodes) }
    }
}