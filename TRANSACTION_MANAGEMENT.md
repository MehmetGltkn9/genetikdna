# Transaction Yönetimi Dokümantasyonu

Bu dokümantasyon, projede uygulanan transaction yönetimi hakkında bilgi vermektedir.

## 📋 Genel Bakış

Projede, birden fazla bağımlı veritabanı işlemini atomik olarak gerçekleştirmek için Spring'in `@Transactional` annotation'ı kullanılmıştır. Bu sayede, işlemlerden herhangi biri başarısız olduğunda tüm işlemler otomatik olarak rollback edilir.

## 🔧 Yapılandırma

### TransactionConfig.java
```java
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
```

Bu yapılandırma ile:
- Transaction yönetimi aktif edilir
- `DataSourceTransactionManager` bean olarak tanımlanır
- Tüm `@Transactional` annotation'ları otomatik olarak işlenir

## 🎯 Transaction'lı İşlemler

### 1. Kullanıcı ve Hesap Ekleme

**Service Metodu:** `KullaniciService.kullaniciVeHesapEkleWithTransaction()`

**Endpoint:** `POST /kullanicilar/kullanici-ve-hesap-transaction`

**İşlemler:**
1. Kullanıcı ekleme
2. Kullanıcı hesabı ekleme

**Transaction Davranışı:**
- Her iki işlem de başarılı olursa commit edilir
- Herhangi bir hata durumunda rollback edilir

**Örnek Request:**
```json
{
  "ad": "Ahmet",
  "soyad": "Yılmaz",
  "dogumTarihi": "1990-01-15",
  "cinsiyet": "Erkek",
  "eposta": "ahmet@example.com",
  "parolaHash": "hashed_password",
  "aktifMi": true
}
```

**Örnek Response:**
```json
{
  "kullanici_id": 123,
  "eposta": "ahmet@example.com",
  "mesaj": "Kullanıcı ve hesap başarıyla oluşturuldu (Transaction ile)"
}
```

### 2. Sipariş ve Numune Ekleme

**Service Metodu:** `TestSiparisiService.siparisVeNumuneEkleWithTransaction()`

**Endpoint:** `POST /test-siparisleri/siparis-ve-numune-transaction`

**İşlemler:**
1. Test siparişi ekleme
2. Numune ekleme

**Transaction Davranışı:**
- Her iki işlem de başarılı olursa commit edilir
- Herhangi bir hata durumunda rollback edilir

**Örnek Request:**
```json
{
  "kullaniciId": 1,
  "paketId": 1,
  "toplamTutar": 1500.00,
  "odemeDurumu": "Beklemede",
  "barkodId": "NUM-2024-001",
  "numuneTipi": "Tükürük",
  "durum": "Beklemede"
}
```

**Örnek Response:**
```json
{
  "siparis_id": 456,
  "barkod_id": "NUM-2024-001",
  "mesaj": "Sipariş ve numune başarıyla oluşturuldu (Transaction ile)"
}
```

### 3. Test Sonucu ve Veri Ekleme

**Service Metodu:** `GenetikTestSonucuService.testSonucuVeVeriEkleWithTransaction()`

**Endpoint:** `POST /genetik-test-sonuclari/test-sonucu-ve-veri-transaction`

**İşlemler:**
1. Genetik test sonucu ekleme
2. Kullanıcı genetik verisi ekleme

**Transaction Davranışı:**
- Her iki işlem de başarılı olursa commit edilir
- Herhangi bir hata durumunda rollback edilir

**Örnek Request:**
```json
{
  "kullaniciId": 1,
  "analizId": 1,
  "veriSurumu": "v1.0",
  "hamVeriYolu": "/data/genetic/user1/raw_data.vcf",
  "dosyaBoyutuMb": 250
}
```

**Örnek Response:**
```json
{
  "sonuc_id": 789,
  "ham_veri_yolu": "/data/genetic/user1/raw_data.vcf",
  "mesaj": "Test sonucu ve veri başarıyla oluşturuldu (Transaction ile)"
}
```

## 🔄 Rollback Davranışı

