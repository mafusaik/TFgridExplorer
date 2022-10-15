package by.homework.hlazarseni.tfgridexplorer.data.mapper

import by.homework.hlazarseni.tfgridexplorer.data.model.NodeDTO
import by.homework.hlazarseni.tfgridexplorer.data.model.TotalResDTO
import by.homework.hlazarseni.tfgridexplorer.data.model.UsedResDTO
import by.homework.hlazarseni.tfgridexplorer.data.model.Node
import by.homework.hlazarseni.tfgridexplorer.data.model.TotalRes
import by.homework.hlazarseni.tfgridexplorer.data.model.UsedRes

fun List<NodeDTO>.toDomainModels(): List<Node> = map {
    it.toDomain()
}

fun NodeDTO.toDomain(): Node {
    return Node(
        nodeId = nodeId,
        farmId = farmId,
        status = status,
        uptime = uptime,
        country = country,
        totalResources = totalResources.toDomain(),
        usedResources = usedResources.toDomain()
    )
}

fun TotalResDTO.toDomain(): TotalRes {
    return TotalRes(
        cru = cru,
        sru = sru,
        hru = hru,
        mru = mru
    )
}

fun UsedResDTO.toDomain(): UsedRes {
    return UsedRes(
        cru = cru,
        sru = sru,
        hru = hru,
        mru = mru
    )
}