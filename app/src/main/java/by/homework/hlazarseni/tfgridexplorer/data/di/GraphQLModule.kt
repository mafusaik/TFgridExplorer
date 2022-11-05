package by.homework.hlazarseni.tfgridexplorer.data.di

import com.apollographql.apollo3.ApolloClient
import org.koin.dsl.module


private const val BASE_URL = "https://graphql.grid.tf/graphql"

val graphModule = module {
    single {
       ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .build()
    }
}