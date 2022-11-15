package com.basicapp.notex.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.basicapp.notex.model.Note

@Dao
interface NoteDao {
    //Nếu dữ liệu người dùng thêm vào bị xung đột thì sẽ bỏ qua
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    //cập nhật
    @Update
    suspend fun update(note: Note)

    //xóa
    @Delete
    suspend fun delete(note: Note)

    //lấy tất cả các ghi chú
    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    //tìm kiếm theo tiêu đề
    @Query("SELECT * FROM notes_table WHERE id = :id")
    fun getNote(id: Int): LiveData<Note>

    //xóa tất cả
    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNotes()
}