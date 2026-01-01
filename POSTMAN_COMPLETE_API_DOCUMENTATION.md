# Genetik DNA API - Kapsamlı Postman Dokümantasyonu

Bu dokümantasyon, tüm 20 controller için API endpoint'lerini içerir.

**Base URL:** `http://localhost:8080`

---

## 📋 İçindekiler

1. [Kullanıcılar](#1-kullanıcılar)
2. [Kullanıcı Hesapları](#2-kullanıcı-hesapları)
3. [Adresler](#3-adresler)
4. [Test Paketleri](#4-test-paketleri)
5. [Test Siparişleri](#5-test-siparişleri)
6. [Numuneler](#6-numuneler)
7. [Laboratuvar Analizleri](#7-laboratuvar-analizleri)
8. [Genetik Test Sonuçları](#8-genetik-test-sonuçları)
9. [Kullanıcı Genetik Verileri](#9-kullanıcı-genetik-verileri)
10. [Genetik Varyantlar](#10-genetik-varyantlar)
11. [Kullanıcı Varyant Sonuçları](#11-kullanıcı-varyant-sonuçları)
12. [Hastalık Tanımları](#12-hastalık-tanımları)
13. [Hastalık Risk Skorları](#13-hastalık-risk-skorları)
14. [Tedaviye Yanıtlar](#14-tedaviye-yanıtlar)
15. [Etnik Köken Raporları](#15-etnik-köken-raporları)
16. [Genetik Danışmanlık](#16-genetik-danışmanlık)
17. [Aile Üyeleri](#17-aile-üyeleri)
18. [Soy Ağacı Bağlantıları](#18-soy-ağacı-bağlantıları)
19. [Veri Erişim İzinleri](#19-veri-erişim-izinleri)
20. [Denetim Kayıtları](#20-denetim-kayıtları)

---

## 1. Kullanıcılar

### POST /kullanicilar
**Kullanıcı Ekle**

```json
{
  "ad": "Mehmet",
  "soyad": "Demir",
  "dogumTarihi": "1985-03-20",
  "cinsiyet": "Erkek"
}
```

### GET /kullanicilar
**Tüm Kullanıcıları Getir**

### GET /kullanicilar/{kullaniciId}/detaylar
**Kullanıcı Detayları**

### GET /kullanicilar/{id}
**Kullanıcı Getir (ID)**

### DELETE /kullanicilar/{id}
**Kullanıcı Sil**

### POST /kullanicilar/kullanici-ve-hesap (Stored Procedure)
**Kullanıcı ve Hesap Ekle**

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

### PUT /kullanicilar/{id}/guncelle?kullaniciId={kullaniciId} (Stored Procedure)
**Kullanıcı Güncelle**

```json
{
  "ad": "Mehmet",
  "soyad": "Demir",
  "dogumTarihi": "1985-03-20",
  "cinsiyet": "Erkek"
}
```

---

## 2. Kullanıcı Hesapları

### POST /kullanici-hesaplari
**Hesap Ekle**

```json
{
  "kullaniciId": 1,
  "eposta": "mehmet@example.com",
  "parolaHash": "hashed_password",
  "aktifMi": true
}
```

### GET /kullanici-hesaplari
**Tüm Hesapları Getir**

### GET /kullanici-hesaplari/kullanicilar/{kullaniciId}
**Kullanıcı Hesapları**

### GET /kullanici-hesaplari/eposta/{eposta}
**Hesap Getir (E-posta)**

### GET /kullanici-hesaplari/{id}
**Hesap Getir (ID)**

### DELETE /kullanici-hesaplari/{id}
**Hesap Sil**

---

## 3. Adresler

### POST /adresler
**Adres Ekle**

```json
{
  "kullaniciId": 1,
  "adresTipi": "Fatura",
  "ulke": "Türkiye",
  "sehir": "İstanbul",
  "postaKodu": "34000",
  "detayliAdres": "Atatürk Cad. No:123 Daire:5"
}
```

### GET /adresler
**Tüm Adresleri Getir**

### GET /adresler/kullanicilar/{kullaniciId}
**Kullanıcı Adresleri**

### GET /adresler/{id}
**Adres Getir (ID)**

### DELETE /adresler/{id}
**Adres Sil**

---

## 4. Test Paketleri

### POST /test-paketleri
**Test Paketi Ekle**

```json
{
  "paketAdi": "Temel Genetik Test Paketi",
  "fiyat": 1500.00,
  "icerikAciklamasi": "Temel genetik analiz ve raporlama"
}
```

### GET /test-paketleri
**Tüm Paketleri Getir**

### GET /test-paketleri/{paketId}/siparisler
**Paket Siparişleri**

### GET /test-paketleri/{id}
**Paket Getir (ID)**

### DELETE /test-paketleri/{id}
**Paket Sil**

---

## 5. Test Siparişleri

### POST /test-siparisleri
**Test Siparişi Ekle**

```json
{
  "kullaniciId": 1,
  "paketId": 1,
  "toplamTutar": 2000.00,
  "odemeDurumu": "Beklemede"
}
```

### GET /test-siparisleri
**Tüm Siparişleri Getir**

### GET /test-siparisleri/kullanicilar/{kullaniciId}
**Kullanıcı Siparişleri**

### GET /test-siparisleri/{siparisId}/detaylar
**Sipariş Detayları**

### GET /test-siparisleri/{id}
**Sipariş Getir (ID)**

### DELETE /test-siparisleri/{id}
**Sipariş Sil**

### POST /test-siparisleri/siparis-ve-numune (Stored Procedure)
**Sipariş ve Numune Ekle**

```json
{
  "kullaniciId": 1,
  "paketId": 1,
  "toplamTutar": 2000.00,
  "barkodId": "NUM-2024-001",
  "numuneTipi": "Tükürük"
}
```

### PUT /test-siparisleri/{siparisId}/odeme (Stored Procedure)
**Sipariş Ödeme**

```json
{
  "odemeDurumu": "Ödendi"
}
```

---

## 6. Numuneler

### POST /numuneler
**Numune Ekle**

```json
{
  "siparisId": 1,
  "barkodId": "NUM-2024-001",
  "numuneTipi": "Tükürük",
  "durum": "Beklemede"
}
```

### GET /numuneler
**Tüm Numuneleri Getir**

### GET /numuneler/siparisler/{siparisId}
**Sipariş Numuneleri**

### GET /numuneler/{numuneId}/analizler
**Numune Analizleri**

### GET /numuneler/barkod/{barkodId}
**Numune Getir (Barkod)**

### GET /numuneler/{id}
**Numune Getir (ID)**

### DELETE /numuneler/{id}
**Numune Sil**

---

## 7. Laboratuvar Analizleri

### POST /laboratuvar-analizleri
**Analiz Ekle**

```json
{
  "numuneId": 1,
  "teknisyenAdi": "Dr. Ayşe Yılmaz",
  "kaliteKontrolSonucu": "Başarılı"
}
```

### GET /laboratuvar-analizleri
**Tüm Analizleri Getir**

### GET /laboratuvar-analizleri/numuneler/{numuneId}
**Numune Analizleri**

### GET /laboratuvar-analizleri/teknisyenler/{teknisyenAdi}
**Teknisyen Analizleri**

### GET /laboratuvar-analizleri/{id}
**Analiz Getir (ID)**

### DELETE /laboratuvar-analizleri/{id}
**Analiz Sil**

### POST /laboratuvar-analizleri/tamamla (Stored Procedure)
**Analiz Tamamla**

```json
{
  "numuneId": 1,
  "analizBitis": "2024-01-15 14:30:00",
  "kaliteKontrolSonucu": "Başarılı",
  "kullaniciId": 1,
  "veriSurumu": "v1.0"
}
```

---

## 8. Genetik Test Sonuçları

### POST /genetik-test-sonuclari
**Test Sonucu Ekle**

```json
{
  "kullaniciId": 1,
  "analizId": 1,
  "veriSurumu": "v1.0"
}
```

### GET /genetik-test-sonuclari
**Tüm Sonuçları Getir**

### GET /genetik-test-sonuclari/kullanicilar/{kullaniciId}
**Kullanıcı Sonuçları**

### GET /genetik-test-sonuclari/analizler/{analizId}
**Analiz Sonuçları**

### GET /genetik-test-sonuclari/{id}
**Sonuç Getir (ID)**

### DELETE /genetik-test-sonuclari/{id}
**Sonuç Sil**

### POST /genetik-test-sonuclari/test-sonucu-ve-veri (Stored Procedure)
**Test Sonucu ve Veri Ekle**

```json
{
  "kullaniciId": 1,
  "analizId": 1,
  "veriSurumu": "v1.0",
  "hamVeriYolu": "/data/genetik/ham_veri_001.vcf",
  "dosyaBoyutuMb": 250
}
```

---

## 9. Kullanıcı Genetik Verileri

### POST /kullanici-genetik-verileri
**Genetik Veri Ekle**

```json
{
  "sonucId": 1,
  "hamVeriDepolamaYolu": "/data/genetik/ham_veri_001.vcf",
  "dosyaBoyutuMb": 250
}
```

### GET /kullanici-genetik-verileri
**Tüm Verileri Getir**

### GET /kullanici-genetik-verileri/sonuclar/{sonucId}
**Sonuç Verileri**

### GET /kullanici-genetik-verileri/{id}
**Veri Getir (ID)**

### DELETE /kullanici-genetik-verileri/{id}
**Veri Sil**

---

## 10. Genetik Varyantlar

### POST /genetik-varyantlar
**Varyant Ekle**

```json
{
  "rsId": "rs123456",
  "kromozom": "1",
  "pozisyon": 123456,
  "referansAlel": "A",
  "alternatifAlel": "G",
  "genAdi": "BRCA1"
}
```

### GET /genetik-varyantlar
**Tüm Varyantları Getir**

### GET /genetik-varyantlar/kromozomlar/{kromozom}
**Kromozom Varyantları**

### GET /genetik-varyantlar/rs-id/{rsId}
**Varyant Getir (RS ID)**

### GET /genetik-varyantlar/{id}
**Varyant Getir (ID)**

### DELETE /genetik-varyantlar/{id}
**Varyant Sil**

---

## 11. Kullanıcı Varyant Sonuçları

### POST /kullanici-varyant-sonuclari
**Varyant Sonucu Ekle**

```json
{
  "sonucId": 1,
  "varyantId": 1,
  "tespitEdilenAlel": "A"
}
```

### GET /kullanici-varyant-sonuclari
**Tüm Sonuçları Getir**

### GET /kullanici-varyant-sonuclari/sonuclar/{sonucId}
**Sonuç Varyantları**

### GET /kullanici-varyant-sonuclari/varyantlar/{varyantId}
**Varyant Sonuçları**

### GET /kullanici-varyant-sonuclari/{id}
**Sonuç Getir (ID)**

### DELETE /kullanici-varyant-sonuclari/{id}
**Sonuç Sil**

### POST /kullanici-varyant-sonuclari/toplu-ekle (Stored Procedure)
**Toplu Varyant Ekle**

```json
{
  "sonucId": 1,
  "varyantVerileri": "[{\"varyant_id\":1,\"alel\":\"A\"},{\"varyant_id\":2,\"alel\":\"G\"},{\"varyant_id\":3,\"alel\":\"T\"}]"
}
```

---

## 12. Hastalık Tanımları

### POST /hastalik-tanimlari
**Hastalık Tanımı Ekle**

```json
{
  "hastalikAdi": "Tip 2 Diyabet",
  "icdKodu": "E11",
  "aciklama": "Tip 2 diyabet hastalığı tanımı"
}
```

### GET /hastalik-tanimlari
**Tüm Tanımları Getir**

### GET /hastalik-tanimlari/icd-kodu/{icdKodu}
**Hastalık Getir (ICD Kodu)**

### GET /hastalik-tanimlari/{id}
**Hastalık Getir (ID)**

### DELETE /hastalik-tanimlari/{id}
**Hastalık Sil**

---

## 13. Hastalık Risk Skorları

### POST /hastalik-risk-skorlari
**Risk Skoru Ekle**

```json
{
  "sonucId": 1,
  "hastalikId": 1,
  "riskYuzdesi": 15.5,
  "riskSeviyesi": "Orta"
}
```

### GET /hastalik-risk-skorlari
**Tüm Skorları Getir**

### GET /hastalik-risk-skorlari/sonuclar/{sonucId}
**Sonuç Risk Skorları**

### GET /hastalik-risk-skorlari/hastaliklar/{hastalikId}
**Hastalık Risk Skorları**

### GET /hastalik-risk-skorlari/{id}
**Risk Skoru Getir (ID)**

### DELETE /hastalik-risk-skorlari/{id}
**Risk Skoru Sil**

### POST /hastalik-risk-skorlari/risk-ve-tedavi (Stored Procedure)
**Risk ve Tedavi Ekle**

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

---

## 14. Tedaviye Yanıtlar

### POST /tedaviye-yanitlar
**Tedaviye Yanıt Ekle**

```json
{
  "sonucId": 1,
  "ilacAdi": "Metformin",
  "yanitTahmini": "İyi",
  "oneriler": "Düzenli egzersiz önerilir."
}
```

### GET /tedaviye-yanitlar
**Tüm Yanıtları Getir**

### GET /tedaviye-yanitlar/sonuclar/{sonucId}
**Sonuç Yanıtları**

### GET /tedaviye-yanitlar/{id}
**Yanıt Getir (ID)**

### DELETE /tedaviye-yanitlar/{id}
**Yanıt Sil**

---

## 15. Etnik Köken Raporları

### POST /etnik-koken-raporlari
**Rapor Ekle**

```json
{
  "sonucId": 1,
  "etnikKoken": "Türk",
  "yuzde": 85.5
}
```

### GET /etnik-koken-raporlari
**Tüm Raporları Getir**

### GET /etnik-koken-raporlari/sonuclar/{sonucId}
**Sonuç Raporları**

### GET /etnik-koken-raporlari/{id}
**Rapor Getir (ID)**

### DELETE /etnik-koken-raporlari/{id}
**Rapor Sil**

---

## 16. Genetik Danışmanlık

### POST /genetik-danismanlik
**Danışmanlık Ekle**

```json
{
  "kullaniciId": 1,
  "danismanlikTarihi": "2024-01-15",
  "danismanlikTipi": "Genetik Test Sonuçları",
  "notlar": "Test sonuçları hakkında danışmanlık verildi."
}
```

### GET /genetik-danismanlik
**Tüm Danışmanlıkları Getir**

### GET /genetik-danismanlik/kullanicilar/{kullaniciId}
**Kullanıcı Danışmanlıkları**

### GET /genetik-danismanlik/{id}
**Danışmanlık Getir (ID)**

### DELETE /genetik-danismanlik/{id}
**Danışmanlık Sil**

---

## 17. Aile Üyeleri

### POST /aile-uyeleri
**Aile Üyesi Ekle**

```json
{
  "kullaniciId": 1,
  "akrabalikDerecesi": "Baba",
  "ad": "Ali",
  "soyad": "Demir",
  "dogumTarihi": "1960-05-15",
  "cinsiyet": "Erkek"
}
```

### GET /aile-uyeleri
**Tüm Üyeleri Getir**

### GET /aile-uyeleri/kullanicilar/{kullaniciId}
**Kullanıcı Aile Üyeleri**

### GET /aile-uyeleri/{id}
**Üye Getir (ID)**

### DELETE /aile-uyeleri/{id}
**Üye Sil**

---

## 18. Soy Ağacı Bağlantıları

### POST /soyagaci-baglantilari
**Bağlantı Ekle**

```json
{
  "kullaniciId": 1,
  "bagliKullaniciId": 2,
  "akrabalikDerecesi": "Kardeş"
}
```

### GET /soyagaci-baglantilari
**Tüm Bağlantıları Getir**

### GET /soyagaci-baglantilari/kullanicilar/{kullaniciId}
**Kullanıcı Bağlantıları**

### GET /soyagaci-baglantilari/{id}
**Bağlantı Getir (ID)**

### DELETE /soyagaci-baglantilari/{id}
**Bağlantı Sil**

---

## 19. Veri Erişim İzinleri

### POST /veri-erisim-izinleri
**İzin Ekle**

```json
{
  "kullaniciId": 1,
  "izinVerilenKullaniciId": 2,
  "izinTipi": "Okuma",
  "gecerlilikTarihi": "2025-12-31"
}
```

### GET /veri-erisim-izinleri
**Tüm İzinleri Getir**

### GET /veri-erisim-izinleri/kullanicilar/{kullaniciId}
**Kullanıcı İzinleri**

### GET /veri-erisim-izinleri/{id}
**İzin Getir (ID)**

### DELETE /veri-erisim-izinleri/{id}
**İzin Sil**

---

## 20. Denetim Kayıtları

### POST /denetim-kayitlari
**Denetim Kaydı Ekle**

```json
{
  "kullaniciId": 1,
  "etkilenenTablo": "kullanici",
  "etkilenenId": 1,
  "islemTipi": "INSERT",
  "aciklama": "Yeni kullanıcı eklendi"
}
```

### GET /denetim-kayitlari
**Tüm Kayıtları Getir**

### GET /denetim-kayitlari/kullanicilar/{kullaniciId}
**Kullanıcı Kayıtları**

### GET /denetim-kayitlari/tablolar/{tabloAdi}
**Tablo Kayıtları**

### GET /denetim-kayitlari/{id}
**Kayıt Getir (ID)**

### DELETE /denetim-kayitlari/{id}
**Kayıt Sil**

---

## 📝 Notlar

### Tarih Formatları
- **Date:** `YYYY-MM-DD` (örn: "2024-01-15")
- **Timestamp:** `YYYY-MM-DD HH:MM:SS` (örn: "2024-01-15 14:30:00")

### Stored Procedure Endpoint'leri
- `(SP)` işareti olan endpoint'ler stored procedure kullanır
- Bu endpoint'ler karmaşık işlemleri tek seferde gerçekleştirir

### Foreign Key İlişkileri
- Endpoint'leri kullanırken foreign key ilişkilerine dikkat edin
- Önce ana kayıtları (kullanıcı, paket vb.) oluşturun
- Sonra bağımlı kayıtları (sipariş, numune vb.) oluşturun

---

## 🚀 Hızlı Başlangıç Senaryosu

1. **Kullanıcı ve Hesap Oluştur** → `POST /kullanicilar/kullanici-ve-hesap`
2. **Test Paketi Oluştur** → `POST /test-paketleri`
3. **Sipariş ve Numune Oluştur** → `POST /test-siparisleri/siparis-ve-numune`
4. **Sipariş Ödemesi Yap** → `PUT /test-siparisleri/{siparisId}/odeme`
5. **Analiz Tamamla** → `POST /laboratuvar-analizleri/tamamla`
6. **Test Sonucu ve Veri Ekle** → `POST /genetik-test-sonuclari/test-sonucu-ve-veri`
7. **Risk ve Tedavi Ekle** → `POST /hastalik-risk-skorlari/risk-ve-tedavi`
8. **Varyant Sonuçları Ekle** → `POST /kullanici-varyant-sonuclari/toplu-ekle`

---

## 📦 Postman Collection

Tüm endpoint'leri içeren Postman Collection dosyası:
- `GenetikDNA_Complete_API.postman_collection.json`

Bu dosyayı Postman'e import ederek tüm endpoint'leri kullanabilirsiniz.

