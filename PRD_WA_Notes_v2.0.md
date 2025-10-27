### Ürün Gereksinim Belgesi (PRD) - WhatsApp as Notes (Versiyon 2.0 - Nihai Sürüm)

| Belge Versiyonu | Tarih | Hazırlayan |
| :--- | :--- | :--- |
| **2.0 (Nihai Sürüm)** | 26 Ekim 2025 | Yapay Zeka Asistanı |

### 1. Giriş

#### 1.1 Amaç
Bu belge, WhatsApp kullanıcı arayüzü ve temel fonksiyonlarını temel alarak geliştirilecek, tamamen çevrimdışı çalışan bir Android not alma uygulaması olan **"WA Notes"** mobil uygulamasının özelliklerini ve gereksinimlerini tanımlar.

#### 1.2 Hedef Kitle
Hızlı, tanıdık ve kesintisiz not alma deneyimi arayan, notlarını konuya göre sohbet dizisi (thread) şeklinde düzenlemek isteyen, çevrimdışı çalışabilme özelliği öncelikli olan Android kullanıcıları.

#### 1.3 Ürün Vizyonu
"WA Notes", kullanıcıların WhatsApp'ın basit ve tanıdık arayüzünü kullanarak, internet bağlantısına ihtiyaç duymadan, her bir konuya özel bir sohbet penceresinde notlarını organize etmelerini sağlayan en kolay ve hızlı not alma uygulaması olmaktır.

### 2. Ürün Özellikleri ve Kapsam

#### 2.1 Temel Konsept
* **"Thread" = "Konu Notu"**: Uygulamadaki her bir sohbet (chat) penceresi, bir not defteri/konu başlığı olarak işlev görecektir.
* **"Mesaj" = "Not Kaydı"**: Sohbet penceresine yazılan her bir mesaj, ilgili konunun içine eklenen bir not kaydı olacaktır.

#### 2.2 Çevrimdışı Çalışma Gereksinimi
* **Zorunluluk:** Uygulama, açılışından not kaydetmeye, başlık oluşturmaya ve içeriği aramaya kadar **TAMAMEN İNTERNETSİZ** çalışmalıdır.
* **Teknik Not:** Tüm veriler yerel olarak (Android cihazın depolama alanında) şifreleme yapılmadan saklanacaktır.

#### 2.3 WhatsApp Benzeri Fonksiyonlar (Özelleştirilmiş)

