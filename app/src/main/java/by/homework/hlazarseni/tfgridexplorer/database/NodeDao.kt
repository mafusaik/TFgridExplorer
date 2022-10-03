package by.homework.hlazarseni.tfgridexplorer.database

import androidx.room.*
import by.homework.hlazarseni.tfgridexplorer.entity.Node
import kotlinx.coroutines.flow.Flow

//@Dao
//interface NodeDao {
//    @Query("SELECT * from node")
//    fun getCats(): Flow<List<Node>>
//
//    @Query("SELECT * from node WHERE nodeId = :id")
//    fun getCat(id: Int): Flow<Node>
//
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(item: Node)
//
//    @Update
//    suspend fun update(item: Node)
//
//    @Delete
//    suspend fun delete(item: Node)
//}