package by.homework.hlazarseni.tfgridexplorer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.homework.hlazarseni.tfgridexplorer.data.model.Node

@Database(entities = [Node::class], version = 1, exportSchema = false)
abstract class NodeDatabase : RoomDatabase() {
    abstract val nodesDao: NodeDao
}