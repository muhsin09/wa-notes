package mhsn.wa_notes.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MediaManager(private val context: Context) {
    
    private val internalStorageDir = File(context.filesDir, Constants.MEDIA_DIR_NAME)
    
    init {
        if (!internalStorageDir.exists()) {
            internalStorageDir.mkdirs()
        }
    }
    
    fun saveFileFromUri(uri: Uri, fileName: String? = null): String? {
        return try {
            val inputStream: InputStream = context.contentResolver.openInputStream(uri) ?: return null
            val finalFileName = fileName ?: generateFileName(uri)
            val file = File(internalStorageDir, finalFileName)
            
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
            
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    fun deleteFile(filePath: String): Boolean {
        return try {
            val file = File(filePath)
            file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    fun getFileUri(filePath: String): Uri? {
        return try {
            val file = File(filePath)
            if (file.exists()) {
                Uri.fromFile(file)
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    private fun generateFileName(uri: Uri): String {
        val timestamp = SimpleDateFormat(Constants.TIMESTAMP_FORMAT, Locale.getDefault()).format(Date())
        val extension = getFileExtension(uri) ?: "file"
        return "${Constants.FILE_NAME_PREFIX}${timestamp}.${extension}"
    }
    
    private fun getFileExtension(uri: Uri): String? {
        return context.contentResolver.getType(uri)?.substringAfterLast("/")
    }
    
    fun getMediaType(uri: Uri): String {
        val mimeType = context.contentResolver.getType(uri) ?: return Constants.NOTE_TYPE_FILE
        return when {
            mimeType.startsWith(Constants.MIME_TYPE_IMAGE_PREFIX) -> Constants.NOTE_TYPE_IMAGE
            mimeType.startsWith(Constants.MIME_TYPE_VIDEO_PREFIX) -> Constants.NOTE_TYPE_VIDEO
            mimeType.startsWith(Constants.MIME_TYPE_AUDIO_PREFIX) -> Constants.NOTE_TYPE_AUDIO
            else -> Constants.NOTE_TYPE_FILE
        }
    }
}

@Composable
fun rememberMediaManager(): MediaManager {
    val context = LocalContext.current
    return remember { MediaManager(context) }
}
