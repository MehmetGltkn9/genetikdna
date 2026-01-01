-- Genetik DNA Veritabanı Şeması
-- PostgreSQL için tablo oluşturma script'i

-- Kullanıcı tablosu
CREATE TABLE IF NOT EXISTS kullanici (
    id SERIAL PRIMARY KEY,
    ad VARCHAR(100) NOT NULL,
    soyad VARCHAR(100) NOT NULL,
    dogum_tarihi DATE,
    cinsiyet VARCHAR(10),
    kayit_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Kullanıcı Hesap tablosu
CREATE TABLE IF NOT EXISTS kullanici_hesap (
    id SERIAL PRIMARY KEY,
    kullanici_id INTEGER REFERENCES kullanici(id),
    eposta VARCHAR(255) UNIQUE NOT NULL,
    parola_hash VARCHAR(255),
    son_giris TIMESTAMP,
    aktif_mi BOOLEAN DEFAULT true
);

-- Adres tablosu
CREATE TABLE IF NOT EXISTS adres (
    id SERIAL PRIMARY KEY,
    kullanici_id INTEGER REFERENCES kullanici(id),
    adres_tipi VARCHAR(50),
    ulke VARCHAR(100),
    sehir VARCHAR(100),
    posta_kodu VARCHAR(20),
    detayli_adres TEXT
);

-- Aile Üyeleri tablosu
CREATE TABLE IF NOT EXISTS aile_uyeleri (
    id SERIAL PRIMARY KEY,
    kullanici_id INTEGER REFERENCES kullanici(id),
    ad_soyad VARCHAR(200),
    iliski_turu VARCHAR(50),
    dogum_yeri VARCHAR(100),
    vefat_tarihi DATE
);

-- Test Paketi tablosu
CREATE TABLE IF NOT EXISTS test_paketi (
    id SERIAL PRIMARY KEY,
    paket_adi VARCHAR(200) NOT NULL,
    fiyat DECIMAL(10,2),
    icerik_aciklamasi TEXT
);

-- Test Siparişi tablosu
CREATE TABLE IF NOT EXISTS test_siparisi (
    id SERIAL PRIMARY KEY,
    kullanici_id INTEGER REFERENCES kullanici(id),
    paket_id INTEGER REFERENCES test_paketi(id),
    siparis_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    toplam_tutar DECIMAL(10,2),
    odeme_durumu VARCHAR(50)
);

-- Numune tablosu
CREATE TABLE IF NOT EXISTS numune (
    id SERIAL PRIMARY KEY,
    siparis_id INTEGER REFERENCES test_siparisi(id),
    barkod_id VARCHAR(100) UNIQUE,
    numune_tipi VARCHAR(50),
    laboratuvara_varis DATE,
    durum VARCHAR(50)
);

-- Laboratuvar Analizi tablosu
CREATE TABLE IF NOT EXISTS laboratuvar_analizi (
    id SERIAL PRIMARY KEY,
    numune_id INTEGER REFERENCES numune(id),
    analiz_baslangic TIMESTAMP,
    analiz_bitis TIMESTAMP,
    teknisyen_adi VARCHAR(100),
    cihaz_bilgisi VARCHAR(200),
    kalite_kontrol_sonucu VARCHAR(100)
);

-- Genetik Test Sonucu tablosu
CREATE TABLE IF NOT EXISTS genetik_test_sonucu (
    id SERIAL PRIMARY KEY,
    kullanici_id INTEGER REFERENCES kullanici(id),
    analiz_id INTEGER REFERENCES laboratuvar_analizi(id),
    yayim_tarihi TIMESTAMP,
    veri_surumu VARCHAR(50)
);

-- Genetik Varyant tablosu
CREATE TABLE IF NOT EXISTS genetik_varyant (
    id SERIAL PRIMARY KEY,
    rs_id VARCHAR(50) UNIQUE,
    kromozom VARCHAR(10),
    konum INTEGER,
    referans_alel VARCHAR(10)
);

-- Kullanıcı Varyant Sonucu tablosu
CREATE TABLE IF NOT EXISTS kullanici_varyant_sonucu (
    id BIGSERIAL PRIMARY KEY,
    sonuc_id INTEGER REFERENCES genetik_test_sonucu(id),
    varyant_id INTEGER REFERENCES genetik_varyant(id),
    tespit_edilen_alel VARCHAR(10)
);

-- Hastalık Tanımı tablosu
CREATE TABLE IF NOT EXISTS hastalik_tanimi (
    id SERIAL PRIMARY KEY,
    hastalik_adi VARCHAR(200) NOT NULL,
    icd_kodu VARCHAR(20) UNIQUE,
    bilimsel_tanim TEXT
);

-- Hastalık Risk Skoru tablosu
CREATE TABLE IF NOT EXISTS hastalik_risk_skoru (
    id SERIAL PRIMARY KEY,
    sonuc_id INTEGER REFERENCES genetik_test_sonucu(id),
    hastalik_id INTEGER REFERENCES hastalik_tanimi(id),
    risk_yuzdesi DECIMAL(5,2),
    risk_seviyesi VARCHAR(50)
);

-- Etnik Köken Raporu tablosu
CREATE TABLE IF NOT EXISTS etnik_koken_raporu (
    id SERIAL PRIMARY KEY,
    sonuc_id INTEGER REFERENCES genetik_test_sonucu(id),
    bolge_adi VARCHAR(100),
    yuzde_orani DECIMAL(5,2),
    rapor_detayi TEXT
);

-- Kullanıcı Genetik Verisi tablosu
CREATE TABLE IF NOT EXISTS kullanici_genetik_verisi (
    id SERIAL PRIMARY KEY,
    sonuc_id INTEGER REFERENCES genetik_test_sonucu(id),
    ham_veri_depolama_yolu VARCHAR(500),
    dosya_boyutu_mb INTEGER
);

-- Tedaviye Yanıt tablosu
CREATE TABLE IF NOT EXISTS tedaviye_yanit (
    id SERIAL PRIMARY KEY,
    sonuc_id INTEGER REFERENCES genetik_test_sonucu(id),
    ilac_adi VARCHAR(200),
    yanit_tahmini VARCHAR(100),
    oneriler TEXT
);

-- Genetik Danışmanlık tablosu
CREATE TABLE IF NOT EXISTS genetik_danismanlik (
    id SERIAL PRIMARY KEY,
    kullanici_id INTEGER REFERENCES kullanici(id),
    danisman_adi VARCHAR(100),
    gorusme_tarihi TIMESTAMP,
    gorusme_ozeti TEXT
);

-- Soy Ağacı Bağlantısı tablosu
CREATE TABLE IF NOT EXISTS soyagaci_baglantisi (
    id SERIAL PRIMARY KEY,
    kullanici_bir_id INTEGER REFERENCES kullanici(id),
    kullanici_iki_id INTEGER REFERENCES kullanici(id),
    tahmini_iliski VARCHAR(50),
    paylasilan_dna DECIMAL(5,2)
);

-- Veri Erişim İzni tablosu
CREATE TABLE IF NOT EXISTS veri_erisim_izni (
    id SERIAL PRIMARY KEY,
    kullanici_id INTEGER REFERENCES kullanici(id),
    izin_tipi VARCHAR(100),
    izin_verildi BOOLEAN DEFAULT false,
    izin_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Denetim Kaydı tablosu
CREATE TABLE IF NOT EXISTS denetim_kaydi (
    id BIGSERIAL PRIMARY KEY,
    islem_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    kullanici_id INTEGER REFERENCES kullanici(id),
    etkilenen_tablo VARCHAR(100),
    etkilenen_id INTEGER,
    islem_tipi VARCHAR(20),
    aciklama TEXT
);

-- İndeksler
CREATE INDEX IF NOT EXISTS idx_kullanici_hesap_kullanici_id ON kullanici_hesap(kullanici_id);
CREATE INDEX IF NOT EXISTS idx_adres_kullanici_id ON adres(kullanici_id);
CREATE INDEX IF NOT EXISTS idx_test_siparisi_kullanici_id ON test_siparisi(kullanici_id);
CREATE INDEX IF NOT EXISTS idx_genetik_test_sonucu_kullanici_id ON genetik_test_sonucu(kullanici_id);

