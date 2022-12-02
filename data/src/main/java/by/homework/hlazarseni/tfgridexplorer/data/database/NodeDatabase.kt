package by.homework.hlazarseni.tfgridexplorer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.homework.hlazarseni.tfgridexplorer.data.model.NodeEntity

@Database(entities = [NodeEntity::class], version = 1, exportSchema = false)
abstract class NodeDatabase : RoomDatabase() {
    abstract val nodesDao: NodeDao
}