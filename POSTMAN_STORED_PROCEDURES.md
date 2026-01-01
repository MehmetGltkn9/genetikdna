# Postman - Stored Procedure Test Örnekleri

Bu dosya, tüm stored procedure endpoint'lerini test etmek için Postman örneklerini içerir.

**Base URL:** `http://localhost:8080`

---

## 1. Kullanıcı ve Hesap Ekleme (Stored Procedure)

**Method:** `POST`  
**URL:** `http://localhost:8080/kullanicilar/kullanici-ve-hesap`  
**Headers:**
```
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "ad": "Mehmet",
  "soyad": "Demir",
  "dogumTarihi": "1985-03-20",
  "cinsiyet": "Erkek",
  "eposta": "mehmet.demir@example.com",
  "parolaHash": "hashed_password_123"
}
```

**Beklenen Yanıt:**
```json
{
  "kullanici_id": 1,
  "hesap_id": 1,
  "mesaj": "Kullanıcı ve hesap başarıyla oluşturuldu"
}
```

---

## 2. Kullanıcı Güncelleme (Stored Procedure)

**Method:** `PUT`  
**URL:** `http://localhost:8080/kullanicilar/{id}/guncelle?kullaniciId=1`  
**Headers:**
```
Content-Type: application/json
```

**Path Variables:**
- `id`: Güncellenecek kullanıcı ID'si (örn: 1)

**Query Parameters:**
- `kullaniciId`: İşlemi yapan kullanıcı ID'si (örn: 1)

**Body (raw JSON):**
```json
{
  "ad": "Mehmet",
  "soyad": "Demir",
  "dogumTarihi": "1985-03-20",
  "cinsiyet": "Erkek"
}
```

**Beklenen Yanıt:**
```json
{
  "guncellenen_id": 1,
  "mesaj": "Kullanıcı başarıyla güncellendi"
}
```

---

## 3. Test Siparişi ve Numune Ekleme (Stored Procedure)

**Method:** `POST`  
**URL:** `http://localhost:8080/test-siparisleri/siparis-ve-numune`  
**Headers:**
```
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "kullaniciId": 1,
  "paketId": 1,
  "toplamTutar": 2000.00,
  "barkodId": "NUM-2024-001",
  "numuneTipi": "Tükürük"
}
```

**Beklenen Yanıt:**
```json
{
  "siparis_id": 1,
  "numune_id": 1,
  "mesaj": "Sipariş ve numune başarıyla oluşturuldu"
}
```

---

## 4. Test Siparişi Ödeme (Stored Procedure)

**Method:** `PUT`  
**URL:** `http://localhost:8080/test-siparisleri/{siparisId}/odeme`  
**Headers:**
```
Content-Type: application/json
```

**Path Variables:**
- `siparisId`: Ödeme yapılacak sipariş ID'si (örn: 1)

**Body (raw JSON):**
```json
{
  "odemeDurumu": "Ödendi"
}
```

**Beklenen Yanıt:**
```json
{
  "guncellenen_id": 1,
  "mesaj": "Sipariş ödeme durumu güncellendi: Ödendi"
}
```

---

## 5. Genetik Test Sonucu ve Veri Ekleme (Stored Procedure)

**Method:** `POST`  
**URL:** `http://localhost:8080/genetik-test-sonuclari/test-sonucu-ve-veri`  
**Headers:**
```
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "kullaniciId": 1,
  "analizId": 1,
  "veriSurumu": "v1.0",
  "hamVeriYolu": "/data/genetik/ham_veri_001.vcf",
  "dosyaBoyutuMb": 250
}
```

**Beklenen Yanıt:**
```json
{
  "sonuc_id": 1,
  "veri_id": 1,
  "mesaj": "Test sonucu ve veri başarıyla eklendi"
}
```

---

## 6. Laboratuvar Analizi Tamamlama (Stored Procedure)

