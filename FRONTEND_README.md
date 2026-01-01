# Genetik DNA - Frontend Arayüzü

Modern ve kullanıcı dostu bir web arayüzü ile API endpoint'lerini test edebilirsiniz.

## 🚀 Kullanım

1. **Uygulamayı Başlatın**
   ```bash
   ./gradlew.bat bootRun
   ```

2. **Tarayıcıda Açın**
   ```
   http://localhost:8080
   ```

## 📋 Özellikler

### Ana Sayfa (`index.html`)
- Tüm controller'ları gösteren dashboard
- Her controller için hızlı erişim kartları
- Modern ve responsive tasarım

### Kullanıcılar (`kullanicilar.html`)
- ✅ Kullanıcı listeleme
- ✅ Yeni kullanıcı ekleme
- ✅ Kullanıcı güncelleme (Stored Procedure)
- ✅ Kullanıcı silme
- ✅ Kullanıcı + Hesap ekleme (Stored Procedure)
- ✅ Kullanıcı detaylarını görüntüleme

### Test Siparişleri (`test-siparisleri.html`)
- ✅ Sipariş listeleme
- ✅ Yeni sipariş ekleme
- ✅ Sipariş + Numune ekleme (Stored Procedure)
- ✅ Ödeme durumu güncelleme (Stored Procedure)
- ✅ Sipariş silme
- ✅ Sipariş detaylarını görüntüleme

### Test Paketleri (`test-paketleri.html`)
- ✅ Paket listeleme
- ✅ Yeni paket ekleme
- ✅ Paket silme
- ✅ Paket siparişlerini görüntüleme

### Adresler (`adresler.html`)
- ✅ Adres listeleme
- ✅ Yeni adres ekleme
- ✅ Adres silme
- ✅ Kullanıcı adreslerini görüntüleme

## 🎨 Tasarım Özellikleri

- **Modern Gradient Tasarım**: Mor-mavi gradient arka plan
- **Bootstrap 5**: Responsive ve modern UI bileşenleri
- **Font Awesome İkonları**: Görsel zenginlik
- **Animasyonlar**: Hover efektleri ve geçişler
- **Modal Pencereler**: Form işlemleri için
- **Alert Mesajları**: Başarı/hata bildirimleri

## 🔧 Teknik Detaylar

### Kullanılan Teknolojiler
- **HTML5**: Yapı
- **CSS3**: Stil ve animasyonlar
- **JavaScript (ES6+)**: API çağrıları ve dinamik içerik
- **Bootstrap 5.3.0**: UI framework
- **Font Awesome 6.4.0**: İkonlar

### API Entegrasyonu
- Tüm API çağrıları `fetch` API kullanılarak yapılmaktadır
- Base URL: `http://localhost:8080`
- CORS desteği: `CorsConfig.java` ile yapılandırılmıştır

### Dosya Yapısı
```
src/main/resources/static/
├── index.html                    # Ana sayfa
├── kullanicilar.html            # Kullanıcı yönetimi
├── test-siparisleri.html        # Sipariş yönetimi
├── test-paketleri.html          # Paket yönetimi
└── adresler.html                # Adres yönetimi
```

## 📝 Kullanım Örnekleri

### Kullanıcı Ekleme
1. Ana sayfadan "Kullanıcılar" kartına tıklayın
2. "Yeni Kullanıcı" butonuna tıklayın
3. Formu doldurun ve "Ekle" butonuna tıklayın

### Stored Procedure Kullanımı
1. "Kullanıcı + Hesap (SP)" butonuna tıklayın
2. Formu doldurun (e-posta ve parola hash dahil)
3. "Ekle" butonuna tıklayın
4. Hem kullanıcı hem de hesap otomatik oluşturulur

### Sipariş Ödeme
1. Test Siparişleri sayfasına gidin
2. Ödeme durumunu güncellemek istediğiniz siparişin yanındaki ödeme ikonuna tıklayın
3. Yeni ödeme durumunu seçin ve "Güncelle" butonuna tıklayın

## 🎯 Gelecek Geliştirmeler

- [ ] Tüm controller'lar için sayfalar
- [ ] Arama ve filtreleme özellikleri
- [ ] Sayfalama (pagination)
- [ ] Export özellikleri (CSV, PDF)
- [ ] Grafik ve istatistikler
- [ ] Kullanıcı yetkilendirme
- [ ] Dark mode desteği

## 🐛 Sorun Giderme

### CORS Hatası
Eğer CORS hatası alıyorsanız, `CorsConfig.java` dosyasının doğru yapılandırıldığından emin olun.

### API Bağlantı Hatası
- Uygulamanın çalıştığından emin olun (`http://localhost:8080`)
- Tarayıcı konsolunu kontrol edin (F12)
- Network sekmesinde API çağrılarını kontrol edin

### Veri Görünmüyor
- Veritabanında veri olduğundan emin olun
- API endpoint'lerinin doğru çalıştığını Postman ile test edin

## 📞 Destek

Sorularınız için:
- API dokümantasyonu: `POSTMAN_COMPLETE_API_DOCUMENTATION.md`
- Postman Collection: `GenetikDNA_Complete_API.postman_collection.json`

---

**Not**: Bu frontend, API test ve yönetim amaçlıdır. Production ortamı için ek güvenlik önlemleri alınmalıdır.

