package mhsn.wa_notes.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes",
    foreignKeys = [ForeignKey(
        entity = ThreadEntity::class,
        parentColumns = ["id"],
        childColumns = ["threadId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("threadId")]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val threadId: Long,
    val content: String? = null,
    val type: String = "text", // text, image, video, audio, file
    val filePath: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

