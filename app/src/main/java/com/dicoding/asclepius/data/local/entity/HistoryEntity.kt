package com.dicoding.asclepius.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
class HistoryEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "photo")
    var photo: String? = null,

    @ColumnInfo(name = "result")
    var result: String? = null,

    @ColumnInfo(name = "isSaved")
    var isSaved: Boolean = false
)