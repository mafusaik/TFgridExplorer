package by.homework.hlazarseni.tfgridexplorer.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import by.homework.hlazarseni.tfgridexplorer.entity.Node
import kotlinx.coroutines.flow.Flow

//@Dao
//interface NodeDao {
//    @Query("SELECT * from node")
//    suspend  fun getNodes(): List<Node>
//
//    @Query("SELECT * from node WHERE nodeId = :id")
//    suspend  fun getNode(id: Int): Node
//
//    @Insert(onConflict = REPLACE)
//    suspend fun insertNodes(nodes: List<Node>)
//}