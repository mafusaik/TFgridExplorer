package by.homework.hlazarseni.tfgridexplorer.data.di

import androidx.room.Room
import by.homework.hlazarseni.tfgridexplorer.data.database.NodeDatabase
import org.koin.dsl.module


val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            NodeDatabase::class.java,
            "node-database"
        )
            .build()
    }
    single { get<NodeDatabase>().nodesDao }
}