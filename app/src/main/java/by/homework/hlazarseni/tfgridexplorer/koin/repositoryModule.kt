package by.homework.hlazarseni.tfgridexplorer.koin

import by.homework.hlazarseni.tfgridexplorer.database.NodeRepository

import org.koin.dsl.module

val repositoryModule = module {
    single {
        NodeRepository(get(),get())
       // viewModel { (id: DetailNode) -> NodeRepository(get(),get(),id) }
    }
}