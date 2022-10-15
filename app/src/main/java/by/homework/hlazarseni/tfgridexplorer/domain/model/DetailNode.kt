package by.homework.hlazarseni.tfgridexplorer.domain.model

import androidx.annotation.Keep
import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import by.homework.hlazarseni.tfgridexplorer.data.model.TotalRes
import by.homework.hlazarseni.tfgridexplorer.data.model.UsedRes
import java.io.Serializable

@Keep
class DetailNode(node: Node) : Serializable {
    val uptime: String = node.uptime
    val nodeId: String = node.nodeId
    val farmId: String = node.farmId
    val status: String = node.status
    val country: String = node.country
    val totalResources: TotalRes = node.totalResources
    val usedResources: UsedRes = node.usedResources
}
