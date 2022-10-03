package by.homework.hlazarseni.tfgridexplorer.entity

import androidx.room.Entity

//@Entity
data class Node(
    val nodeId: String,
    val farmId: String,
    val status: String,
    val uptime: String,
    val country: String,
    val total_resources: TotalRes,
    val used_resources: UsedRes
)
