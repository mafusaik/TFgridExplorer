package by.homework.hlazarseni.tfgridexplorer.data.mapper

import by.homework.hlazarseni.tfgridexplorer.data.model.*


fun List<NodeDTO>.toDomainModels(): List<Node> = map {
    it.toDomain()
}

fun List<NodeEntity>.toDomainModelsForEntity(): List<Node> = map {
    it.toDomain()
}

fun NodeDTO.toDomain(): Node {
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

    fun NodeEntity.toDomain(): Node {
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

    fun TotalResEntity.toDomain(): TotalRes {
        return TotalRes(
            cru = cru,
            sru = sru,
            hru = hru,
            mru = mru
        )
    }

    fun UsedResEntity.toDomain(): UsedRes {
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

    fun TotalRes.toDomain(): TotalResEntity {
        return TotalResEntity(
            cru = cru,
            sru = sru,
            hru = hru,
            mru = mru
        )
    }

    fun UsedRes.toDomain(): UsedResEntity {
        return UsedResEntity(
            cru = cru,
            sru = sru,
            hru = hru,
            mru = mru
        )
    }
