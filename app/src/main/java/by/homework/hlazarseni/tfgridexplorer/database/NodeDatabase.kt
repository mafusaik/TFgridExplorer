package by.homework.hlazarseni.tfgridexplorer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.homework.hlazarseni.tfgridexplorer.entity.Node
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Node::class], version = 1, exportSchema = false)
abstract class NodeDatabase : RoomDatabase() {
    abstract val nodesDao: NodeDao


//    companion object {
//
//        private var _nodeDatabase: NodeDatabase? = null
//
//        fun getDatabase(context: Context): NodeDatabase {
//            return _nodeDatabase ?: synchronized(this) {
//                val nodeDatabase = Room.databaseBuilder(
//                    context.applicationContext,
//                    NodeDatabase::class.java,
//                    "node-database"
//                )
//                    .allowMainThreadQueries()
//                    .build()
//
//                _nodeDatabase = nodeDatabase
//
//                nodeDatabase
//            }
//        }
//    }
}
