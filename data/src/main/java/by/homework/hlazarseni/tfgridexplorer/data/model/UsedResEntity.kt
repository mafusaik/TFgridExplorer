package by.homework.hlazarseni.tfgridexplorer.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class UsedResEntity(
    @ColumnInfo(name = "used_cru")
    val cru: Long,
    @ColumnInfo(name = "used_sru")
    val sru: Long,
    @ColumnInfo(name = "used_hru")
    val hru: Long,
    @ColumnInfo(name = "used_mru")
    val mru: Long
)