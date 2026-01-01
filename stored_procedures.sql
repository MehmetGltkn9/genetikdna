-- Genetik DNA Projesi için Stored Procedure'lar
-- PostgreSQL Stored Procedure'ları

-- 1. Kullanıcı Ekleme ve Otomatik Hesap Oluşturma
CREATE OR REPLACE FUNCTION sp_kullanici_ve_hesap_ekle(
    p_ad VARCHAR(100),
    p_soyad VARCHAR(100),
    p_dogum_tarihi DATE,
    p_cinsiyet VARCHAR(10),
    p_eposta VARCHAR(255),
    p_parola_hash VARCHAR(255)
)
RETURNS TABLE(
    kullanici_id INTEGER,
    hesap_id INTEGER,
    mesaj TEXT
) AS $$
DECLARE
    v_kullanici_id INTEGER;
    v_hesap_id INTEGER;
BEGIN
    -- Kullanıcı ekle
    INSERT INTO kullanici (ad, soyad, dogum_tarihi, cinsiyet, kayit_tarihi)
    VALUES (p_ad, p_soyad, p_dogum_tarihi, p_cinsiyet, CURRENT_TIMESTAMP)
    RETURNING id INTO v_kullanici_id;
    
    -- Otomatik hesap oluştur
    INSERT INTO kullanici_hesap (kullanici_id, eposta, parola_hash, aktif_mi)
    VALUES (v_kullanici_id, p_eposta, p_parola_hash, true)
    RETURNING id INTO v_hesap_id;
    
    -- Denetim kaydı
    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
    VALUES (v_kullanici_id, 'kullanici', v_kullanici_id, 'INSERT', 'Kullanıcı ve hesap otomatik oluşturuldu');
    
    RETURN QUERY SELECT v_kullanici_id, v_hesap_id, 'Kullanıcı ve hesap başarıyla oluşturuldu'::TEXT;
END;
$$ LANGUAGE plpgsql;

-- 2. Test Siparişi Ekleme ve Otomatik Numune Oluşturma
CREATE OR REPLACE FUNCTION sp_test_siparisi_ve_numune_ekle(
    p_kullanici_id INTEGER,
    p_paket_id INTEGER,
    p_toplam_tutar DOUBLE PRECISION,
    p_barkod_id VARCHAR(100),
    p_numune_tipi VARCHAR(50)
)
RETURNS TABLE(
    siparis_id INTEGER,
    numune_id INTEGER,
    mesaj TEXT
) AS $$
DECLARE
    v_siparis_id INTEGER;
    v_numune_id INTEGER;
BEGIN
    -- Test siparişi ekle
    INSERT INTO test_siparisi (kullanici_id, paket_id, siparis_tarihi, toplam_tutar, odeme_durumu)
    VALUES (p_kullanici_id, p_paket_id, CURRENT_TIMESTAMP, p_toplam_tutar, 'Beklemede')
    RETURNING id INTO v_siparis_id;
    
    -- Otomatik numune oluştur
    INSERT INTO numune (siparis_id, barkod_id, numune_tipi, durum)
    VALUES (v_siparis_id, p_barkod_id, p_numune_tipi, 'Beklemede')
    RETURNING id INTO v_numune_id;
    
    -- Denetim kaydı
    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
    VALUES (p_kullanici_id, 'test_siparisi', v_siparis_id, 'INSERT', 'Sipariş ve numune otomatik oluşturuldu');
    
    RETURN QUERY SELECT v_siparis_id, v_numune_id, 'Sipariş ve numune başarıyla oluşturuldu'::TEXT;
END;
$$ LANGUAGE plpgsql;

-- 3. Genetik Test Sonucu ve İlgili Verileri Ekleme
CREATE OR REPLACE FUNCTION sp_genetik_test_sonucu_ekle(
    p_kullanici_id INTEGER,
    p_analiz_id INTEGER,
    p_veri_surumu VARCHAR(50),
    p_ham_veri_yolu VARCHAR(500),
    p_dosya_boyutu_mb INTEGER
)
RETURNS TABLE(
    sonuc_id INTEGER,
    veri_id INTEGER,
    mesaj TEXT
) AS $$
DECLARE
    v_sonuc_id INTEGER;
    v_veri_id INTEGER;
