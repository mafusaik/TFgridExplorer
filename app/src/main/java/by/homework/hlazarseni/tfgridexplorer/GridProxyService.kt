package by.homework.hlazarseni.tfgridexplorer

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object GridProxyService {

    private const val BASE_URL = "https://gridproxy.grid.tf/"

    val api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create<GridProxy>()
    }
}