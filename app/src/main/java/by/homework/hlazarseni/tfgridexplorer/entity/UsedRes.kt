package by.homework.hlazarseni.tfgridexplorer.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class UsedRes(
    @ColumnInfo(name = "used_cru")
    val cru: Long,
    @ColumnInfo(name = "used_sru")
    val sru: Long,
    @ColumnInfo(name = "used_hru")
    val hru: Long,
    @ColumnInfo(name = "used_mru")
    val mru: Long
)