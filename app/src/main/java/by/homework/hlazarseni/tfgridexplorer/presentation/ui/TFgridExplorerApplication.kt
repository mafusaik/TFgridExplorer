package by.homework.hlazarseni.tfgridexplorer.presentation.ui

import android.app.Application
import by.homework.hlazarseni.tfgridexplorer.data.di.*
import by.homework.hlazarseni.tfgridexplorer.presentation.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class TFgridExplorerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TFgridExplorerApplication)
            modules(
                apiModule,
                databaseModule,
                repositoryModule,
                viewModelModule,
                favoritesDatabaseModule,
                graphModule,
                serviceModule
            )
        }
    }
}