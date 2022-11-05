package by.homework.hlazarseni.tfgridexplorer.data.api

import by.homework.NodeByIdQuery


interface GridGraphQL {
    suspend fun NodeById(
        nodeByIdId: String
    ): NodeByIdQuery.Location
}