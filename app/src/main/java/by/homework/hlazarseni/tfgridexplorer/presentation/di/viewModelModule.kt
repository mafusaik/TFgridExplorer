package by.homework.hlazarseni.tfgridexplorer.presentation.di

import by.homework.hlazarseni.tfgridexplorer.presentation.ui.list.NodeListViewModel
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.stats.StatsViewModel
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.favorites.FavoritesNodeViewModel
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.detail.DetailViewModel
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.map.MapViewModel
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.nightmode.NightModeViewModel

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule= module {
    viewModelOf(::NodeListViewModel)
    viewModelOf(::StatsViewModel)
    viewModelOf(::FavoritesNodeViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::MapViewModel)
    viewModelOf(::NightModeViewModel)
}
