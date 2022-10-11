package by.homework.hlazarseni.tfgridexplorer.data.di

import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.domain.repository.NodeRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf

import org.koin.dsl.module

val repositoryModule = module {
   // single { NodeRepositoryImpl(get(),get()) }
    singleOf(::NodeRepositoryImpl){
        bind<NodeRepository>()
    }
}