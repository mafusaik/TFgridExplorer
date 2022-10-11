package by.homework.hlazarseni.tfgridexplorer.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.stats.StatsViewModel

val statsViewModelModule= module {
     viewModelOf(::StatsViewModel)
}