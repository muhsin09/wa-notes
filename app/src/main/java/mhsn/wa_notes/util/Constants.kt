package mhsn.wa_notes.util

object Constants {
    
    // Database
    const val DATABASE_NAME = "wa_notes.db"
    const val DATABASE_VERSION = 1
    
    // Default Values
    const val DEFAULT_THREAD_TITLE = "Varsayılan Notlar"
    const val DEFAULT_THREAD_ID = 1L
    
    // UI Dimensions
    const val PHOTO_PREVIEW_SIZE_DP = 200
    const val AVATAR_SIZE_DP = 56
    const val FAB_SIZE_DP = 48
    const val ICON_SIZE_DP = 24
    const val LARGE_ICON_SIZE_DP = 64
    
    // Padding and Spacing
    const val DEFAULT_PADDING_DP = 8
    const val MEDIUM_PADDING_DP = 16
    const val LARGE_PADDING_DP = 32
    const val SMALL_SPACING_DP = 4
    const val MEDIUM_SPACING_DP = 8
    
    // Corner Radius
    const val SMALL_CORNER_RADIUS_DP = 8
    const val MEDIUM_CORNER_RADIUS_DP = 12
    const val LARGE_CORNER_RADIUS_DP = 16
    const val EXTRA_LARGE_CORNER_RADIUS_DP = 24
    
    // Elevation
    const val LOW_ELEVATION_DP = 1
    const val MEDIUM_ELEVATION_DP = 2
    const val HIGH_ELEVATION_DP = 4
    const val VERY_HIGH_ELEVATION_DP = 8
    
    // Animation Durations
    const val SHORT_ANIMATION_DURATION_MS = 100
    const val MEDIUM_ANIMATION_DURATION_MS = 300
    const val LONG_ANIMATION_DURATION_MS = 500
    
    // Search
    const val SEARCH_DEBOUNCE_MS = 300
    const val SEARCH_MIN_QUERY_LENGTH = 1
    
    // File Management
    const val MEDIA_DIR_NAME = "media"
    const val FILE_NAME_PREFIX = "media_"
    const val TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss"
    
    // Time Formats
    const val TIME_FORMAT_HH_MM = "HH:mm"
    const val DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy"
    const val DATE_FORMAT_DD_MMM_YYYY_HH_MM = "dd MMM yyyy, HH:mm"
    const val DAY_FORMAT_EEE = "EEE"
    
    // Time Thresholds (milliseconds)
    const val ONE_MINUTE_MS = 60_000L
    const val ONE_HOUR_MS = 3_600_000L
    const val ONE_DAY_MS = 86_400_000L
    const val ONE_WEEK_MS = 604_800_000L
    
    // Note Types
    const val NOTE_TYPE_TEXT = "text"
    const val NOTE_TYPE_IMAGE = "image"
    const val NOTE_TYPE_VIDEO = "video"
    const val NOTE_TYPE_AUDIO = "audio"
    const val NOTE_TYPE_FILE = "file"
    
    // MIME Types
    const val MIME_TYPE_IMAGE_PREFIX = "image/"
    const val MIME_TYPE_VIDEO_PREFIX = "video/"
    const val MIME_TYPE_AUDIO_PREFIX = "audio/"
    const val MIME_TYPE_ALL_FILES = "*/*"
    const val MIME_TYPE_IMAGE_ALL = "image/*"
    
    // Error Messages
    const val ERROR_THREAD_LOAD_FAILED = "Thread yüklenemedi"
    const val ERROR_NOTE_SEND_FAILED = "Not gönderilemedi"
    const val ERROR_MEDIA_SEND_FAILED = "Medya gönderilemedi"
    const val ERROR_THREAD_CREATE_FAILED = "Thread oluşturulamadı"
    const val ERROR_THREAD_UPDATE_FAILED = "Thread güncellenemedi"
    const val ERROR_THREAD_DELETE_FAILED = "Thread silinemedi"
    const val ERROR_NOTE_UPDATE_FAILED = "Not güncellenemedi"
    const val ERROR_NOTE_DELETE_FAILED = "Not silinemedi"
    const val ERROR_SEARCH_FAILED = "Arama sırasında hata oluştu"
    const val ERROR_DEFAULT_THREAD_CREATE_FAILED = "Varsayılan thread oluşturulamadı"
    
    // UI Text
    const val TEXT_NO_NOTES_YET = "Henüz not yok"
    const val TEXT_NO_THREADS_YET = "Henüz not defteriniz yok"
    const val TEXT_CREATE_FIRST_NOTE = "İlk notunuzu eklemek için aşağıdaki mesaj kutusunu kullanın"
    const val TEXT_CREATE_FIRST_THREAD = "Yeni bir not defteri oluşturmak için + butonuna tıklayın"
    const val TEXT_SEARCH_IN_NOTES = "Notlarınızda arama yapın"
    const val TEXT_SEARCH_HINT = "Arama yapmak için yukarıdaki kutuya yazın"
    const val TEXT_NO_RESULTS_FOUND = "Sonuç bulunamadı"
    const val TEXT_TRY_DIFFERENT_KEYWORDS = "Farklı anahtar kelimeler deneyin"
    const val TEXT_ERROR = "Hata"
    const val TEXT_RETRY = "Tekrar Dene"
    const val TEXT_CREATE = "Oluştur"
    const val TEXT_CANCEL = "İptal"
    const val TEXT_NEW_NOTEBOOK = "Yeni Not Defteri"
    const val TEXT_TITLE = "Başlık"
    const val TEXT_MESSAGE_PLACEHOLDER = "Mesaj yazın..."
    const val TEXT_SEARCH_PLACEHOLDER = "Notlarda ara..."
    const val TEXT_CLEAR = "Temizle"
    const val TEXT_SEARCH = "Ara"
    const val TEXT_BACK = "Geri"
    const val TEXT_SEND = "Gönder"
    const val TEXT_ADD_FILE = "Dosya Ekle"
    const val TEXT_NEW_THREAD = "Yeni Thread"
    const val TEXT_MENU = "Menü"
    const val TEXT_THREAD_SEARCH = "Thread İçinde Ara"
    const val TEXT_SEARCH_ALL_NOTES = "Tüm Notlarda Ara"
    const val TEXT_ALL_NOTES = "Tüm Notlar"
    const val TEXT_THIS_THREAD = "Bu Thread"
    const val TEXT_NOTES = "Notlar"
    const val TEXT_NOW = "Şimdi"
    const val TEXT_MINUTES_AGO = "dk"
    const val TEXT_ADD = "Ekle"
    const val TEXT_FILE = "Dosya"
    const val TEXT_FILE_SUBTITLE = "Belge, PDF, vb."
    const val TEXT_PHOTO_VIDEO = "Fotoğraf ve Video"
    const val TEXT_PHOTO_VIDEO_SUBTITLE = "Galeriden seç"
    const val TEXT_PHOTO = "Fotoğraf"
    const val TEXT_VIDEO = "Video"
    const val TEXT_AUDIO_RECORDING = "Ses Kaydı"
    const val TEXT_FILE_ATTACHMENT = "Dosya"
}
