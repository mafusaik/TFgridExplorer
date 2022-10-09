package by.homework.hlazarseni.tfgridexplorer.koin

import by.homework.hlazarseni.tfgridexplorer.entity.DetailNode
import by.homework.hlazarseni.tfgridexplorer.viewModel.NodeListViewModel
import by.homework.hlazarseni.tfgridexplorer.viewModel.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val detailViewModelModule= module {
    viewModelOf(::DetailViewModel)
    //viewModel { (id: DetailNode) -> DetailViewModel(id, get()) }
}
