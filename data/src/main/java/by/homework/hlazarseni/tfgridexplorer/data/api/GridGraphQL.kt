package by.homework.hlazarseni.tfgridexplorer.data.api

import by.homework.NodeByIdQuery


internal interface GridGraphQL {
    suspend fun NodeById(
        nodeByIdId: String
    ): NodeByIdQuery.Location
}