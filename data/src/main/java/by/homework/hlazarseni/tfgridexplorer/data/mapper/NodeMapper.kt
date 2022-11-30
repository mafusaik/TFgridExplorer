package by.homework.hlazarseni.tfgridexplorer.data.mapper

import by.homework.hlazarseni.tfgridexplorer.data.model.*
import by.homework.hlazarseni.tfgridexplorer.domain.model.Node
import by.homework.hlazarseni.tfgridexplorer.domain.model.TotalRes
import by.homework.hlazarseni.tfgridexplorer.domain.model.UsedRes


internal fun List<NodeDTO>.toDomainModels(): List<Node> = map {
    it.toDomain()
}

internal fun List<NodeEntity>.toDomainModelsForEntity(): List<Node> = map {
    it.toDomain()
}

internal fun NodeDTO.toDomain(): Node {
    return Node(
        nodeId = nodeId,
        farmId = farmId,
        status = status,
        uptime = uptime,
        country = country,
        id = id,
        totalResources = totalResources.toDomain(),
        usedResources = usedResources.toDomain()
    )
}

internal fun TotalResDTO.toDomain(): TotalRes {
    return TotalRes(
        cru = cru,
        sru = sru,
        hru = hru,
        mru = mru
    )
}

internal fun UsedResDTO.toDomain(): UsedRes {
    return UsedRes(
        cru = cru,
        sru = sru,
        hru = hru,
        mru = mru
    )
}

internal fun NodeEntity.toDomain(): Node {
    return Node(
        nodeId = nodeId,
        farmId = farmId,
        status = status,
        uptime = uptime,
        country = country,
        id = id,
        totalResources = totalResources.toDomain(),
        usedResources = usedResources.toDomain()
    )
}

internal fun TotalResEntity.toDomain(): TotalRes {
    return TotalRes(
        cru = cru,
        sru = sru,
        hru = hru,
        mru = mru
    )
}

internal fun UsedResEntity.toDomain(): UsedRes {
    return UsedRes(
        cru = cru,
        sru = sru,
        hru = hru,
        mru = mru
    )
}

fun Node.toDomain(): NodeEntity {
    return NodeEntity(
        nodeId = nodeId,
        farmId = farmId,
        status = status,
        uptime = uptime,
        country = country,
        id = id,
        totalResources = totalResources.toDomain(),
        usedResources = usedResources.toDomain()
    )
}

internal fun TotalRes.toDomain(): TotalResEntity {
    return TotalResEntity(
        cru = cru,
        sru = sru,
        hru = hru,
        mru = mru
    )
}

internal fun UsedRes.toDomain(): UsedResEntity {
    return UsedResEntity(
        cru = cru,
        sru = sru,
        hru = hru,
        mru = mru
    )
}
