package com.basicapp.notex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.basicapp.notex.database.dao.NoteDao
import com.basicapp.notex.model.Note

//csdl của ghi chú
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        // Singleton chặn nhiều instance của csdl mở cùng lúc
        @Volatile //Thông báo ngay khi có thay đổi giá trị ở các luồng truy xuất tới biến này
        private var instance: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase {
/*
            Nếu instance null thì khởi tạo csdl
            Nếu không thì trả về nó
*/
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
                //Toán tử Elvis ?: là toán tử rút gọn, nếu biểu thức trước nó không null thì trả về nó, nếu null thì trả về biểu thức sau nó

            }
        }

        //Khởi tạo csdl
        private fun buildDatabase(context: Context): NoteDatabase {
            return Room.databaseBuilder(context, NoteDatabase::class.java, "note_database")
                // Wipes and rebuilds instead of migrating if no Migration object.
                // Migration is not part of this codelab.
                .fallbackToDestructiveMigration()
                .build()
        }

    }

}