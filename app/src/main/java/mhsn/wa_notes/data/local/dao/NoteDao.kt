package mhsn.wa_notes.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mhsn.wa_notes.data.local.entity.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE threadId = :threadId ORDER BY createdAt ASC")
    fun getByThreadFlow(threadId: Long): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE threadId = :threadId ORDER BY createdAt ASC")
    suspend fun getByThread(threadId: Long): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE content IS NOT NULL AND content LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    suspend fun search(query: String): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity): Long

    @Update
    suspend fun update(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)
}