**Method:** `POST`  
**URL:** `http://localhost:8080/laboratuvar-analizleri/tamamla`  
**Headers:**
```
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "numuneId": 1,
  "analizBitis": "2024-01-15 14:30:00",
  "kaliteKontrolSonucu": "Başarılı",
  "kullaniciId": 1,
  "veriSurumu": "v1.0"
}
```

**Not:** `analizBitis` formatı: `YYYY-MM-DD HH:MM:SS` (örn: "2024-01-15 14:30:00")

**Beklenen Yanıt:**
```json
{
  "analiz_id": 1,
  "sonuc_id": 1,
  "mesaj": "Analiz tamamlandı ve test sonucu oluşturuldu"
}
```

---

## 7. Hastalık Risk ve Tedavi Ekleme (Stored Procedure)

**Method:** `POST`  
**URL:** `http://localhost:8080/hastalik-risk-skorlari/risk-ve-tedavi`  
**Headers:**
```
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "sonucId": 1,
  "hastalikId": 1,
  "riskYuzdesi": 15.5,
  "riskSeviyesi": "Orta",
  "ilacAdi": "Metformin",
  "yanitTahmini": "İyi",
  "oneriler": "Düzenli egzersiz ve sağlıklı beslenme önerilir."
}
```

**Beklenen Yanıt:**
```json
{
  "risk_id": 1,
  "tedavi_id": 1,
  "mesaj": "Risk skoru ve tedavi yanıtı başarıyla eklendi"
}
```

---

## 8. Varyant Sonuçlarını Toplu Ekleme (Stored Procedure)

**Method:** `POST`  
**URL:** `http://localhost:8080/kullanici-varyant-sonuclari/toplu-ekle`  
**Headers:**
```
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "sonucId": 1,
  "varyantVerileri": "[{\"varyant_id\":1,\"alel\":\"A\"},{\"varyant_id\":2,\"alel\":\"G\"},{\"varyant_id\":3,\"alel\":\"T\"}]"
}
```

**Not:** `varyantVerileri` bir JSON string formatında olmalıdır.

**Beklenen Yanıt:**
```json
{
  "eklenen_sayisi": 3,
  "mesaj": "3 varyant sonucu başarıyla eklendi"
}
```

---

## Test Senaryosu (Sıralı)

Aşağıdaki sırayla test edebilirsiniz:

1. **Kullanıcı ve Hesap Ekleme** → Kullanıcı ID'sini not edin (örn: 1)
2. **Test Siparişi ve Numune Ekleme** → Sipariş ID'sini not edin (örn: 1)
3. **Test Siparişi Ödeme** → Ödeme durumunu güncelleyin
4. **Laboratuvar Analizi Tamamlama** → Analiz ID'sini not edin (örn: 1)
5. **Genetik Test Sonucu ve Veri Ekleme** → Sonuç ID'sini not edin (örn: 1)
6. **Hastalık Risk ve Tedavi Ekleme** → Risk skoru ekleyin
7. **Varyant Sonuçlarını Toplu Ekleme** → Toplu varyant sonuçları ekleyin

---

## Hata Durumları

Eğer hata alırsanız, aşağıdakileri kontrol edin:

1. **Foreign Key Hataları:** İlgili tablolarda kayıt olup olmadığını kontrol edin (örn: `kullanici_id`, `paket_id`, `hastalik_id`)
2. **Veri Tipi Hataları:** JSON'daki veri tiplerinin doğru olduğundan emin olun
3. **Tarih Formatı:** Tarih formatının `YYYY-MM-DD` olduğundan emin olun
4. **Timestamp Formatı:** Timestamp formatının `YYYY-MM-DD HH:MM:SS` olduğundan emin olun

---

## Postman Collection İçin Notlar

Postman'de bir Collection oluştururken:

1. Her endpoint için ayrı bir Request oluşturun
2. Environment Variables kullanarak `base_url` tanımlayın
3. Test Scripts ekleyerek yanıtları doğrulayın
4. Pre-request Scripts ile dinamik değerler oluşturun

