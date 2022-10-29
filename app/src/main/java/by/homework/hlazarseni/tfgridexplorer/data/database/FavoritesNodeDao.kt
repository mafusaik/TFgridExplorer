package by.homework.hlazarseni.tfgridexplorer.data.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesNodeDao {
    @Query("SELECT * from node")
    fun getFavoritesNodes(): Flow<List<Node>>

    @Query("SELECT * from node WHERE nodeId = :id")
    suspend fun getFavoritesNode(id: Int): Node


    @Insert(onConflict = REPLACE)
    suspend fun insertFavoritesNode(node: Node)

    @Delete
    suspend fun deleteFavoritesNode(node: Node)
}