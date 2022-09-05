package by.homework.hlazarseni.tfgridexplorer.services

import by.homework.hlazarseni.tfgridexplorer.Node
import by.homework.hlazarseni.tfgridexplorer.entity.Stats
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GridProxy {

    @GET("nodes")
    fun getNodes(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Call<List<Node>>

    @GET("stats")
    fun getStats(): Call<Stats>
}