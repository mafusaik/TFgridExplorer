package by.homework.hlazarseni.tfgridexplorer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.homework.hlazarseni.tfgridexplorer.domain.model.Node

@Database(entities = [Node::class], version = 1, exportSchema = false)
abstract class NodeDatabase : RoomDatabase() {
    abstract val nodesDao: NodeDao
}

private lateinit var INSTANCE: NodeDatabase

fun getDatabase(context: Context): NodeDatabase {
    synchronized(NodeDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                NodeDatabase::class.java,
                "node-database").build()
        }
    }
    return INSTANCE
}