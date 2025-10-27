package mhsn.wa_notes.ui.screens.threaddetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.io.File
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mhsn.wa_notes.R
import mhsn.wa_notes.data.local.entity.NoteEntity
import mhsn.wa_notes.ui.theme.MessageSentBubble
import mhsn.wa_notes.util.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadDetailScreen(
    onNavigateBack: () -> Unit,
    threadId: Long = 1L,
    onSearchClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val viewModel = remember { ThreadDetailViewModel(context, threadId) }
    val notes by viewModel.notes.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    
    var showMediaPicker by remember { mutableStateOf(false) }

    // Scroll to bottom when new note arrives
    LaunchedEffect(notes.size) {
        if (notes.isNotEmpty()) {
            listState.animateScrollToItem(notes.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = uiState.thread?.title ?: stringResource(R.string.thread_detail_default_title),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(R.string.thread_detail_notes_count, notes.size),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = stringResource(R.string.cd_thread_search),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            MessageInputBar(
                messageText = uiState.messageText,
                onMessageTextChange = { 
                    viewModel.onEvent(ThreadDetailEvent.MessageTextChanged(it))
                },
                onSendClick = {
                    if (uiState.messageText.isNotBlank()) {
                        viewModel.onEvent(ThreadDetailEvent.SendTextNote(uiState.messageText))
                    }
                },
                onAttachClick = {
                    showMediaPicker = true
                }
            )
        }
    ) { paddingValues ->
        // WhatsApp Style Attachment Menu
        WhatsAppStyleAttachmentMenu(
            showMediaPicker = showMediaPicker,
            onDismiss = { showMediaPicker = false },
            onMediaSelected = { filePath, type ->
                viewModel.onEvent(ThreadDetailEvent.SendMediaNote(filePath, type))
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            if (uiState.isLoading && notes.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (notes.isEmpty()) {
                EmptyNotesState(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                NotesList(
                    notes = notes,
                    listState = listState,
                    onNoteClick = { /* TODO: Note options */ }
                )
            }
        }
    }
}

@Composable
private fun NotesList(
    notes: List<NoteEntity>,
    listState: androidx.compose.foundation.lazy.LazyListState,
    onNoteClick: (NoteEntity) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = notes,
            key = { it.id }
        ) { note ->
            NoteItem(
                note = note,
                onClick = { onNoteClick(note) }
            )
        }
    }
}

@Composable
private fun NoteItem(
    note: NoteEntity,
    onClick: () -> Unit
) {
    // All notes are "sent" style (green bubble on right)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(modifier = Modifier.weight(0.2f))
        
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MessageSentBubble,
            shadowElevation = 1.dp,
            modifier = Modifier.weight(0.8f)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                when (note.type) {
                    "text" -> {
                        Text(
                            text = note.content ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }
                    "image" -> {
                        Column {
                            // Photo preview
                            note.filePath?.let { path ->
                                val file = File(path)
                                if (file.exists()) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(file)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = stringResource(R.string.cd_photo),
                                        modifier = Modifier
                                            .size(Constants.PHOTO_PREVIEW_SIZE_DP.dp)
                                            .clip(RoundedCornerShape(Constants.SMALL_CORNER_RADIUS_DP.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    // Fallback if file doesn't exist
                                    Box(
                                        modifier = Modifier
                                            .size(Constants.PHOTO_PREVIEW_SIZE_DP.dp)
                                            .background(Color.Gray.copy(alpha = 0.3f))
                                            .clip(RoundedCornerShape(Constants.SMALL_CORNER_RADIUS_DP.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = stringResource(R.string.note_type_photo),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            } ?: run {
                                // Fallback if no file path
                                Box(
                                    modifier = Modifier
                                        .size(Constants.PHOTO_PREVIEW_SIZE_DP.dp)
                                        .background(Color.Gray.copy(alpha = 0.3f))
                                        .clip(RoundedCornerShape(Constants.SMALL_CORNER_RADIUS_DP.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = stringResource(R.string.note_type_photo),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(Constants.SMALL_SPACING_DP.dp))
                            
                            // File name
                            note.filePath?.let { path ->
                                Text(
                                    text = path.substringAfterLast("/"),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                    "video" -> {
                        Text(
                            text = stringResource(R.string.note_type_video),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                        note.filePath?.let { path ->
                            Text(
                                text = path.substringAfterLast("/"),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                    "audio" -> {
                        Text(
                            text = stringResource(R.string.note_type_audio),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                        note.filePath?.let { path ->
                            Text(
                                text = path.substringAfterLast("/"),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                    "file" -> {
                        Text(
                            text = stringResource(R.string.note_type_file),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                        note.filePath?.let { path ->
                            Text(
                                text = path.substringAfterLast("/"),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(Constants.SMALL_SPACING_DP.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formatMessageTime(note.createdAt),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.width(Constants.SMALL_SPACING_DP.dp))
                    // Checkmark for saved
                    Text(
                        text = "✓",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageInputBar(
    messageText: String,
    onMessageTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onAttachClick: () -> Unit
) {
    Surface(
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            IconButton(onClick = onAttachClick) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.cd_add_file),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            TextField(
                value = messageText,
                onValueChange = onMessageTextChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text(stringResource(R.string.thread_detail_message_placeholder)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(24.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.width(8.dp))

            FloatingActionButton(
                onClick = onSendClick,
                modifier = Modifier.size(48.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = stringResource(R.string.cd_send),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun EmptyNotesState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.thread_detail_empty_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(Constants.MEDIUM_SPACING_DP.dp))
        Text(
            text = stringResource(R.string.thread_detail_empty_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun formatMessageTime(timestamp: Long): String {
    return SimpleDateFormat(Constants.TIME_FORMAT_HH_MM, Locale.getDefault()).format(Date(timestamp))
}

// WhatsApp Style Attachment Menu
@Composable
private fun WhatsAppStyleAttachmentMenu(
    showMediaPicker: Boolean,
    onDismiss: () -> Unit,
    onMediaSelected: (String, String) -> Unit
) {
    mhsn.wa_notes.ui.components.WhatsAppStyleAttachmentMenu(
        isVisible = showMediaPicker,
        onDismiss = onDismiss,
        onMediaSelected = onMediaSelected
    )
}


