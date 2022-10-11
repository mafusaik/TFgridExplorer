package by.homework.hlazarseni.tfgridexplorer.domain.repository

import by.homework.hlazarseni.tfgridexplorer.domain.model.Node
import by.homework.hlazarseni.tfgridexplorer.domain.model.Stats


interface NodeRepository {

    suspend fun getNodes(page: Int): Result<List<Node>>

    suspend fun getNode(id: Int): Result<Node>

    suspend fun getNodeDB(id: Int): Result<Node>

    suspend fun getNodesDB(): Result<List<Node>>

    suspend fun insertNodesDB(nodes: List<Node>)

    suspend fun clearDB()

    suspend fun getStats():Result<Stats>
}