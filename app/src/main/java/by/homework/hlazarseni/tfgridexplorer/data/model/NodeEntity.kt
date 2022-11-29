package by.homework.hlazarseni.tfgridexplorer.data.model

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class NodeEntity(
    @PrimaryKey
    @NonNull
    val nodeId: String,
    val farmId: String,
    val status: String,
    val uptime: String,
    val country: String,
    val id: String,
    @Embedded
    @SerializedName("total_resources")
    val totalResources: TotalResEntity,
    @Embedded
    @SerializedName("used_resources")
    val usedResources: UsedResEntity
)
