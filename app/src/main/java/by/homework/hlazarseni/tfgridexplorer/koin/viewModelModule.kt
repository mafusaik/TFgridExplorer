package by.homework.hlazarseni.tfgridexplorer.koin


import by.homework.hlazarseni.tfgridexplorer.entity.DetailNode
import by.homework.hlazarseni.tfgridexplorer.viewModel.DetailViewModel
import by.homework.hlazarseni.tfgridexplorer.viewModel.NodeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule= module {
    viewModelOf(::NodeListViewModel)
  //  viewModelOf(::DetailViewModel)
//    viewModel { NodeListViewModel(get(), get()) }
    viewModel { (id: DetailNode) -> DetailViewModel(id, get()) }
}
