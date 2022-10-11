package by.homework.hlazarseni.tfgridexplorer.presentation.di

import by.homework.hlazarseni.tfgridexplorer.presentation.ui.list.NodeListViewModel


import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val listViewModelModule= module {
    viewModelOf(::NodeListViewModel)

   // viewModel { (id: DetailNode) -> DetailViewModel(id, get()) }
}
