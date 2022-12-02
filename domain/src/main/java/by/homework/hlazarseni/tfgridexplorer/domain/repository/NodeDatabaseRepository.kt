package by.homework.hlazarseni.tfgridexplorer.domain.repository

import by.homework.hlazarseni.tfgridexplorer.domain.model.Node
import by.homework.hlazarseni.tfgridexplorer.domain.model.Stats
import kotlinx.coroutines.flow.Flow


interface NodeDatabaseRepository {

    suspend fun getNodeDB(id: Int): Result<Node>

    suspend fun getNodesDB(): List<Node>

    suspend fun insertNodesDB(nodes: List<Node>)

    suspend fun deleteNodesDB(nodes: List<Node>)

    suspend fun cleanDB()
}