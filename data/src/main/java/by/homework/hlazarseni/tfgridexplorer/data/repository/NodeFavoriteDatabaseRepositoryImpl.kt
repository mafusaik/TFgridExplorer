package by.homework.hlazarseni.tfgridexplorer.data.repository

import by.homework.hlazarseni.tfgridexplorer.domain.model.Node
import by.homework.hlazarseni.tfgridexplorer.data.database.FavoritesNodeDao
import by.homework.hlazarseni.tfgridexplorer.data.mapper.toDomain
import by.homework.hlazarseni.tfgridexplorer.data.mapper.toDomainModelsForEntity
import by.homework.hlazarseni.tfgridexplorer.domain.repository.NodeFavoriteDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NodeFavoriteDatabaseRepositoryImpl(
    private val favoritesNodeDao: FavoritesNodeDao
) : NodeFavoriteDatabaseRepository {

    override suspend fun addFavoritesNodeDB(node: Node) = withContext(Dispatchers.IO) {
        favoritesNodeDao.insertFavoritesNode(node.toDomain())
    }

    override fun getFavoritesNodesDB() =
        favoritesNodeDao.getFavoritesNodes().map { it.toDomainModelsForEntity() }

    override suspend fun deleteFavoritesNode(node: Node) = withContext(Dispatchers.IO) {
        favoritesNodeDao.deleteFavoritesNode(node.toDomain())
    }
}