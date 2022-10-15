package by.homework.hlazarseni.tfgridexplorer.presentation.di

import by.homework.hlazarseni.tfgridexplorer.presentation.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val detailViewModelModule= module {
    viewModelOf(::DetailViewModel)
}
