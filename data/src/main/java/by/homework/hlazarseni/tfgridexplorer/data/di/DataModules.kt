package by.homework.hlazarseni.tfgridexplorer.data.di

import org.koin.dsl.module

val dataModules = module {
    includes(
        apiModule,
        databaseModule,
        favoritesDatabaseModule,
        graphModule,
        repositoryModule,
        serviceModule
    )
}