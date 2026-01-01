# Raporlama Fonksiyonları Dokümantasyonu

Bu dokümantasyon, PostgreSQL FUNCTION kullanılarak oluşturulmuş detaylı raporlama fonksiyonlarını açıklar.

## 📊 Raporlama Fonksiyonları

### 1. Kullanıcı Detaylı Raporu

**Fonksiyon:** `fn_kullanici_detayli_raporu(p_kullanici_id INTEGER)`

**Açıklama:** Belirli bir kullanıcının tüm istatistiklerini detaylı olarak döndürür.

**Dönen Alanlar:**
- `kullanici_id` - Kullanıcı ID
- `kullanici_adi` - Kullanıcı adı
- `kullanici_soyadi` - Kullanıcı soyadı
- `kayit_tarihi` - Kayıt tarihi
- `toplam_siparis_sayisi` - Toplam sipariş sayısı
- `toplam_harcama` - Toplam harcama tutarı
- `odeme_bekleyen_siparis_sayisi` - Ödeme bekleyen sipariş sayısı
- `test_sonucu_sayisi` - Test sonucu sayısı
- `risk_skoru_sayisi` - Risk skoru sayısı
- `ortalama_risk_yuzdesi` - Ortalama risk yüzdesi
- `en_yuksek_risk_hastalik` - En yüksek riskli hastalık
- `numune_sayisi` - Numune sayısı
- `aktif_hesap_sayisi` - Aktif hesap sayısı
- `adres_sayisi` - Adres sayısı

**Kullanım:**
```sql
SELECT * FROM fn_kullanici_detayli_raporu(1);
```

**API Endpoint:**
```
GET /raporlama/kullanici/{kullaniciId}
```

**Örnek Response:**
```json
{
  "kullanici_id": 1,
  "kullanici_adi": "Ahmet",
  "kullanici_soyadi": "Yılmaz",
  "kayit_tarihi": "2024-01-15T10:30:00",
  "toplam_siparis_sayisi": 5,
  "toplam_harcama": 7500.00,
  "odeme_bekleyen_siparis_sayisi": 2,
  "test_sonucu_sayisi": 3,
  "risk_skoru_sayisi": 8,
  "ortalama_risk_yuzdesi": 45.5,
  "en_yuksek_risk_hastalik": "Diyabet",
  "numune_sayisi": 5,
  "aktif_hesap_sayisi": 1,
  "adres_sayisi": 2
}
```

---

### 2. Test Sonuçları Analiz Raporu

**Fonksiyon:** `fn_test_sonuclari_analiz_raporu(p_baslangic_tarihi DATE, p_bitis_tarihi DATE)`

**Açıklama:** Belirli bir tarih aralığındaki (veya tüm) test sonuçlarının detaylı analizini yapar.

**Parametreler:**
- `p_baslangic_tarihi` - Başlangıç tarihi (opsiyonel, NULL ise tüm kayıtlar)
- `p_bitis_tarihi` - Bitiş tarihi (opsiyonel, NULL ise tüm kayıtlar)

**Dönen Alanlar:**
- `toplam_test_sayisi` - Toplam test sayısı
- `ortalama_test_suresi_gun` - Ortalama test süresi (gün)
- `en_cok_test_yapan_kullanici` - En çok test yapan kullanıcı
- `test_sonucu_dagilimi` - Test sonucu dağılımı (JSONB)
- `risk_seviyesi_dagilimi` - Risk seviyesi dağılımı (JSONB)
- `en_sik_gorulen_hastalik` - En sık görülen hastalık
- `toplam_varyant_sayisi` - Toplam varyant sayısı
- `aktif_numune_sayisi` - Aktif numune sayısı
- `tamamlanan_analiz_sayisi` - Tamamlanan analiz sayısı

**Kullanım:**
```sql
-- Tüm test sonuçları
SELECT * FROM fn_test_sonuclari_analiz_raporu(NULL, NULL);

-- Belirli tarih aralığı
SELECT * FROM fn_test_sonuclari_analiz_raporu('2024-01-01', '2024-12-31');
```

**API Endpoint:**
```
GET /raporlama/test-sonuclari
GET /raporlama/test-sonuclari?baslangicTarihi=2024-01-01&bitisTarihi=2024-12-31
```

**Örnek Response:**
```json
{
  "toplam_test_sayisi": 150,
  "ortalama_test_suresi_gun": 7,
  "en_cok_test_yapan_kullanici": "Ahmet Yılmaz",
  "test_sonucu_dagilimi": {
    "v1.0": 80,
    "v2.0": 70
  },
  "risk_seviyesi_dagilimi": {
    "Yüksek": 30,
    "Orta": 60,
    "Düşük": 60
  },
  "en_sik_gorulen_hastalik": "Diyabet",
  "toplam_varyant_sayisi": 5000,
  "aktif_numune_sayisi": 25,
  "tamamlanan_analiz_sayisi": 125
}
```

---

