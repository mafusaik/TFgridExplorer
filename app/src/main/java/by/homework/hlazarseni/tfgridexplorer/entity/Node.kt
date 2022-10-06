package by.homework.hlazarseni.tfgridexplorer.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Node(
    @PrimaryKey
    @NonNull
    val nodeId: String,
    val farmId: String,
    val status: String,
    val uptime: String,
    val country: String,
    @Embedded
    val total_resources: TotalRes,
    @Embedded
    val used_resources: UsedRes
)
