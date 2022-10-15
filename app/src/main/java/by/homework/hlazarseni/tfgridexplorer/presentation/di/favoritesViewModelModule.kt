package by.homework.hlazarseni.tfgridexplorer.presentation.di


import by.homework.hlazarseni.tfgridexplorer.presentation.ui.favorites.FavoritesNodeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val favoritesViewModelModule= module {
    viewModelOf(::FavoritesNodeViewModel)
}
