package by.homework.hlazarseni.tfgridexplorer.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class TotalRes(
    @ColumnInfo(name = "total_cru")
    val cru: Long,
    @ColumnInfo(name = "total_sru")
    val sru: Long,
    @ColumnInfo(name = "total_hru")
    val hru: Long,
    @ColumnInfo(name = "total_mru")
    val mru: Long
)