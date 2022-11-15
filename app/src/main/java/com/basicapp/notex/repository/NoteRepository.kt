package com.basicapp.notex.repository

import androidx.lifecycle.LiveData

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.basicapp.notex.database.dao.NoteDao
import com.basicapp.notex.model.Note


//Class này cung cấp API để lấy dữ liệu từ nguồn dữ liệu (csdl hoặc mạng)
class NoteRepository(private val noteDao: NoteDao) {
    // tạo biến lấy hết note từ DAO đẩy về list
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    // thêm.
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    // xóa
    suspend fun delete(note: Note){
        noteDao.delete(note)
    }

    // cập nhật
    suspend fun update(note: Note){
        noteDao.update(note)
    }
    //xóa hết
    suspend fun deleteAllNotes(){
        noteDao.deleteAllNotes()
    }


    /**
     * Retrieve an note from the repository.
     */
    fun retrieveNote(id: Int): LiveData<Note> {
        return noteDao.getNote(id)
    }




}