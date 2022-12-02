package by.homework.hlazarseni.tfgridexplorer.data.di

import by.homework.hlazarseni.tfgridexplorer.domain.service.NightModeService
import by.homework.hlazarseni.tfgridexplorer.data.service.preferences.PreferencesService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val serviceModule = module {
    singleOf(::PreferencesService) {
        bind<NightModeService>()
    }
}