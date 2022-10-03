package by.homework.hlazarseni.tfgridexplorer.entity

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class DetailNode(node: Node) : Serializable {
    val uptime: String = node.uptime
    val nodeId: String = node.nodeId
    val farmId: String = node.farmId
    val status: String = node.status
    val country: String = node.country
    val totalResources: TotalRes = node.total_resources
    val usedResources: UsedRes = node.used_resources

}