BEGIN
    -- Genetik test sonucu ekle
    INSERT INTO genetik_test_sonucu (kullanici_id, analiz_id, yayim_tarihi, veri_surumu)
    VALUES (p_kullanici_id, p_analiz_id, CURRENT_TIMESTAMP, p_veri_surumu)
    RETURNING id INTO v_sonuc_id;
    
    -- Genetik veri ekle
    INSERT INTO kullanici_genetik_verisi (sonuc_id, ham_veri_depolama_yolu, dosya_boyutu_mb)
    VALUES (v_sonuc_id, p_ham_veri_yolu, p_dosya_boyutu_mb)
    RETURNING id INTO v_veri_id;
    
    -- Denetim kaydı
    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
    VALUES (p_kullanici_id, 'genetik_test_sonucu', v_sonuc_id, 'INSERT', 'Test sonucu ve veri eklendi');
    
    RETURN QUERY SELECT v_sonuc_id, v_veri_id, 'Test sonucu ve veri başarıyla eklendi'::TEXT;
END;
$$ LANGUAGE plpgsql;

-- 4. Kullanıcı Güncelleme ve Denetim Kaydı
CREATE OR REPLACE FUNCTION sp_kullanici_guncelle(
    p_id INTEGER,
    p_ad VARCHAR(100),
    p_soyad VARCHAR(100),
    p_dogum_tarihi DATE,
    p_cinsiyet VARCHAR(10),
    p_kullanici_id INTEGER
)
RETURNS TABLE(
    guncellenen_id INTEGER,
    mesaj TEXT
) AS $$
DECLARE
    v_guncellenen_id INTEGER;
BEGIN
    -- Kullanıcı güncelle
    UPDATE kullanici
    SET ad = p_ad,
        soyad = p_soyad,
        dogum_tarihi = p_dogum_tarihi,
        cinsiyet = p_cinsiyet
    WHERE id = p_id
    RETURNING id INTO v_guncellenen_id;
    
    -- Denetim kaydı
    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
    VALUES (p_kullanici_id, 'kullanici', v_guncellenen_id, 'UPDATE', 'Kullanıcı bilgileri güncellendi');
    
    RETURN QUERY SELECT v_guncellenen_id, 'Kullanıcı başarıyla güncellendi'::TEXT;
END;
$$ LANGUAGE plpgsql;

-- 5. Hastalık Risk Skoru ve Tedaviye Yanıt Ekleme
CREATE OR REPLACE FUNCTION sp_hastalik_risk_ve_tedavi_ekle(
    p_sonuc_id INTEGER,
    p_hastalik_id INTEGER,
    p_risk_yuzdesi DOUBLE PRECISION,
    p_risk_seviyesi VARCHAR(50),
    p_ilac_adi VARCHAR(200),
    p_yanit_tahmini VARCHAR(100),
    p_oneriler TEXT
)
RETURNS TABLE(
    risk_id INTEGER,
    tedavi_id INTEGER,
    mesaj TEXT
) AS $$
DECLARE
    v_risk_id INTEGER;
    v_tedavi_id INTEGER;
    v_kullanici_id INTEGER;
BEGIN
    -- Kullanıcı ID'yi al
    SELECT kullanici_id INTO v_kullanici_id FROM genetik_test_sonucu WHERE id = p_sonuc_id;
    
    -- Hastalık risk skoru ekle
    INSERT INTO hastalik_risk_skoru (sonuc_id, hastalik_id, risk_yuzdesi, risk_seviyesi)
    VALUES (p_sonuc_id, p_hastalik_id, p_risk_yuzdesi, p_risk_seviyesi)
    RETURNING id INTO v_risk_id;
    
    -- Tedaviye yanıt ekle
    INSERT INTO tedaviye_yanit (sonuc_id, ilac_adi, yanit_tahmini, oneriler)
    VALUES (p_sonuc_id, p_ilac_adi, p_yanit_tahmini, p_oneriler)
    RETURNING id INTO v_tedavi_id;
    
    -- Denetim kaydı
    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
    VALUES (v_kullanici_id, 'hastalik_risk_skoru', v_risk_id, 'INSERT', 'Risk skoru ve tedavi yanıtı eklendi');
    
    RETURN QUERY SELECT v_risk_id, v_tedavi_id, 'Risk skoru ve tedavi yanıtı başarıyla eklendi'::TEXT;
END;
$$ LANGUAGE plpgsql;

-- 6. Laboratuvar Analizi Tamamlama ve Test Sonucu Oluşturma
CREATE OR REPLACE FUNCTION sp_laboratuvar_analizi_tamamla(
    p_numune_id INTEGER,
    p_analiz_bitis TIMESTAMP,
    p_kalite_kontrol_sonucu VARCHAR(100),
    p_kullanici_id INTEGER,
    p_veri_surumu VARCHAR(50)
)
RETURNS TABLE(
    analiz_id INTEGER,
    sonuc_id INTEGER,
    mesaj TEXT
) AS $$
DECLARE
    v_analiz_id INTEGER;
    v_sonuc_id INTEGER;
