package by.homework.hlazarseni.tfgridexplorer

data class Node(
    val nodeId: String,
    val farmId: String,
    val status: String,
    val uptime: String,
    val country: String,
    val total_resources: TotalRes,
    val used_resources: UsedRes
)

data class TotalRes(
    val cru: Long,
    val sru: Long,
    val hru: Long,
    val mru: Long
)

data class UsedRes(
    val cru: Long,
    val sru: Long,
    val hru: Long,
    val mru: Long
)