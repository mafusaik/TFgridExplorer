package by.homework.hlazarseni.tfgridexplorer

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