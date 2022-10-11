package by.homework.hlazarseni.tfgridexplorer.data.model


data class NodeDTO(
    val nodeId: String,
    val farmId: String,
    val status: String,
    val uptime: String,
    val country: String,
    val total_resources: TotalResDTO,
    val used_resources: UsedResDTO
)
