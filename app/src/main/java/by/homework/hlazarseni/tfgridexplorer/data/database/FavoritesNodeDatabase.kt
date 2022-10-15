package by.homework.hlazarseni.tfgridexplorer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.homework.hlazarseni.tfgridexplorer.data.model.Node

@Database(entities = [Node::class], version = 1, exportSchema = false)
abstract class FavoritesNodeDatabase : RoomDatabase() {
    abstract val favoritesNodeDao: FavoritesNodeDao
}