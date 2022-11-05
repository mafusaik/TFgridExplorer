package by.homework.hlazarseni.tfgridexplorer.data.di

import by.homework.hlazarseni.tfgridexplorer.data.api.GridProxy
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://gridproxy.grid.tf/"

val apiModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create<GridProxy>() }
}