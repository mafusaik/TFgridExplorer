package by.homework.hlazarseni.tfgridexplorer.koin

import androidx.room.Room
import by.homework.hlazarseni.tfgridexplorer.database.NodeDatabase
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