package by.homework.hlazarseni.tfgridexplorer.module

import by.homework.hlazarseni.tfgridexplorer.model.NodeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val listViewModelModule= module {
    viewModelOf(::NodeListViewModel) }