BEGIN
    -- Mevcut analizi güncelle
    UPDATE laboratuvar_analizi
    SET analiz_bitis = p_analiz_bitis,
        kalite_kontrol_sonucu = p_kalite_kontrol_sonucu
    WHERE numune_id = p_numune_id
    RETURNING id INTO v_analiz_id;
    
    -- Eğer analiz yoksa oluştur
    IF v_analiz_id IS NULL THEN
        INSERT INTO laboratuvar_analizi (numune_id, analiz_baslangic, analiz_bitis, kalite_kontrol_sonucu)
        VALUES (p_numune_id, CURRENT_TIMESTAMP, p_analiz_bitis, p_kalite_kontrol_sonucu)
        RETURNING id INTO v_analiz_id;
    END IF;
    
    -- Test sonucu oluştur
    INSERT INTO genetik_test_sonucu (kullanici_id, analiz_id, yayim_tarihi, veri_surumu)
    VALUES (p_kullanici_id, v_analiz_id, CURRENT_TIMESTAMP, p_veri_surumu)
    RETURNING id INTO v_sonuc_id;
    
    -- Numune durumunu güncelle
    UPDATE numune SET durum = 'Tamamlandı' WHERE id = p_numune_id;
    
    -- Denetim kaydı
    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
    VALUES (p_kullanici_id, 'laboratuvar_analizi', v_analiz_id, 'UPDATE', 'Analiz tamamlandı ve test sonucu oluşturuldu');
    
    RETURN QUERY SELECT v_analiz_id, v_sonuc_id, 'Analiz tamamlandı ve test sonucu oluşturuldu'::TEXT;
END;
$$ LANGUAGE plpgsql;

-- 7. Kullanıcı Varyant Sonuçlarını Toplu Ekleme
CREATE OR REPLACE FUNCTION sp_varyant_sonuclari_toplu_ekle(
    p_sonuc_id INTEGER,
    p_varyant_verileri TEXT  -- JSON formatında: [{"varyant_id":1,"alel":"A"},{"varyant_id":2,"alel":"G"}]
)
RETURNS TABLE(
    eklenen_sayisi INTEGER,
    mesaj TEXT
) AS $$
DECLARE
    v_eklenen INTEGER := 0;
    v_json JSON;
    v_item JSON;
    v_varyant_id INTEGER;
    v_alel VARCHAR(10);
BEGIN
    -- JSON'u parse et
    v_json := p_varyant_verileri::JSON;
    
    -- Her varyant için döngü
    FOR v_item IN SELECT * FROM json_array_elements(v_json)
    LOOP
        v_varyant_id := (v_item->>'varyant_id')::INTEGER;
        v_alel := v_item->>'alel';
        
        -- Varyant sonucu ekle
        INSERT INTO kullanici_varyant_sonucu (sonuc_id, varyant_id, tespit_edilen_alel)
        VALUES (p_sonuc_id, v_varyant_id, v_alel);
        
        v_eklenen := v_eklenen + 1;
    END LOOP;
    
    RETURN QUERY SELECT v_eklenen, format('%s varyant sonucu başarıyla eklendi', v_eklenen)::TEXT;
END;
$$ LANGUAGE plpgsql;

-- 8. Test Siparişi Ödeme ve Durum Güncelleme
CREATE OR REPLACE FUNCTION sp_test_siparisi_odeme(
    p_siparis_id INTEGER,
    p_odeme_durumu VARCHAR(50)
)
RETURNS TABLE(
    guncellenen_id INTEGER,
    mesaj TEXT
) AS $$
DECLARE
    v_kullanici_id INTEGER;
BEGIN
    -- Kullanıcı ID'yi al
    SELECT kullanici_id INTO v_kullanici_id FROM test_siparisi WHERE id = p_siparis_id;
    
    -- Sipariş durumunu güncelle
    UPDATE test_siparisi
    SET odeme_durumu = p_odeme_durumu
    WHERE id = p_siparis_id;
    
    -- Eğer ödeme yapıldıysa numune durumunu güncelle
    IF p_odeme_durumu = 'Ödendi' THEN
        UPDATE numune
        SET durum = 'Hazırlanıyor'
        WHERE siparis_id = p_siparis_id;
    END IF;
    
    -- Denetim kaydı
    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
    VALUES (v_kullanici_id, 'test_siparisi', p_siparis_id, 'UPDATE', format('Sipariş ödeme durumu güncellendi: %s', p_odeme_durumu));
    
    RETURN QUERY SELECT p_siparis_id, format('Sipariş ödeme durumu güncellendi: %s', p_odeme_durumu)::TEXT;
END;
$$ LANGUAGE plpgsql;

