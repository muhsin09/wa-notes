package mhsn.wa_notes.data.repository

import kotlinx.coroutines.flow.Flow
import mhsn.wa_notes.data.local.dao.ThreadDao
import mhsn.wa_notes.data.local.entity.ThreadEntity

interface ThreadRepository {
    fun getAllThreadsFlow(): Flow<List<ThreadEntity>>
    suspend fun getAllThreads(): List<ThreadEntity>
    suspend fun getThreadById(threadId: Long): ThreadEntity?
    suspend fun insertThread(thread: ThreadEntity): Long
    suspend fun updateThread(thread: ThreadEntity)
    suspend fun deleteThread(thread: ThreadEntity)
}

class ThreadRepositoryImpl(
    private val threadDao: ThreadDao
) : ThreadRepository {

    override fun getAllThreadsFlow(): Flow<List<ThreadEntity>> {
        return threadDao.getAllFlow()
    }

    override suspend fun getAllThreads(): List<ThreadEntity> {
        return threadDao.getAll()
    }

    override suspend fun getThreadById(threadId: Long): ThreadEntity? {
        return threadDao.getById(threadId)
    }

    override suspend fun insertThread(thread: ThreadEntity): Long {
        return threadDao.insert(thread)
    }

    override suspend fun updateThread(thread: ThreadEntity) {
        threadDao.update(thread)
    }

    override suspend fun deleteThread(thread: ThreadEntity) {
        threadDao.delete(thread)
    }
}

