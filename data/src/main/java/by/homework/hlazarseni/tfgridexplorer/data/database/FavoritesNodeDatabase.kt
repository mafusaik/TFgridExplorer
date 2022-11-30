package by.homework.hlazarseni.tfgridexplorer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.homework.hlazarseni.tfgridexplorer.data.model.NodeEntity

@Database(entities = [NodeEntity::class], version = 1, exportSchema = false)
internal abstract class FavoritesNodeDatabase : RoomDatabase() {
    abstract val favoritesNodeDao: FavoritesNodeDao
}