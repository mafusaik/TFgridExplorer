package by.homework.hlazarseni.tfgridexplorer.domain.repository

import by.homework.hlazarseni.tfgridexplorer.domain.model.Node
import kotlinx.coroutines.flow.Flow


interface NodeFavoriteDatabaseRepository {

    suspend fun addFavoritesNodeDB(node: Node)

    fun getFavoritesNodesDB(): Flow<List<Node>>

    suspend fun deleteFavoritesNode(node: Node)
}