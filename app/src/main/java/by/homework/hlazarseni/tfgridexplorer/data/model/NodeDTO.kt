package by.homework.hlazarseni.tfgridexplorer.data.model

import com.google.gson.annotations.SerializedName


data class NodeDTO(
    val nodeId: String,
    val farmId: String,
    val status: String,
    val uptime: String,
    val country: String,
    val id: String,
    @SerializedName("total_resources")
    val totalResources: TotalResDTO,
    @SerializedName("used_resources")
    val usedResources: UsedResDTO
)
