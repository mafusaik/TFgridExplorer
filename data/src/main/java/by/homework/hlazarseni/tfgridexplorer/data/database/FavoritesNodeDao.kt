package by.homework.hlazarseni.tfgridexplorer.data.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import by.homework.hlazarseni.tfgridexplorer.data.model.NodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesNodeDao {
    @Query("SELECT * from nodeentity")
    fun getFavoritesNodes(): Flow<List<NodeEntity>>

    @Query("SELECT * from nodeentity WHERE nodeId = :id")
    suspend fun getFavoritesNode(id: Int): NodeEntity


    @Insert(onConflict = REPLACE)
    suspend fun insertFavoritesNode(node: NodeEntity)

    @Delete
    suspend fun deleteFavoritesNode(node: NodeEntity)
}