package by.homework.hlazarseni.tfgridexplorer.data.model

data class Node(
    val nodeId: String,
    val farmId: String,
    val status: String,
    val uptime: String,
    val country: String,
    val id: String,
    val totalResources: TotalRes,
    val usedResources: UsedRes
)
