package by.homework.hlazarseni.tfgridexplorer.database

import android.app.Application

import by.homework.hlazarseni.tfgridexplorer.module.viewModelModule
import by.homework.hlazarseni.tfgridexplorer.module.nodeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class TFgridExplorerApplication : Application() {

//    private val value: MyClass by inject {
//        parametersOf(1)
//    }

//    private val nodeDao = get<NodeDao>()

//    override fun onCreate() {
//        super.onCreate()
//
//        //    get<MyClass>{ parametersOf(1)}
//
//        startKoin {
//            androidContext(this@TFgridExplorerApplication)
//            modules(
//                nodeModule,
//                 databaseModule,
//                viewModelModule
//            )
//        }
//    }
}