package by.homework.hlazarseni.tfgridexplorer.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class DetailNode(node: NodeEntity) : Serializable {
    val uptime: String = node.uptime
    val nodeId: String = node.nodeId
    val farmId: String = node.farmId
    val status: String = node.status
    val country: String = node.country
    val id: String = node.id
    val totalResources: TotalResEntity = node.totalResources
    val usedResources: UsedResEntity = node.usedResources
}
