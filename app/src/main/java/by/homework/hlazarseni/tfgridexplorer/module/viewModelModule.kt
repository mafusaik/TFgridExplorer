package by.homework.hlazarseni.tfgridexplorer.module

import by.homework.hlazarseni.tfgridexplorer.model.NodeListViewModel
import by.homework.hlazarseni.tfgridexplorer.model.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule= module {
    viewModelOf(::NodeListViewModel)
    viewModelOf(::DetailViewModel)
}
