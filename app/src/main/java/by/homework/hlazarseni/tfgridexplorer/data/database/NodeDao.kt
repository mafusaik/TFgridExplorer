package by.homework.hlazarseni.tfgridexplorer.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import by.homework.hlazarseni.tfgridexplorer.data.model.NodeEntity

@Dao
interface NodeDao {
    @Query("SELECT * from nodeentity")
    suspend fun getNodes(): List<NodeEntity>

    @Query("SELECT * from nodeentity WHERE nodeId = :id")
    suspend fun getNode(id: Int): NodeEntity

    @Insert(onConflict = REPLACE)
    suspend fun insertNodes(nodes: List<NodeEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun insertNode(node: NodeEntity)

    @Delete
    suspend fun deleteNodes(nodes: List<NodeEntity>)
}