package by.homework.hlazarseni.tfgridexplorer.presentation.ui

import android.app.Application
import by.homework.hlazarseni.tfgridexplorer.data.di.apiModule
import by.homework.hlazarseni.tfgridexplorer.data.di.databaseModule
import by.homework.hlazarseni.tfgridexplorer.data.di.favoritesDatabaseModule
import by.homework.hlazarseni.tfgridexplorer.data.di.repositoryModule
import by.homework.hlazarseni.tfgridexplorer.presentation.di.detailViewModelModule
import by.homework.hlazarseni.tfgridexplorer.presentation.di.favoritesViewModelModule
import by.homework.hlazarseni.tfgridexplorer.presentation.di.listViewModelModule
import by.homework.hlazarseni.tfgridexplorer.presentation.di.statsViewModelModule
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
                listViewModelModule,
                detailViewModelModule,
                statsViewModelModule,
                favoritesViewModelModule,
                favoritesDatabaseModule
            )
        }
    }
}