### Otomatik Rollback
`@Transactional(rollbackFor = Exception.class)` annotation'ı sayesinde:
- Herhangi bir `Exception` fırlatıldığında transaction otomatik olarak rollback edilir
- Veritabanında hiçbir değişiklik kalıcı hale gelmez

### Rollback Senaryoları
1. **Foreign Key Hatası:** İlişkili kayıt bulunamazsa
2. **Unique Constraint Hatası:** Benzersiz alan tekrarı durumunda
3. **Null Constraint Hatası:** Zorunlu alan eksikse
4. **Veri Tipi Hatası:** Yanlış veri tipi gönderilirse
5. **Genel Hatalar:** Herhangi bir runtime exception

## 📝 Kod Örneği

```java
@Transactional(rollbackFor = Exception.class)
public Map<String, Object> kullaniciVeHesapEkleWithTransaction(
        Kullanici kullanici, String eposta, String parolaHash, boolean aktifMi) {
    try {
        // 1. Kullanıcı ekle ve ID'yi al
        Integer kullaniciId = kullaniciDAO.addKullaniciAndGetId(kullanici);
        
        // 2. Hesap ekle
        KullaniciHesap hesap = new KullaniciHesap();
        hesap.setKullaniciId(kullaniciId);
        hesap.setEposta(eposta);
        hesap.setParolaHash(parolaHash);
        hesap.setAktifMi(aktifMi);
        kullaniciHesapDAO.addKullaniciHesap(hesap);
        
        // 3. Sonuç döndür
        Map<String, Object> result = new HashMap<>();
        result.put("kullanici_id", kullaniciId);
        result.put("eposta", eposta);
        result.put("mesaj", "Kullanıcı ve hesap başarıyla oluşturuldu (Transaction ile)");
        
        return result;
    } catch (Exception e) {
        // Hata durumunda transaction otomatik olarak rollback edilir
        throw new RuntimeException("Kullanıcı ve hesap ekleme işlemi başarısız oldu: " + e.getMessage(), e);
    }
}
```

## 🧪 Test Senaryoları

### Başarılı Senaryo
1. Tüm veriler geçerli
2. Foreign key ilişkileri doğru
3. Constraint'ler sağlanıyor
4. **Sonuç:** Tüm işlemler commit edilir

### Hata Senaryosu
1. Kullanıcı eklenir ✓
2. Hesap ekleme sırasında hata oluşur ✗
3. **Sonuç:** Kullanıcı ekleme işlemi de rollback edilir

## 🔍 Hata Yönetimi

### Exception Handling
- Service katmanında `RuntimeException` fırlatılır
- Controller katmanında Spring otomatik olarak HTTP 500 hatası döner
- Frontend'de kullanıcıya uygun hata mesajı gösterilir

### Loglama
Transaction işlemleri Spring tarafından otomatik olarak loglanır:
- Transaction başlangıcı
- Commit işlemi
- Rollback işlemi

## 📊 Performans Notları

- Transaction'lar veritabanı bağlantılarını tutar
- Uzun süren transaction'lar diğer işlemleri bloke edebilir
- Transaction scope'u mümkün olduğunca küçük tutulmalıdır

## ✅ Best Practices

1. **Transaction Scope:** Sadece gerekli işlemler transaction içinde olmalı
2. **Exception Handling:** Spesifik exception'lar yakalanmalı
3. **Rollback Policy:** `rollbackFor = Exception.class` kullanılmalı
4. **Read Operations:** Sadece okuma işlemleri için `@Transactional(readOnly = true)` kullanılmalı
5. **Isolation Level:** Gerekirse isolation level belirtilmeli

## 🔗 İlgili Dosyalar

- `src/main/java/org/example/genetikdna/Config/TransactionConfig.java`
- `src/main/java/org/example/genetikdna/Service/KullaniciService.java`
- `src/main/java/org/example/genetikdna/Service/TestSiparisiService.java`
- `src/main/java/org/example/genetikdna/Service/GenetikTestSonucuService.java`

---

**Not:** Bu transaction yönetimi, veri tutarlılığını garanti eder ve veritabanı bütünlüğünü korur.