### 3. Hastalık Risk Analiz Raporu

**Fonksiyon:** `fn_hastalik_risk_analiz_raporu(p_hastalik_id INTEGER)`

**Açıklama:** Belirli bir hastalık (veya tüm hastalıklar) için risk analiz raporu oluşturur.

**Parametreler:**
- `p_hastalik_id` - Hastalık ID (opsiyonel, NULL ise tüm hastalıklar)

**Dönen Alanlar:**
- `hastalik_id` - Hastalık ID
- `hastalik_adi` - Hastalık adı
- `icd_kodu` - ICD kodu
- `toplam_test_sayisi` - Toplam test sayısı
- `ortalama_risk_yuzdesi` - Ortalama risk yüzdesi
- `en_yuksek_risk_yuzdesi` - En yüksek risk yüzdesi
- `en_dusuk_risk_yuzdesi` - En düşük risk yüzdesi
- `yuksek_riskli_kullanici_sayisi` - Yüksek riskli kullanıcı sayısı
- `orta_riskli_kullanici_sayisi` - Orta riskli kullanıcı sayısı
- `dusuk_riskli_kullanici_sayisi` - Düşük riskli kullanıcı sayısı
- `riskli_kullanicilar` - Riskli kullanıcılar listesi (JSONB)

**Kullanım:**
```sql
-- Tüm hastalıklar
SELECT * FROM fn_hastalik_risk_analiz_raporu(NULL);

-- Belirli bir hastalık
SELECT * FROM fn_hastalik_risk_analiz_raporu(1);
```

**API Endpoint:**
```
GET /raporlama/hastalik-risk
GET /raporlama/hastalik-risk?hastalikId=1
```

**Örnek Response:**
```json
[
  {
    "hastalik_id": 1,
    "hastalik_adi": "Diyabet",
    "icd_kodu": "E11",
    "toplam_test_sayisi": 50,
    "ortalama_risk_yuzdesi": 45.5,
    "en_yuksek_risk_yuzdesi": 85.0,
    "en_dusuk_risk_yuzdesi": 15.0,
    "yuksek_riskli_kullanici_sayisi": 10,
    "orta_riskli_kullanici_sayisi": 20,
    "dusuk_riskli_kullanici_sayisi": 20,
    "riskli_kullanicilar": [
      {
        "kullanici_id": 1,
        "kullanici_adi": "Ahmet Yılmaz",
        "risk_yuzdesi": 85.0,
        "risk_seviyesi": "Yüksek",
        "test_tarihi": "2024-01-15T10:30:00"
      }
    ]
  }
]
```

---

## 🔧 Teknik Detaylar

### Fonksiyon Özellikleri

1. **JOIN İşlemleri:** Birden fazla tablo birleştirilerek detaylı veri çekilir
2. **Aggregasyon:** COUNT, SUM, AVG, MAX, MIN gibi fonksiyonlar kullanılır
3. **JSONB Desteği:** Dağılım verileri JSONB formatında döndürülür
4. **Subquery:** İç içe sorgular ile detaylı analiz yapılır
5. **Filtreleme:** Tarih ve ID bazlı filtreleme desteği

### Performans

- Index'ler kullanılarak optimize edilmiştir
- Foreign key ilişkileri üzerinden JOIN yapılır
- GROUP BY ile aggregasyon yapılır

---

## 📍 Konum

**Fonksiyon Tanımları:**
- `src/main/java/org/example/genetikdna/DatabaseInitializer.java` (satır 560-750)

**DAO:**
- `src/main/java/org/example/genetikdna/Dao/RaporlamaDAO.java`

**Service:**
- `src/main/java/org/example/genetikdna/Service/RaporlamaService.java`

**Controller:**
- `src/main/java/org/example/genetikdna/Controller/RaporlamaController.java`

**Frontend:**
- `src/main/resources/static/raporlama.html`

---

## 🎯 Kullanım Senaryoları

1. **Kullanıcı Profili:** Kullanıcının tüm aktivitelerini görüntüleme
2. **İstatistiksel Analiz:** Test sonuçlarının genel analizi
3. **Risk Değerlendirmesi:** Hastalık bazlı risk analizi
4. **Raporlama:** Yönetim için detaylı raporlar

---

## ✅ Değerlendirme Kriteri

**Kriter 6:** Fonksiyon kullanılarak oluşturulmuş detaylı bir raporlamanın olması (15 Puan)

**Durum:** ✅ **TAMAMLANDI**

- ✅ 3 adet detaylı raporlama fonksiyonu
- ✅ JOIN, aggregasyon, subquery kullanımı
- ✅ JSONB formatında veri döndürme
- ✅ Tarih ve ID bazlı filtreleme
- ✅ DAO, Service, Controller entegrasyonu
- ✅ Frontend arayüzü

---

**Not:** Bu fonksiyonlar PostgreSQL FUNCTION olarak tanımlanmıştır ve uygulama başlatıldığında otomatik olarak oluşturulur.

