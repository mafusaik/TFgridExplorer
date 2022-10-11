package by.homework.hlazarseni.tfgridexplorer.data.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import by.homework.hlazarseni.tfgridexplorer.domain.model.Node

@Dao
interface NodeDao {
    @Query("SELECT * from node")
    suspend fun getNodes(): List<Node>

    @Query("SELECT * from node WHERE nodeId = :id")
    suspend fun getNode(id: Int): Node

    @Insert(onConflict = REPLACE)
    suspend fun insertNodes(nodes: List<Node>)

    @Insert(onConflict = REPLACE)
    suspend fun insertNode(node: Node)

    @Delete
    suspend fun deleteNode(node: Node)
}