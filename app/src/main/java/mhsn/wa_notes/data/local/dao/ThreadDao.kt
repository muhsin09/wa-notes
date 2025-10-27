package mhsn.wa_notes.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mhsn.wa_notes.data.local.entity.ThreadEntity

@Dao
interface ThreadDao {
    @Query("SELECT * FROM threads ORDER BY updatedAt DESC")
    fun getAllFlow(): Flow<List<ThreadEntity>>

    @Query("SELECT * FROM threads ORDER BY updatedAt DESC")
    suspend fun getAll(): List<ThreadEntity>

    @Query("SELECT * FROM threads WHERE id = :threadId")
    suspend fun getById(threadId: Long): ThreadEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(thread: ThreadEntity): Long

    @Update
    suspend fun update(thread: ThreadEntity)

    @Delete
    suspend fun delete(thread: ThreadEntity)
}

