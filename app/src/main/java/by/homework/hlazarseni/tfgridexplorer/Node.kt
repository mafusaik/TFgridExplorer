package by.homework.hlazarseni.tfgridexplorer

data class Node(
    val nodeId: String,
    val farmId: String,
    val status: String,
    val uptime: String,
    val country: String,
    val nodes: String,
    val farms: String,
    val countries: String,
    val totalCru: String,
    val totalSru: Int,
    val totalMru: Int,
    val totalHru: Int
)