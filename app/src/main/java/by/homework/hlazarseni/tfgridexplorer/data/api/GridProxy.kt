package by.homework.hlazarseni.tfgridexplorer.data.api

import by.homework.hlazarseni.tfgridexplorer.data.model.NodeDTO
import by.homework.hlazarseni.tfgridexplorer.data.model.Stats
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GridProxy {

    @GET("nodes")
    suspend fun getNodes(
        @Query("page") page: Int
    ): List<NodeDTO>

    @GET("nodes/{id}")
    suspend fun getNode(
        @Path("id") id: Int
    ): NodeDTO

    @GET("stats")
   suspend fun getStats(): Stats
}