| WhatsApp Fonksiyonu | "WA Notes" Karşılığı | Açıklama |
| :--- | :--- | :--- |
| **Sohbet Listesi** | **Konu Başlıkları Listesi** | Tüm not konularının (thread'lerin) listesi. |
| **Sohbet Penceresi** | **Not Dizini (Thread)** | Belirli bir konuya ait tüm not kayıtlarının listelendiği alan. |
| **Mesaj Gönderme** | **Not Kaydı Ekleme** | Kullanıcının metin, medya vb. içerikleri konuya not olarak eklemesi. |
| **Arama** | **Not Arama** | **Ana Ekranda:** Tüm notların *içeriğinde* arama yapar ve eşleşen notu içeren Konu Başlığını listeler. **Konu İçinde:** Sadece o konudaki notlarda arama yapar. |
| **Mesaj Türleri** | **Not Türleri** | Metin, Fotoğraf/Görüntü, **Video**, Ses Kaydı, **Herhangi Bir Dosya**. |
| **Mesaj Silme/Düzenleme** | **Not Kaydı Silme/Düzenleme** | Tekil notlar üzerinde düzenleme ve silme yapılabilmelidir. |
| **Mesaj Saat/Tarih Damgası**| **Not Oluşturma Tarih/Saat Damgası** | Her notun ne zaman oluşturulduğu gösterilmelidir. |

### 3. Detaylı Özellik Gereksinimleri

#### 3.1 Konu Başlıkları (Threads) Yönetimi
1.  **Başlık Oluşturma:** Kullanıcı ana ekranda yeni bir Konu Başlığı oluşturabilmelidir.
2.  **Başlık Düzenleme/Adlandırma:** Kullanıcı, başlığı kolayca yeniden adlandırabilmelidir.
3.  **Başlık Sayısı:** Kullanıcı **istediği sayıda** Konu Başlığı oluşturabilir.
4.  **Varsayılan Başlık:** Uygulama ilk açıldığında, **"Varsayılan Notlar"** adında bir Konu Başlığı otomatik olarak oluşturulmalıdır.

#### 3.2 Not Kaydı Ekleme (Mesaj Fonksiyonu)
1.  **G.3.2.1 Metin Notu:** Standart klavye ile hızlı metin girişi.
2.  **G.3.2.2 Medya Notu:** Cihaz galerisinden görsel **veya video** ekleme; uygulama içinden kamera ile fotoğraf **veya video** çekme ve kaydetme.
3.  **G.3.2.3 Sesli Not:** Ses kaydı düğmesine basılı tutarak hızlı sesli not kaydı yapma (WhatsApp'taki gibi).
4.  **G.3.2.4 Dosya Ekleme:** Cihazdaki **herhangi bir dosya türünü** (örn: .zip, .mp3, .txt, .pdf) not olarak ekleme.

#### 3.3 Kullanıcı Arayüzü (UI) ve Deneyim (UX)
1.  **Arayüz:** WhatsApp'ın güncel Android arayüzüne (malzeme tasarımı) olabildiğince yakın olmalıdır.
2.  **Okundu/İletildi Göstergeleri:**
    * Tik işaretleri ($\checkmark\checkmark$) ve mavi tik (okundu) göstergeleri **kullanılmamalıdır**.
    * Notun yerel veritabanına başarıyla kaydedildiğini belirtmek için, gönderilen notun sağ alt köşesinde **tek bir basit onay işareti ($\checkmark$)** kullanılmalıdır.
3.  **Kullanıcı Adı/Profil:** Uygulama içinde bir profil adı veya fotoğrafı **gerekli değildir**.

#### 3.4 Veri Yönetimi ve Yedekleme
1.  **Yerel Veri Güvenliği:** Veri şifrelemesi **Gerekli Değildir**. Standart yerel depolama yeterlidir.
2.  **Yedekleme:**
    * Kullanıcının, tüm notları (metin ve medya/dosyalar) içeren bir yedekleme dosyası oluşturmasına olanak tanıyan bir özellik **zorunludur**.
    * Yedekleme, yerel bir dosya (örn: `.zip` veya özel uzantı) olarak oluşturulmalıdır. Bulut hizmetlerine (Google Drive vb.) yükleme tamamen **manuel** olarak kullanıcıya bırakılmalıdır.
3.  **Geri Yükleme Davranışı:**
    * Kullanıcı bir yedek dosyasını geri yüklediğinde, mevcut veriler silinmez.
    * Yedek dosyasındaki veriler, mevcut verilerle **birleştirilir (Merge)**.

### 4. Geliştirme Süreci Notları

#### 4.1 Başlangıç Noktası
- **Tarih:** 27 Ekim 2025
- **Başlangıç Adımı:** Proje yapısı oluşturuldu ve temel gereksinimler belirlendi. 
- **Tamamlanan İşler:**
  - Android Studio projesi oluşturuldu.
  - PRD belgesi yazılmaya başlandı.

#### 4.2 Güncel Durum
- **Tarih:** 27 Ekim 2025
- **Son Durum:** PRD belgesi yazımı devam ediyor. Teknik gereksinimler ve kullanıcı arayüzü detayları üzerinde çalışılıyor.

#### 4.3 Bir Sonraki Adım
- **Planlanan İşler:**
  - ~~Uygulama için temel ekranların tasarlanması~~ (Compose migration yapılacak)
  - ~~SQLite veritabanı entegrasyonu~~ (Room kullanılıyor)
  - Jetpack Compose + MVVM + Hilt mimarisi kurulumu
  - WhatsApp benzeri modern UI implementasyonu

### 5. Teknik Gereksinimler

* **Platform:** Android (Minimum Sürüm: **Android 9.0 Pie (API 28) ve üzeri**)
* **Geliştirme Dili:** **Kotlin**
* **UI Framework:** **Jetpack Compose (Material 3)**
* **Mimari:** **MVVM + Repository Pattern**
* **Dependency Injection:** **Hilt (Dagger)**
* **Yerel Veritabanı:** **Room (SQLite wrapper)**
* **Async Programming:** **Kotlin Coroutines + Flow**
* **Navigation:** **Jetpack Compose Navigation**
* **Depolama:** Metin notları Room database'de; medya ve dosyalar ise internal storage'da (app-specific directory) saklanmalıdır.

#### 5.1 Detaylı Mimari

**Data Layer:**
- Room Database: `ThreadEntity`, `NoteEntity`
- DAOs: Suspend functions ve Flow
- Repository Pattern: Interface + Implementation

**Domain Layer:**
- Business logic
- Use cases (gerekirse)

**UI Layer:**
- Jetpack Compose screens
- ViewModels (state management)
- Navigation Graph

**Dependency Injection:**
- Hilt modules (AppModule, DatabaseModule)
- ViewModel injection
- Repository injection

### 6. Başarı Kriterleri

* Kullanıcıların notlarını (metin, medya, dosya) bir WhatsApp sohbeti kadar **hızlı ve kolay** ekleyebilmesi.
* Uygulamanın internet bağlantısı olmadan **tam ve kesintisiz** çalışabilmesi.
* Uygulama performansının yüksek olması (donma, takılma olmaması).

### Son Yapılanlar (27 Ekim 2025)

- Room veri tabanı ve DAO'lar oluşturuldu: ThreadEntity, NoteEntity, ThreadDao, NoteDao, AppDatabase.
- build.gradle.kts dosyası version catalog ile güncellendi, kapt plugin eklendi, hatalar giderildi.
- TopicListActivity artık Room üzerinden thread listesini çekiyor ve RecyclerView'a bağlıyor.
- TopicListAdapter güncellendi, dinamik veri güncelleme desteği eklendi.
- Eski DatabaseHelper (SQLite) kodu ileride kaldırılacak.

**Sonraki adım:**
- ✅ Mevcut kod hataları düzeltildi (TopicListActivity, DAO'lar, AppDatabase)
- ✅ DatabaseHelper.kt kaldırıldı (Room'a geçildi)
- 🔄 Hilt DI kurulumu yapılıyor
- 🔄 Repository pattern implementasyonu
- 🔄 Compose UI migration (MVVM mimarisi ile)
- 📋 Thread CRUD operations (ekleme, düzenleme, silme)
- 📋 Thread detay ekranı (WhatsApp benzeri chat UI)
- 📋 Medya ve dosya yönetimi
- 📋 Arama fonksiyonu
- 📋 Yedekleme/Geri yükleme sistemi

### 6. Tespit Edilen ve Düzeltilen Sorunlar

#### 6.1 Kod İyileştirmeleri (27 Ekim 2025)
1. **TopicListActivity**: `runOnUiThread` gereksiz kullanımı kaldırıldı (lifecycleScope zaten main thread'de çalışıyor)
2. **NoteDao**: Search sorgusu düzeltildi - `content IS NOT NULL` kontrolü eklendi (null content olan notlarda crash önlendi)
3. **ThreadDao**: Insert conflict strategy `ABORT` yapıldı (REPLACE yerine - veri kaybı riski azaltıldı)
4. **AppDatabase**: `fallbackToDestructiveMigration()` eklendi (development aşaması için)
5. **DatabaseHelper.kt**: Eski SQLite helper tamamen kaldırıldı (artık Room kullanılıyor)

#### 6.2 Planlanan İyileştirmeler
- TopicListAdapter: DiffUtil kullanımı (notifyDataSetChanged yerine)
- ThreadEntity click handling (detay ekranına geçiş için)
- Proper error handling (database operations)
- Loading states ve error UI
- WhatsApp color theme uygulanması
