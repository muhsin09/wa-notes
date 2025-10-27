package mhsn.wa_notes.data.repository

import kotlinx.coroutines.flow.Flow
import mhsn.wa_notes.data.local.dao.NoteDao
import mhsn.wa_notes.data.local.entity.NoteEntity

interface NoteRepository {
    fun getNotesByThreadFlow(threadId: Long): Flow<List<NoteEntity>>
    suspend fun getNotesByThread(threadId: Long): List<NoteEntity>
    suspend fun searchNotes(query: String): List<NoteEntity>
    suspend fun insertNote(note: NoteEntity): Long
    suspend fun updateNote(note: NoteEntity)
    suspend fun deleteNote(note: NoteEntity)
}

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getNotesByThreadFlow(threadId: Long): Flow<List<NoteEntity>> {
        return noteDao.getByThreadFlow(threadId)
    }

    override suspend fun getNotesByThread(threadId: Long): List<NoteEntity> {
        return noteDao.getByThread(threadId)
    }

    override suspend fun searchNotes(query: String): List<NoteEntity> {
        return noteDao.search(query)
    }

    override suspend fun insertNote(note: NoteEntity): Long {
        return noteDao.insert(note)
    }

    override suspend fun updateNote(note: NoteEntity) {
        noteDao.update(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        noteDao.delete(note)
    }
}

