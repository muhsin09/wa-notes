package mhsn.wa_notes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mhsn.wa_notes.data.local.dao.NoteDao
import mhsn.wa_notes.data.local.dao.ThreadDao
import mhsn.wa_notes.data.local.entity.NoteEntity
import mhsn.wa_notes.data.local.entity.ThreadEntity
import mhsn.wa_notes.util.Constants

@Database(
    entities = [ThreadEntity::class, NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun threadDao(): ThreadDao
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                .fallbackToDestructiveMigration(true)
                .build().also { INSTANCE = it }
            }
    }
}

