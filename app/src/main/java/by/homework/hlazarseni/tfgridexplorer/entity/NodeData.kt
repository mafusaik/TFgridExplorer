package by.homework.hlazarseni.tfgridexplorer.entity

import androidx.room.Entity

@Entity
data class NodeData(
    val nodeId: String,
    val farmId: String,
    val status: String,
    val uptime: String,
    val country: String

)
