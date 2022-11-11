package by.homework.hlazarseni.tfgridexplorer.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import by.homework.hlazarseni.tfgridexplorer.data.model.Node

@Dao
interface NodeDao {
    @Query("SELECT * from node")
    suspend fun getNodes(): List<Node>

//    @Query("SELECT * from node ORDER BY " + " CASE WHEN nodeId = 1 THEN nodeId END ASC ")
//    suspend fun getNodes(): List<Node>

    @Query("SELECT * from node WHERE nodeId = :id")
    suspend fun getNode(id: Int): Node

    @Insert(onConflict = REPLACE)
    suspend fun insertNodes(nodes: List<Node>)

    @Insert(onConflict = REPLACE)
    suspend fun insertNode(node: Node)

    @Delete
    suspend fun deleteNodes(nodes: List<Node>)
}