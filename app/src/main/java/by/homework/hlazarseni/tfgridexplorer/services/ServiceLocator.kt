package by.homework.hlazarseni.tfgridexplorer.services

import android.app.Application
import android.content.Context
import androidx.room.Room



//object ServiceLocator:Application() {
//
//        private var _nodeDatabase: NodeDatabase? = null
//    val nodeDatabase get() = requireNotNull(_nodeDatabase)
//
//    override fun onCreate() {
//        super.onCreate()
//        _nodeDatabase = Room
//            .databaseBuilder(
//                this,
//                NodeDatabase::class.java,
//                "node-database"
//            )
//            .allowMainThreadQueries()
//            .build()
//    }
//}
//
//val Context.nodeDatabase: NodeDatabase
//    get() = when (this) {
//        is ServiceLocator -> nodeDatabase
//        else -> applicationContext.nodeDatabase
//    }
