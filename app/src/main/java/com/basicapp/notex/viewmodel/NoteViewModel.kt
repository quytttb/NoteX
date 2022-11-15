package com.basicapp.notex.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.basicapp.notex.database.NoteDatabase
import com.basicapp.notex.model.Note
import com.basicapp.notex.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : ViewModel() {

    // tạo list tất cả ghi chú và repository
    val allNotes: LiveData<List<Note>>
    private val repository: NoteRepository

    // khởi tạo repository và allNotes
    init {
        val dao = NoteDatabase.getInstance(application).noteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }


    /**
     * Trả về true nếu EditText có dữ liệu
     */
    fun isDataValid(noteTitle: String, noteContent: String): Boolean {
        if (noteTitle.isBlank() || noteContent.isBlank()) {
            return false
        }
        return true
    }

    /**
     * Lấy 1 ghi chú từ repo
     */
    fun retrieveNote(id: Int): LiveData<Note> {
        return repository.retrieveNote(id)
    }

    // phương thức thêm ghi chú
    // gọi phương thức từ repository
    private fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    /**
     * Trả về 1 instance của lớp [Note] với dữ liệu được người dùng nhập.
     * Thêm 1 bản ghi mới vào csdl.
     */

    private fun getNewNoteData(
        noteTitle: String,
        noteContent: String,
        timeStamp: String
    ): Note {
        return Note(
            noteTitle = noteTitle,
            noteContent = noteContent,
            timeStamp = timeStamp
        )
    }

    /**
     * constructor
     */
    fun addNote(
        noteTitle: String,
        noteContent: String,
        timeStamp: String
    ) {
        val newNote = getNewNoteData(noteTitle, noteContent, timeStamp)
        addNote(newNote)
    }


    // phương thức cập nhật ghi chú
    // gọi phương thức từ repository
    private fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    /**
     * Trả về 1 instance của lớp [Note] với dữ liệu được người dùng nhập, kèm theo id để cập nhật bản ghi đã có trong csdl
     */
    private fun getUpdateNoteData(
        id: Int,
        noteTitle: String,
        noteContent: String,
        timeStamp: String
    ): Note {
        return Note(
            id = id,
            noteTitle = noteTitle,
            noteContent = noteContent,
            timeStamp = timeStamp
        )
    }

    /**
     * constructor
     */
    fun updateNote(
        id: Int,
        noteTitle: String,
        noteContent: String,
        timeStamp: String
    ) {
        val updateNote = getUpdateNoteData(id, noteTitle, noteContent, timeStamp)
        updateNote(updateNote)
    }


    // phương thức xóa ghi chú
    // gọi phương thức từ repository
    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    // phương thức xóa hết ghi chú
    // gọi phương thức từ repository
    fun deleteAllNotes(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllNotes()
    }

    /**
     * Factory class để khởi tạo [ViewModel] instance.
     */
    class NoteViewModelFactory(private val application: Application?) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return application?.let { NoteViewModel(it) } as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


