package by.homework.hlazarseni.tfgridexplorer.services

import by.homework.hlazarseni.tfgridexplorer.entity.Node
import by.homework.hlazarseni.tfgridexplorer.entity.PagingData
import by.homework.hlazarseni.tfgridexplorer.entity.Stats
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GridProxy {


    @GET("stats")
    fun getStats(): Call<Stats>

    @GET("nodes")
    suspend fun getNodes(
        @Query("page") page: Int
    ): List<Node>

    @GET("stats")
    suspend fun getStats2(): Stats

    @GET("nodes/{id}")
    suspend fun getNode(
        @Path("id") id: Int
    ): Node
}