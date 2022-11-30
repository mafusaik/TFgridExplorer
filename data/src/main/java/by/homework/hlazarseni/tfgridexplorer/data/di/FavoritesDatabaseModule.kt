package by.homework.hlazarseni.tfgridexplorer.data.di

import androidx.room.Room
import by.homework.hlazarseni.tfgridexplorer.data.database.FavoritesNodeDatabase
import org.koin.dsl.module


internal val favoritesDatabaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            FavoritesNodeDatabase::class.java,
            "favorites-node-database"
        )
            .build()
    }
    single { get<FavoritesNodeDatabase>().favoritesNodeDao }
}