package by.homework.hlazarseni.tfgridexplorer.domain.repository

import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import by.homework.hlazarseni.tfgridexplorer.data.model.Stats
import kotlinx.coroutines.flow.Flow


interface NodeRepository {

    suspend fun getNodes(page: Int): Result<List<Node>>

    suspend fun getNode(id: Int): Result<Node>

    suspend fun getNodeDB(id: Int): Result<Node>

    suspend fun getNodesDB(): List<Node>

    suspend fun insertNodesDB(nodes: List<Node>)

    suspend fun clearDB()

    suspend fun getStats(): Result<Stats>

    suspend fun addFavoritesNodeDB(node: Node)

    fun getFavoritesNodesDB(): Flow<List<Node>>

    suspend fun deleteFavoritesNode(node: Node)

    suspend fun deleteNodes(nodes: List<Node>)
}