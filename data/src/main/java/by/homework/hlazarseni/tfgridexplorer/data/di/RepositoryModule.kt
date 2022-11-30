package by.homework.hlazarseni.tfgridexplorer.data.di

import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeDatabaseRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeFavoriteDatabaseRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.domain.repository.NodeDatabaseRepository
import by.homework.hlazarseni.tfgridexplorer.domain.repository.NodeFavoriteDatabaseRepository
import by.homework.hlazarseni.tfgridexplorer.domain.repository.NodeRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf(::NodeRepositoryImpl){
        bind<NodeRepository>()
    }
    singleOf(::NodeDatabaseRepositoryImpl){
        bind<NodeDatabaseRepository>()
    }
    singleOf(::NodeFavoriteDatabaseRepositoryImpl){
        bind<NodeFavoriteDatabaseRepository>()
    }
}