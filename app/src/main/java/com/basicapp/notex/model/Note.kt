package com.basicapp.notex.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Đối tượng Note.
 */
@Entity(tableName = "notes_table")

class Note(
    // khóa chính tự khởi tạo
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title") val noteTitle: String,
    @ColumnInfo(name = "content") val noteContent: String,
    @ColumnInfo(name = "timestamp") val timeStamp: String
)