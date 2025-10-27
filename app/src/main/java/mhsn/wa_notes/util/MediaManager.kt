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
    
    private val internalStorageDir = File(context.filesDir, "media")
    
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
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val extension = getFileExtension(uri) ?: "file"
        return "media_${timestamp}.${extension}"
    }
    
    private fun getFileExtension(uri: Uri): String? {
        return context.contentResolver.getType(uri)?.substringAfterLast("/")
    }
    
    fun getMediaType(uri: Uri): String {
        val mimeType = context.contentResolver.getType(uri) ?: return "file"
        return when {
            mimeType.startsWith("image/") -> "image"
            mimeType.startsWith("video/") -> "video"
            mimeType.startsWith("audio/") -> "audio"
            else -> "file"
        }
    }
}

@Composable
fun rememberMediaManager(): MediaManager {
    val context = LocalContext.current
    return remember { MediaManager(context) }
}
