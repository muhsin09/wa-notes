package mhsn.wa_notes.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import mhsn.wa_notes.util.MediaManager
import mhsn.wa_notes.util.rememberMediaManager

@Composable
fun WhatsAppStyleAttachmentMenu(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onMediaSelected: (String, String) -> Unit // filePath, type
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            AttachmentMenuContent(
                onDismiss = onDismiss,
                onMediaSelected = onMediaSelected
            )
        }
    }
}

@Composable
private fun AttachmentMenuContent(
    onDismiss: () -> Unit,
    onMediaSelected: (String, String) -> Unit
) {
    val context = LocalContext.current
    val mediaManager = rememberMediaManager()
    
    // File picker launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val filePath = mediaManager.saveFileFromUri(it)
            val mediaType = mediaManager.getMediaType(it)
            if (filePath != null) {
                onMediaSelected(filePath, mediaType)
            }
        }
        onDismiss()
    }
    
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val filePath = mediaManager.saveFileFromUri(it)
            if (filePath != null) {
                onMediaSelected(filePath, "image")
            }
        }
        onDismiss()
    }
    
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val filePath = mediaManager.saveFileFromUri(it)
            if (filePath != null) {
                onMediaSelected(filePath, "video")
            }
        }
        onDismiss()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f)),
        contentAlignment = Alignment.BottomStart
    ) {
        // Attachment menu
        Card(
            modifier = Modifier
                .padding(16.dp)
                .width(280.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF2A2A2A)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Header
                Text(
                    text = "Ekle",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Menu items
                AttachmentMenuItem(
                    icon = Icons.Default.Add,
                    title = "Dosya",
                    subtitle = "Belge, PDF, vb.",
                    iconColor = Color(0xFF4FC3F7),
                    onClick = {
                        filePickerLauncher.launch("*/*")
                    }
                )
                
                AttachmentMenuItem(
                    icon = Icons.Default.Add,
                    title = "Fotoğraf ve Video",
                    subtitle = "Galeriden seç",
                    iconColor = Color(0xFF81C784),
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                    }
                )
            }
        }
    }
}

@Composable
private fun AttachmentMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconColor: Color,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "scale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (isPressed) 0.7f else 1f,
        animationSpec = tween(100),
        label = "alpha"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .scale(scale)
            .alpha(alpha),
        onClick = {
            isPressed = true
            onClick()
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = iconColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Text content
            Column {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = subtitle,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}
