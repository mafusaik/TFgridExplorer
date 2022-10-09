package by.homework.hlazarseni.tfgridexplorer.koin

import android.app.Application
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
              //  detailViewModelModule
            )
        }
    }
}