package by.homework.hlazarseni.tfgridexplorer.presentation.di

import by.homework.hlazarseni.tfgridexplorer.domain.model.DetailNode
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailViewModelModule= module {
   // viewModelOf(::DetailViewModel)
    viewModel { (id: DetailNode) -> DetailViewModel(id, get()) }
}
