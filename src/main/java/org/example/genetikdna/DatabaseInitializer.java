package org.example.genetikdna;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        createAllTables();
        createIndexes();
        createStoredProcedures();
        createReportingFunctions();
        insertSampleData();
    }

    private void createAllTables() {

        List<String> tableDefinitions = Arrays.asList(
                """
                CREATE TABLE IF NOT EXISTS kullanici (
                    id SERIAL PRIMARY KEY,
                    ad VARCHAR(100) NOT NULL,
                    soyad VARCHAR(100) NOT NULL,
                    dogum_tarihi DATE,
                    cinsiyet VARCHAR(10),
                    kayit_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS kullanici_hesap (
                    id SERIAL PRIMARY KEY,
                    kullanici_id INTEGER REFERENCES kullanici(id),
                    eposta VARCHAR(255) UNIQUE NOT NULL,
                    parola_hash VARCHAR(255),
                    son_giris TIMESTAMP,
                    aktif_mi BOOLEAN DEFAULT true
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS adres (
                    id SERIAL PRIMARY KEY,
                    kullanici_id INTEGER REFERENCES kullanici(id),
                    adres_tipi VARCHAR(50),
                    ulke VARCHAR(100),
                    sehir VARCHAR(100),
                    posta_kodu VARCHAR(20),
                    detayli_adres TEXT
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS aile_uyeleri (
                    id SERIAL PRIMARY KEY,
                    kullanici_id INTEGER REFERENCES kullanici(id),
                    ad_soyad VARCHAR(200),
                    iliski_turu VARCHAR(50),
                    dogum_yeri VARCHAR(100),
                    vefat_tarihi DATE
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS test_paketi (
                    id SERIAL PRIMARY KEY,
                    paket_adi VARCHAR(200) NOT NULL,
                    fiyat DECIMAL(10,2),
                    icerik_aciklamasi TEXT
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS test_siparisi (
                    id SERIAL PRIMARY KEY,
                    kullanici_id INTEGER REFERENCES kullanici(id),
                    paket_id INTEGER REFERENCES test_paketi(id),
                    siparis_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    toplam_tutar DECIMAL(10,2),
                    odeme_durumu VARCHAR(50)
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS numune (
                    id SERIAL PRIMARY KEY,
                    siparis_id INTEGER REFERENCES test_siparisi(id),
                    barkod_id VARCHAR(100) UNIQUE,
                    numune_tipi VARCHAR(50),
                    laboratuvara_varis DATE,
                    durum VARCHAR(50)
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS laboratuvar_analizi (
                    id SERIAL PRIMARY KEY,
                    numune_id INTEGER REFERENCES numune(id),
                    analiz_baslangic TIMESTAMP,
                    analiz_bitis TIMESTAMP,
                    teknisyen_adi VARCHAR(100),
                    cihaz_bilgisi VARCHAR(200),
                    kalite_kontrol_sonucu VARCHAR(100)
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS genetik_test_sonucu (
                    id SERIAL PRIMARY KEY,
                    kullanici_id INTEGER REFERENCES kullanici(id),
                    analiz_id INTEGER REFERENCES laboratuvar_analizi(id),
                    yayim_tarihi TIMESTAMP,
                    veri_surumu VARCHAR(50)
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS genetik_varyant (
                    id SERIAL PRIMARY KEY,
                    rs_id VARCHAR(50) UNIQUE,
                    kromozom VARCHAR(10),
                    konum INTEGER,
                    referans_alel VARCHAR(10)
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS kullanici_varyant_sonucu (
                    id BIGSERIAL PRIMARY KEY,
                    sonuc_id INTEGER REFERENCES genetik_test_sonucu(id),
                    varyant_id INTEGER REFERENCES genetik_varyant(id),
                    tespit_edilen_alel VARCHAR(10)
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS hastalik_tanimi (
                    id SERIAL PRIMARY KEY,
                    hastalik_adi VARCHAR(200) NOT NULL,
                    icd_kodu VARCHAR(20) UNIQUE,
                    bilimsel_tanim TEXT
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS hastalik_risk_skoru (
                    id SERIAL PRIMARY KEY,
                    sonuc_id INTEGER REFERENCES genetik_test_sonucu(id),
                    hastalik_id INTEGER REFERENCES hastalik_tanimi(id),
                    risk_yuzdesi DECIMAL(5,2),
                    risk_seviyesi VARCHAR(50)
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS etnik_koken_raporu (
                    id SERIAL PRIMARY KEY,
                    sonuc_id INTEGER REFERENCES genetik_test_sonucu(id),
                    bolge_adi VARCHAR(100),
                    yuzde_orani DECIMAL(5,2),
                    rapor_detayi TEXT
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS kullanici_genetik_verisi (
                    id SERIAL PRIMARY KEY,
                    sonuc_id INTEGER REFERENCES genetik_test_sonucu(id),
                    ham_veri_depolama_yolu VARCHAR(500),
                    dosya_boyutu_mb INTEGER
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS tedaviye_yanit (
                    id SERIAL PRIMARY KEY,
                    sonuc_id INTEGER REFERENCES genetik_test_sonucu(id),
                    ilac_adi VARCHAR(200),
                    yanit_tahmini VARCHAR(100),
                    oneriler TEXT
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS genetik_danismanlik (
                    id SERIAL PRIMARY KEY,
                    kullanici_id INTEGER REFERENCES kullanici(id),
                    danisman_adi VARCHAR(100),
                    gorusme_tarihi TIMESTAMP,
                    gorusme_ozeti TEXT
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS soyagaci_baglantisi (
                    id SERIAL PRIMARY KEY,
                    kullanici_bir_id INTEGER REFERENCES kullanici(id),
                    kullanici_iki_id INTEGER REFERENCES kullanici(id),
                    tahmini_iliski VARCHAR(50),
                    paylasilan_dna DECIMAL(5,2)
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS veri_erisim_izni (
                    id SERIAL PRIMARY KEY,
                    kullanici_id INTEGER REFERENCES kullanici(id),
                    izin_tipi VARCHAR(100),
                    izin_verildi BOOLEAN DEFAULT false,
                    izin_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS denetim_kaydi (
                    id BIGSERIAL PRIMARY KEY,
                    islem_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    kullanici_id INTEGER REFERENCES kullanici(id),
                    etkilenen_tablo VARCHAR(100),
                    etkilenen_id INTEGER,
                    islem_tipi VARCHAR(20),
                    aciklama TEXT
                );
                """
        );

        for (String tableDefinition : tableDefinitions) {
            try {
                jdbcTemplate.execute(tableDefinition);
                System.out.println("Tablo başarıyla oluşturuldu: " + extractTableName(tableDefinition));
            } catch (Exception e) {
                System.err.println("Tablo oluşturulurken hata: " + extractTableName(tableDefinition));
                System.err.println("Hata: " + e.getMessage());
            }
        }

        createIndexes();
        createStoredProcedures();
    }

    private void createIndexes() {
        List<String> indexDefinitions = Arrays.asList(
                "CREATE INDEX IF NOT EXISTS idx_kullanici_hesap_kullanici_id ON kullanici_hesap(kullanici_id);",
                "CREATE INDEX IF NOT EXISTS idx_adres_kullanici_id ON adres(kullanici_id);",
                "CREATE INDEX IF NOT EXISTS idx_test_siparisi_kullanici_id ON test_siparisi(kullanici_id);",
                "CREATE INDEX IF NOT EXISTS idx_genetik_test_sonucu_kullanici_id ON genetik_test_sonucu(kullanici_id);",
                "CREATE INDEX IF NOT EXISTS idx_aile_uyeleri_kullanici_id ON aile_uyeleri(kullanici_id);",
                "CREATE INDEX IF NOT EXISTS idx_numune_siparis_id ON numune(siparis_id);",
                "CREATE INDEX IF NOT EXISTS idx_laboratuvar_analizi_numune_id ON laboratuvar_analizi(numune_id);"
        );

        for (String indexDefinition : indexDefinitions) {
            try {
                jdbcTemplate.execute(indexDefinition);
                System.out.println("İndeks başarıyla oluşturuldu: " + extractIndexName(indexDefinition));
            } catch (Exception e) {
                // PostgreSQL'de bazı versiyonlarda IF NOT EXISTS desteklenmeyebilir
                // Bu durumda index zaten varsa hata vermez, sadece log yazarız
                if (e.getMessage() != null && e.getMessage().contains("already exists")) {
                    System.out.println("İndeks zaten mevcut: " + extractIndexName(indexDefinition));
                } else {
                    System.err.println("İndeks oluşturulurken hata: " + extractIndexName(indexDefinition));
                    System.err.println("Hata: " + e.getMessage());
                }
            }
        }
    }

    private String extractTableName(String tableDefinition) {
        int start = tableDefinition.indexOf("CREATE TABLE IF NOT EXISTS") + "CREATE TABLE IF NOT EXISTS".length();
        int end = tableDefinition.indexOf("(", start);
        if (end == -1) end = tableDefinition.length();
        return tableDefinition.substring(start, end).trim();
    }

    private void createStoredProcedures() {
        List<String> procedureDefinitions = Arrays.asList(
                """
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
                    INSERT INTO kullanici (ad, soyad, dogum_tarihi, cinsiyet, kayit_tarihi)
                    VALUES (p_ad, p_soyad, p_dogum_tarihi, p_cinsiyet, CURRENT_TIMESTAMP)
                    RETURNING id INTO v_kullanici_id;
                    
                    INSERT INTO kullanici_hesap (kullanici_id, eposta, parola_hash, aktif_mi)
                    VALUES (v_kullanici_id, p_eposta, p_parola_hash, true)
                    RETURNING id INTO v_hesap_id;
                    
                    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
                    VALUES (v_kullanici_id, 'kullanici', v_kullanici_id, 'INSERT', 'Kullanıcı ve hesap otomatik oluşturuldu');
                    
                    RETURN QUERY SELECT v_kullanici_id, v_hesap_id, 'Kullanıcı ve hesap başarıyla oluşturuldu'::TEXT;
                END;
                $$ LANGUAGE plpgsql;
                """,
                """
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
                    INSERT INTO test_siparisi (kullanici_id, paket_id, siparis_tarihi, toplam_tutar, odeme_durumu)
                    VALUES (p_kullanici_id, p_paket_id, CURRENT_TIMESTAMP, p_toplam_tutar, 'Beklemede')
                    RETURNING id INTO v_siparis_id;
                    
                    INSERT INTO numune (siparis_id, barkod_id, numune_tipi, durum)
                    VALUES (v_siparis_id, p_barkod_id, p_numune_tipi, 'Beklemede')
                    RETURNING id INTO v_numune_id;
                    
                    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
                    VALUES (p_kullanici_id, 'test_siparisi', v_siparis_id, 'INSERT', 'Sipariş ve numune otomatik oluşturuldu');
                    
                    RETURN QUERY SELECT v_siparis_id, v_numune_id, 'Sipariş ve numune başarıyla oluşturuldu'::TEXT;
                END;
                $$ LANGUAGE plpgsql;
                """,
                """
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
                    INSERT INTO genetik_test_sonucu (kullanici_id, analiz_id, yayim_tarihi, veri_surumu)
                    VALUES (p_kullanici_id, p_analiz_id, CURRENT_TIMESTAMP, p_veri_surumu)
                    RETURNING id INTO v_sonuc_id;
                    
                    INSERT INTO kullanici_genetik_verisi (sonuc_id, ham_veri_depolama_yolu, dosya_boyutu_mb)
                    VALUES (v_sonuc_id, p_ham_veri_yolu, p_dosya_boyutu_mb)
                    RETURNING id INTO v_veri_id;
                    
                    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
                    VALUES (p_kullanici_id, 'genetik_test_sonucu', v_sonuc_id, 'INSERT', 'Test sonucu ve veri eklendi');
                    
                    RETURN QUERY SELECT v_sonuc_id, v_veri_id, 'Test sonucu ve veri başarıyla eklendi'::TEXT;
                END;
                $$ LANGUAGE plpgsql;
                """,
                """
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
                    UPDATE kullanici
                    SET ad = p_ad,
                        soyad = p_soyad,
                        dogum_tarihi = p_dogum_tarihi,
                        cinsiyet = p_cinsiyet
                    WHERE id = p_id
                    RETURNING id INTO v_guncellenen_id;
                    
                    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
                    VALUES (p_kullanici_id, 'kullanici', v_guncellenen_id, 'UPDATE', 'Kullanıcı bilgileri güncellendi');
                    
                    RETURN QUERY SELECT v_guncellenen_id, 'Kullanıcı başarıyla güncellendi'::TEXT;
                END;
                $$ LANGUAGE plpgsql;
                """,
                """
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
                    SELECT kullanici_id INTO v_kullanici_id FROM genetik_test_sonucu WHERE id = p_sonuc_id;
                    
                    INSERT INTO hastalik_risk_skoru (sonuc_id, hastalik_id, risk_yuzdesi, risk_seviyesi)
                    VALUES (p_sonuc_id, p_hastalik_id, p_risk_yuzdesi, p_risk_seviyesi)
                    RETURNING id INTO v_risk_id;
                    
                    INSERT INTO tedaviye_yanit (sonuc_id, ilac_adi, yanit_tahmini, oneriler)
                    VALUES (p_sonuc_id, p_ilac_adi, p_yanit_tahmini, p_oneriler)
                    RETURNING id INTO v_tedavi_id;
                    
                    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
                    VALUES (v_kullanici_id, 'hastalik_risk_skoru', v_risk_id, 'INSERT', 'Risk skoru ve tedavi yanıtı eklendi');
                    
                    RETURN QUERY SELECT v_risk_id, v_tedavi_id, 'Risk skoru ve tedavi yanıtı başarıyla eklendi'::TEXT;
                END;
                $$ LANGUAGE plpgsql;
                """,
                """
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
                    UPDATE laboratuvar_analizi
                    SET analiz_bitis = p_analiz_bitis,
                        kalite_kontrol_sonucu = p_kalite_kontrol_sonucu
                    WHERE numune_id = p_numune_id
                    RETURNING id INTO v_analiz_id;
                    
                    IF v_analiz_id IS NULL THEN
                        INSERT INTO laboratuvar_analizi (numune_id, analiz_baslangic, analiz_bitis, kalite_kontrol_sonucu)
                        VALUES (p_numune_id, CURRENT_TIMESTAMP, p_analiz_bitis, p_kalite_kontrol_sonucu)
                        RETURNING id INTO v_analiz_id;
                    END IF;
                    
                    INSERT INTO genetik_test_sonucu (kullanici_id, analiz_id, yayim_tarihi, veri_surumu)
                    VALUES (p_kullanici_id, v_analiz_id, CURRENT_TIMESTAMP, p_veri_surumu)
                    RETURNING id INTO v_sonuc_id;
                    
                    UPDATE numune SET durum = 'Tamamlandı' WHERE id = p_numune_id;
                    
                    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
                    VALUES (p_kullanici_id, 'laboratuvar_analizi', v_analiz_id, 'UPDATE', 'Analiz tamamlandı ve test sonucu oluşturuldu');
                    
                    RETURN QUERY SELECT v_analiz_id, v_sonuc_id, 'Analiz tamamlandı ve test sonucu oluşturuldu'::TEXT;
                END;
                $$ LANGUAGE plpgsql;
                """,
                """
                CREATE OR REPLACE FUNCTION sp_varyant_sonuclari_toplu_ekle(
                    p_sonuc_id INTEGER,
                    p_varyant_verileri TEXT
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
                    v_json := p_varyant_verileri::JSON;
                    
                    FOR v_item IN SELECT * FROM json_array_elements(v_json)
                    LOOP
                        v_varyant_id := (v_item->>'varyant_id')::INTEGER;
                        v_alel := v_item->>'alel';
                        
                        INSERT INTO kullanici_varyant_sonucu (sonuc_id, varyant_id, tespit_edilen_alel)
                        VALUES (p_sonuc_id, v_varyant_id, v_alel);
                        
                        v_eklenen := v_eklenen + 1;
                    END LOOP;
                    
                    RETURN QUERY SELECT v_eklenen, format('%s varyant sonucu başarıyla eklendi', v_eklenen)::TEXT;
                END;
                $$ LANGUAGE plpgsql;
                """,
                """
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
                    SELECT kullanici_id INTO v_kullanici_id FROM test_siparisi WHERE id = p_siparis_id;
                    
                    UPDATE test_siparisi
                    SET odeme_durumu = p_odeme_durumu
                    WHERE id = p_siparis_id;
                    
                    IF p_odeme_durumu = 'Ödendi' THEN
                        UPDATE numune
                        SET durum = 'Hazırlanıyor'
                        WHERE siparis_id = p_siparis_id;
                    END IF;
                    
                    INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama)
                    VALUES (v_kullanici_id, 'test_siparisi', p_siparis_id, 'UPDATE', format('Sipariş ödeme durumu güncellendi: %s', p_odeme_durumu));
                    
                    RETURN QUERY SELECT p_siparis_id, format('Sipariş ödeme durumu güncellendi: %s', p_odeme_durumu)::TEXT;
                END;
                $$ LANGUAGE plpgsql;
                """
        );

        for (String procedureDefinition : procedureDefinitions) {
            try {
                jdbcTemplate.execute(procedureDefinition);
                System.out.println("Stored Procedure başarıyla oluşturuldu: " + extractProcedureName(procedureDefinition));
            } catch (Exception e) {
                System.err.println("Stored Procedure oluşturulurken hata: " + extractProcedureName(procedureDefinition));
                System.err.println("Hata: " + e.getMessage());
            }
        }
    }

    private String extractProcedureName(String procedureDefinition) {
        int start = procedureDefinition.indexOf("FUNCTION sp_") + "FUNCTION ".length();
        int end = procedureDefinition.indexOf("(", start);
        if (end == -1) end = procedureDefinition.length();
        return procedureDefinition.substring(start, end).trim();
    }

    private void createReportingFunctions() {
        List<String> functionDefinitions = Arrays.asList(
                """
                CREATE OR REPLACE FUNCTION fn_kullanici_detayli_raporu(p_kullanici_id INTEGER)
                RETURNS TABLE(
                    kullanici_id INTEGER,
                    kullanici_adi VARCHAR,
                    kullanici_soyadi VARCHAR,
                    kayit_tarihi TIMESTAMP,
                    toplam_siparis_sayisi BIGINT,
                    toplam_harcama DOUBLE PRECISION,
                    odeme_bekleyen_siparis_sayisi BIGINT,
                    test_sonucu_sayisi BIGINT,
                    risk_skoru_sayisi BIGINT,
                    ortalama_risk_yuzdesi DOUBLE PRECISION,
                    en_yuksek_risk_hastalik VARCHAR,
                    numune_sayisi BIGINT,
                    aktif_hesap_sayisi BIGINT,
                    adres_sayisi BIGINT
                ) AS $$
                BEGIN
                    RETURN QUERY
                    SELECT 
                        k.id AS kullanici_id,
                        k.ad AS kullanici_adi,
                        k.soyad AS kullanici_soyadi,
                        k.kayit_tarihi,
                        COUNT(DISTINCT ts.id) AS toplam_siparis_sayisi,
                        COALESCE(SUM(ts.toplam_tutar), 0) AS toplam_harcama,
                        COUNT(DISTINCT CASE WHEN ts.odeme_durumu = 'Beklemede' THEN ts.id END) AS odeme_bekleyen_siparis_sayisi,
                        COUNT(DISTINCT gts.id) AS test_sonucu_sayisi,
                        COUNT(DISTINCT hrs.id) AS risk_skoru_sayisi,
                        COALESCE(AVG(hrs.risk_yuzdesi), 0) AS ortalama_risk_yuzdesi,
                        (SELECT ht.hastalik_adi 
                         FROM hastalik_risk_skoru hrs2
                         JOIN hastalik_tanimi ht ON hrs2.hastalik_id = ht.id
                         WHERE hrs2.sonuc_id IN (SELECT id FROM genetik_test_sonucu WHERE kullanici_id = k.id)
                         ORDER BY hrs2.risk_yuzdesi DESC
                         LIMIT 1) AS en_yuksek_risk_hastalik,
                        COUNT(DISTINCT n.id) AS numune_sayisi,
                        COUNT(DISTINCT CASE WHEN kh.aktif_mi = true THEN kh.id END) AS aktif_hesap_sayisi,
                        COUNT(DISTINCT a.id) AS adres_sayisi
                    FROM kullanici k
                    LEFT JOIN test_siparisi ts ON k.id = ts.kullanici_id
                    LEFT JOIN genetik_test_sonucu gts ON k.id = gts.kullanici_id
                    LEFT JOIN hastalik_risk_skoru hrs ON gts.id = hrs.sonuc_id
                    LEFT JOIN numune n ON ts.id = n.siparis_id
                    LEFT JOIN kullanici_hesap kh ON k.id = kh.kullanici_id
                    LEFT JOIN adres a ON k.id = a.kullanici_id
                    WHERE k.id = p_kullanici_id
                    GROUP BY k.id, k.ad, k.soyad, k.kayit_tarihi;
                END;
                $$ LANGUAGE plpgsql;
                """,
                """
                CREATE OR REPLACE FUNCTION fn_test_sonuclari_analiz_raporu(p_baslangic_tarihi DATE DEFAULT NULL, p_bitis_tarihi DATE DEFAULT NULL)
                RETURNS TABLE(
                    toplam_test_sayisi BIGINT,
                    ortalama_test_suresi_gun INTEGER,
                    en_cok_test_yapan_kullanici VARCHAR,
                    test_sonucu_dagilimi JSONB,
                    risk_seviyesi_dagilimi JSONB,
                    en_sik_gorulen_hastalik VARCHAR,
                    toplam_varyant_sayisi BIGINT,
                    aktif_numune_sayisi BIGINT,
                    tamamlanan_analiz_sayisi BIGINT
                ) AS $$
                BEGIN
                    RETURN QUERY
                    SELECT 
                        COUNT(DISTINCT gts.id) AS toplam_test_sayisi,
                        COALESCE(AVG(EXTRACT(DAY FROM (gts.yayim_tarihi - la.analiz_baslangic))), 0)::INTEGER AS ortalama_test_suresi_gun,
                        (SELECT k.ad || ' ' || k.soyad
                         FROM genetik_test_sonucu gts2
                         JOIN kullanici k ON gts2.kullanici_id = k.id
                         WHERE (p_baslangic_tarihi IS NULL OR DATE(gts2.yayim_tarihi) >= p_baslangic_tarihi)
                           AND (p_bitis_tarihi IS NULL OR DATE(gts2.yayim_tarihi) <= p_bitis_tarihi)
                         GROUP BY k.id, k.ad, k.soyad
                         ORDER BY COUNT(gts2.id) DESC
                         LIMIT 1) AS en_cok_test_yapan_kullanici,
                        COALESCE((SELECT jsonb_object_agg(
                            COALESCE(gts3.veri_surumu, 'Bilinmiyor'),
                            to_jsonb(COUNT(*))
                         )
                         FROM genetik_test_sonucu gts3
                         WHERE (p_baslangic_tarihi IS NULL OR DATE(gts3.yayim_tarihi) >= p_baslangic_tarihi)
                           AND (p_bitis_tarihi IS NULL OR DATE(gts3.yayim_tarihi) <= p_bitis_tarihi)
                         GROUP BY gts3.veri_surumu), '{}'::jsonb) AS test_sonucu_dagilimi,
                        COALESCE((SELECT jsonb_object_agg(
                            COALESCE(hrs2.risk_seviyesi, 'Belirlenmemiş'),
                            to_jsonb(COUNT(*))
                         )
                         FROM hastalik_risk_skoru hrs2
                         JOIN genetik_test_sonucu gts4 ON hrs2.sonuc_id = gts4.id
                         WHERE (p_baslangic_tarihi IS NULL OR DATE(gts4.yayim_tarihi) >= p_baslangic_tarihi)
                           AND (p_bitis_tarihi IS NULL OR DATE(gts4.yayim_tarihi) <= p_bitis_tarihi)
                         GROUP BY hrs2.risk_seviyesi), '{}'::jsonb) AS risk_seviyesi_dagilimi,
                        (SELECT ht.hastalik_adi
                         FROM hastalik_risk_skoru hrs3
                         JOIN hastalik_tanimi ht ON hrs3.hastalik_id = ht.id
                         JOIN genetik_test_sonucu gts5 ON hrs3.sonuc_id = gts5.id
                         WHERE (p_baslangic_tarihi IS NULL OR DATE(gts5.yayim_tarihi) >= p_baslangic_tarihi)
                           AND (p_bitis_tarihi IS NULL OR DATE(gts5.yayim_tarihi) <= p_bitis_tarihi)
                         GROUP BY ht.hastalik_adi
                         ORDER BY COUNT(*) DESC
                         LIMIT 1) AS en_sik_gorulen_hastalik,
                        COUNT(DISTINCT kvs.varyant_id) AS toplam_varyant_sayisi,
                        COUNT(DISTINCT CASE WHEN n.durum IN ('Beklemede', 'Hazırlanıyor') THEN n.id END) AS aktif_numune_sayisi,
                        COUNT(DISTINCT CASE WHEN la.analiz_bitis IS NOT NULL THEN la.id END) AS tamamlanan_analiz_sayisi
                    FROM genetik_test_sonucu gts
                    LEFT JOIN laboratuvar_analizi la ON gts.analiz_id = la.id
                    LEFT JOIN kullanici_varyant_sonucu kvs ON gts.id = kvs.sonuc_id
                    LEFT JOIN numune n ON la.numune_id = n.id
                    LEFT JOIN hastalik_risk_skoru hrs ON gts.id = hrs.sonuc_id
                    WHERE (p_baslangic_tarihi IS NULL OR DATE(gts.yayim_tarihi) >= p_baslangic_tarihi)
                      AND (p_bitis_tarihi IS NULL OR DATE(gts.yayim_tarihi) <= p_bitis_tarihi);
                END;
                $$ LANGUAGE plpgsql;
                """,
                """
                CREATE OR REPLACE FUNCTION fn_hastalik_risk_analiz_raporu(p_hastalik_id INTEGER DEFAULT NULL)
                RETURNS TABLE(
                    hastalik_id INTEGER,
                    hastalik_adi VARCHAR,
                    icd_kodu VARCHAR,
                    toplam_test_sayisi BIGINT,
                    ortalama_risk_yuzdesi DOUBLE PRECISION,
                    en_yuksek_risk_yuzdesi DOUBLE PRECISION,
                    en_dusuk_risk_yuzdesi DOUBLE PRECISION,
                    yuksek_riskli_kullanici_sayisi BIGINT,
                    orta_riskli_kullanici_sayisi BIGINT,
                    dusuk_riskli_kullanici_sayisi BIGINT,
                    riskli_kullanicilar JSONB
                ) AS $$
                BEGIN
                    RETURN QUERY
                    SELECT 
                        ht.id AS hastalik_id,
                        ht.hastalik_adi,
                        ht.icd_kodu,
                        COUNT(DISTINCT hrs.sonuc_id) AS toplam_test_sayisi,
                        COALESCE(AVG(hrs.risk_yuzdesi), 0) AS ortalama_risk_yuzdesi,
                        COALESCE(MAX(hrs.risk_yuzdesi), 0) AS en_yuksek_risk_yuzdesi,
                        COALESCE(MIN(hrs.risk_yuzdesi), 0) AS en_dusuk_risk_yuzdesi,
                        COUNT(DISTINCT CASE WHEN hrs.risk_seviyesi = 'Yüksek' THEN gts.kullanici_id END) AS yuksek_riskli_kullanici_sayisi,
                        COUNT(DISTINCT CASE WHEN hrs.risk_seviyesi = 'Orta' THEN gts.kullanici_id END) AS orta_riskli_kullanici_sayisi,
                        COUNT(DISTINCT CASE WHEN hrs.risk_seviyesi = 'Düşük' THEN gts.kullanici_id END) AS dusuk_riskli_kullanici_sayisi,
                        (SELECT jsonb_agg(
                            jsonb_build_object(
                                'kullanici_id', k.id,
                                'kullanici_adi', k.ad || ' ' || k.soyad,
                                'risk_yuzdesi', hrs2.risk_yuzdesi,
                                'risk_seviyesi', hrs2.risk_seviyesi,
                                'test_tarihi', gts2.yayim_tarihi
                            )
                         )
                         FROM hastalik_risk_skoru hrs2
                         JOIN genetik_test_sonucu gts2 ON hrs2.sonuc_id = gts2.id
                         JOIN kullanici k ON gts2.kullanici_id = k.id
                         WHERE hrs2.hastalik_id = ht.id
                           AND hrs2.risk_seviyesi IN ('Yüksek', 'Orta')
                         ORDER BY hrs2.risk_yuzdesi DESC
                         LIMIT 10
                        ) AS riskli_kullanicilar
                    FROM hastalik_tanimi ht
                    LEFT JOIN hastalik_risk_skoru hrs ON ht.id = hrs.hastalik_id
                    LEFT JOIN genetik_test_sonucu gts ON hrs.sonuc_id = gts.id
                    WHERE (p_hastalik_id IS NULL OR ht.id = p_hastalik_id)
                    GROUP BY ht.id, ht.hastalik_adi, ht.icd_kodu
                    ORDER BY toplam_test_sayisi DESC;
                END;
                $$ LANGUAGE plpgsql;
                """
        );

        for (String functionDefinition : functionDefinitions) {
            try {
                jdbcTemplate.execute(functionDefinition);
                System.out.println("Raporlama Fonksiyonu başarıyla oluşturuldu: " + extractFunctionName(functionDefinition));
            } catch (Exception e) {
                System.err.println("Raporlama Fonksiyonu oluşturulurken hata: " + extractFunctionName(functionDefinition));
                System.err.println("Hata: " + e.getMessage());
            }
        }
    }

    private String extractFunctionName(String functionDefinition) {
        int start = functionDefinition.indexOf("FUNCTION fn_") + "FUNCTION ".length();
        int end = functionDefinition.indexOf("(", start);
        if (end == -1) end = functionDefinition.length();
        return functionDefinition.substring(start, end).trim();
    }

    private String extractIndexName(String indexDefinition) {
        int start = indexDefinition.indexOf("idx_");
        int end = indexDefinition.indexOf(" ON", start);
        if (end == -1) end = indexDefinition.length();
        return indexDefinition.substring(start, end).trim();
    }

    private void insertSampleData() {
        // Önce mevcut kayıtları kontrol et
        Integer kullaniciCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM kullanici", Integer.class);
        if (kullaniciCount != null && kullaniciCount > 0) {
            System.out.println("Örnek veriler zaten mevcut. Yeni veri eklenmedi.");
            return;
        }

        System.out.println("Örnek veriler ekleniyor...");

        // 1. KULLANICI (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO kullanici (ad, soyad, dogum_tarihi, cinsiyet) VALUES
            ('Ahmet', 'Yılmaz', '1985-03-15', 'Erkek'),
            ('Ayşe', 'Kaya', '1990-07-22', 'Kadın'),
            ('Mehmet', 'Demir', '1988-11-05', 'Erkek'),
            ('Fatma', 'Şahin', '1992-01-18', 'Kadın'),
            ('Ali', 'Çelik', '1987-09-30', 'Erkek'),
            ('Zeynep', 'Arslan', '1991-04-12', 'Kadın'),
            ('Mustafa', 'Öztürk', '1986-06-25', 'Erkek'),
            ('Elif', 'Yıldız', '1993-08-14', 'Kadın'),
            ('Hasan', 'Kurt', '1989-12-03', 'Erkek'),
            ('Selin', 'Aydın', '1994-02-28', 'Kadın'),
            ('Burak', 'Doğan', '1985-05-20', 'Erkek'),
            ('Ceren', 'Koç', '1990-10-08', 'Kadın'),
            ('Emre', 'Şimşek', '1987-07-16', 'Erkek'),
            ('Derya', 'Bulut', '1992-03-09', 'Kadın'),
            ('Onur', 'Yıldırım', '1988-11-22', 'Erkek'),
            ('Gizem', 'Ateş', '1991-09-04', 'Kadın'),
            ('Serkan', 'Toprak', '1986-04-17', 'Erkek'),
            ('Burcu', 'Taş', '1993-12-26', 'Kadın'),
            ('Can', 'Su', '1989-08-11', 'Erkek'),
            ('Deniz', 'Deniz', '1994-06-19', 'Kadın')
            """);

        // 2. TEST_PAKETI (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO test_paketi (paket_adi, fiyat, icerik_aciklamasi) VALUES
            ('Temel Genetik Test Paketi', 1500.00, 'Temel genetik analiz ve raporlama'),
            ('Kapsamlı DNA Analizi', 2500.00, 'Detaylı genetik analiz ve yorumlama'),
            ('Hastalık Risk Analizi', 3000.00, 'Hastalık risk skorları ve öneriler'),
            ('Farmakogenetik Test', 2000.00, 'İlaç yanıt analizi ve öneriler'),
            ('Etnik Köken Analizi', 1800.00, 'Etnik köken ve soy ağacı analizi'),
            ('Kanser Risk Analizi', 3500.00, 'Kanser risk skorları ve önleme önerileri'),
            ('Kalp Sağlığı Analizi', 2800.00, 'Kalp hastalıkları risk analizi'),
            ('Beslenme Genetiği', 2200.00, 'Beslenme önerileri ve genetik yatkınlık'),
            ('Spor Genetiği', 2400.00, 'Spor performansı ve yatkınlık analizi'),
            ('Cilt Sağlığı Analizi', 1900.00, 'Cilt sağlığı ve yaşlanma analizi'),
            ('Beyin Sağlığı Analizi', 3200.00, 'Nörolojik hastalıklar risk analizi'),
            ('Bağışıklık Sistemi', 2600.00, 'Bağışıklık sistemi ve genetik yatkınlık'),
            ('Metabolizma Analizi', 2100.00, 'Metabolik hastalıklar risk analizi'),
            ('Göz Sağlığı Analizi', 2300.00, 'Göz hastalıkları risk analizi'),
            ('Kemik Sağlığı Analizi', 2700.00, 'Kemik sağlığı ve osteoporoz riski'),
            ('Tiroid Analizi', 2000.00, 'Tiroid hastalıkları risk analizi'),
            ('Diyabet Risk Analizi', 2900.00, 'Diyabet risk skorları ve öneriler'),
            ('Obezite Analizi', 2500.00, 'Obezite riski ve beslenme önerileri'),
            ('Alzheimer Risk Analizi', 3400.00, 'Alzheimer risk skorları ve önleme'),
            ('Genel Sağlık Paketi', 4000.00, 'Kapsamlı genel sağlık analizi')
            """);

        // 3. GENETIK_VARYANT (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO genetik_varyant (rs_id, kromozom, konum, referans_alel) VALUES
            ('rs123456', '1', 123456, 'A'),
            ('rs234567', '2', 234567, 'G'),
            ('rs345678', '3', 345678, 'T'),
            ('rs456789', '4', 456789, 'C'),
            ('rs567890', '5', 567890, 'A'),
            ('rs678901', '6', 678901, 'G'),
            ('rs789012', '7', 789012, 'T'),
            ('rs890123', '8', 890123, 'C'),
            ('rs901234', '9', 901234, 'A'),
            ('rs012345', '10', 102345, 'G'),
            ('rs111111', '11', 111111, 'T'),
            ('rs222222', '12', 222222, 'C'),
            ('rs333333', '13', 333333, 'A'),
            ('rs444444', '14', 444444, 'G'),
            ('rs555555', '15', 555555, 'T'),
            ('rs666666', '16', 666666, 'C'),
            ('rs777777', '17', 777777, 'A'),
            ('rs888888', '18', 888888, 'G'),
            ('rs999999', '19', 999999, 'T'),
            ('rs000000', '20', 100000, 'C')
            """);

        // 4. HASTALIK_TANIMI (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO hastalik_tanimi (hastalik_adi, icd_kodu, bilimsel_tanim) VALUES
            ('Tip 2 Diyabet', 'E11', 'Tip 2 diyabet, insülin direnci ve göreceli insülin eksikliği ile karakterize metabolik bir bozukluktur'),
            ('Hipertansiyon', 'I10', 'Yüksek kan basıncı, kalp ve damar hastalıkları riskini artırır'),
            ('Koroner Arter Hastalığı', 'I25', 'Kalp kasına kan sağlayan arterlerin daralması veya tıkanması'),
            ('Osteoporoz', 'M81', 'Kemik yoğunluğunun azalması ve kırık riskinin artması'),
            ('Alzheimer Hastalığı', 'G30', 'İlerleyici nörodejeneratif bir hastalık'),
            ('Kolon Kanseri', 'C18', 'Kolon ve rektumda gelişen kanser türü'),
            ('Meme Kanseri', 'C50', 'Meme dokusunda gelişen kanser türü'),
            ('Prostat Kanseri', 'C61', 'Prostat bezinde gelişen kanser türü'),
            ('Akciğer Kanseri', 'C34', 'Akciğerlerde gelişen kanser türü'),
            ('Tip 1 Diyabet', 'E10', 'Otoimmün bir hastalık, pankreas beta hücrelerinin yıkımı'),
            ('Romatoid Artrit', 'M06', 'Kronik inflamatuar eklem hastalığı'),
            ('Multipl Skleroz', 'G35', 'Merkezi sinir sisteminin demiyelinizan hastalığı'),
            ('Parkinson Hastalığı', 'G20', 'Progresif nörolojik bozukluk'),
            ('Astım', 'J45', 'Kronik hava yolu inflamasyonu'),
            ('Obezite', 'E66', 'Vücut kitle indeksinin 30 üzerinde olması'),
            ('Depresyon', 'F32', 'Majör depresif bozukluk'),
            ('Anksiyete Bozukluğu', 'F41', 'Yaygın anksiyete bozukluğu'),
            ('Migren', 'G43', 'Tekrarlayan baş ağrıları'),
            ('Glokom', 'H40', 'Göz içi basıncının artması'),
            ('Tiroid Hastalığı', 'E07', 'Tiroid bezinin fonksiyon bozuklukları')
            """);

        // 5. KULLANICI_HESAP (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO kullanici_hesap (kullanici_id, eposta, parola_hash, aktif_mi) VALUES
            (1, 'ahmet.yilmaz@example.com', 'hash123', true),
            (2, 'ayse.kaya@example.com', 'hash456', true),
            (3, 'mehmet.demir@example.com', 'hash789', true),
            (4, 'fatma.sahin@example.com', 'hash012', true),
            (5, 'ali.celik@example.com', 'hash345', true),
            (6, 'zeynep.arslan@example.com', 'hash678', true),
            (7, 'mustafa.ozturk@example.com', 'hash901', true),
            (8, 'elif.yildiz@example.com', 'hash234', true),
            (9, 'hasan.kurt@example.com', 'hash567', true),
            (10, 'selin.aydin@example.com', 'hash890', true),
            (11, 'burak.dogan@example.com', 'hash111', true),
            (12, 'ceren.koc@example.com', 'hash222', true),
            (13, 'emre.simsek@example.com', 'hash333', true),
            (14, 'derya.bulut@example.com', 'hash444', true),
            (15, 'onur.yildirim@example.com', 'hash555', true),
            (16, 'gizem.ates@example.com', 'hash666', true),
            (17, 'serkan.toprak@example.com', 'hash777', true),
            (18, 'burcu.tas@example.com', 'hash888', true),
            (19, 'can.su@example.com', 'hash999', true),
            (20, 'deniz.deniz@example.com', 'hash000', true)
            """);

        // 6. ADRES (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO adres (kullanici_id, adres_tipi, ulke, sehir, posta_kodu, detayli_adres) VALUES
            (1, 'Fatura', 'Türkiye', 'İstanbul', '34000', 'Atatürk Cad. No:123 Daire:5 Kadıköy'),
            (2, 'Numune Gönderim', 'Türkiye', 'Ankara', '06000', 'Kızılay Mah. İnönü Bulvarı No:45'),
            (3, 'Fatura', 'Türkiye', 'İzmir', '35000', 'Konak Meydanı No:12 Daire:3'),
            (4, 'Numune Gönderim', 'Türkiye', 'Bursa', '16000', 'Osmangazi Mah. Fomara Cad. No:78'),
            (5, 'Fatura', 'Türkiye', 'Antalya', '07000', 'Konyaaltı Sahil Yolu No:234'),
            (6, 'Numune Gönderim', 'Türkiye', 'Adana', '01000', 'Seyhan Mah. Atatürk Cad. No:56'),
            (7, 'Fatura', 'Türkiye', 'Gaziantep', '27000', 'Şahinbey Mah. İstasyon Cad. No:89'),
            (8, 'Numune Gönderim', 'Türkiye', 'Konya', '42000', 'Meram Mah. Mevlana Cad. No:123'),
            (9, 'Fatura', 'Türkiye', 'Kayseri', '38000', 'Melikgazi Mah. Cumhuriyet Meydanı No:45'),
            (10, 'Numune Gönderim', 'Türkiye', 'Mersin', '33000', 'Yenişehir Mah. Atatürk Cad. No:67'),
            (11, 'Fatura', 'Türkiye', 'Diyarbakır', '21000', 'Sur Mah. Gazi Cad. No:234'),
            (12, 'Numune Gönderim', 'Türkiye', 'Eskişehir', '26000', 'Odunpazarı Mah. Porsuk Bulvarı No:12'),
            (13, 'Fatura', 'Türkiye', 'Samsun', '55000', 'İlkadım Mah. Atatürk Bulvarı No:345'),
            (14, 'Numune Gönderim', 'Türkiye', 'Malatya', '44000', 'Battalgazi Mah. İnönü Cad. No:78'),
            (15, 'Fatura', 'Türkiye', 'Erzurum', '25000', 'Yakutiye Mah. Cumhuriyet Cad. No:90'),
            (16, 'Numune Gönderim', 'Türkiye', 'Van', '65000', 'İpekyolu Mah. Atatürk Cad. No:123'),
            (17, 'Fatura', 'Türkiye', 'Trabzon', '61000', 'Ortahisar Mah. Uzun Sokak No:45'),
            (18, 'Numune Gönderim', 'Türkiye', 'Ordu', '52000', 'Altınordu Mah. Atatürk Bulvarı No:67'),
            (19, 'Fatura', 'Türkiye', 'Denizli', '20000', 'Pamukkale Mah. Atatürk Cad. No:234'),
            (20, 'Numune Gönderim', 'Türkiye', 'Muğla', '48000', 'Menteşe Mah. Atatürk Bulvarı No:56')
            """);

        // 7. AILE_UYELERI (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO aile_uyeleri (kullanici_id, ad_soyad, iliski_turu, dogum_yeri, vefat_tarihi) VALUES
            (1, 'Ali Yılmaz', 'Baba', 'İstanbul', NULL),
            (2, 'Fatma Kaya', 'Anne', 'Ankara', NULL),
            (3, 'Hasan Demir', 'Baba', 'İzmir', NULL),
            (4, 'Ayşe Şahin', 'Anne', 'Bursa', NULL),
            (5, 'Mehmet Çelik', 'Baba', 'Antalya', '2020-05-15'),
            (6, 'Zeynep Arslan', 'Anne', 'Adana', NULL),
            (7, 'Mustafa Öztürk', 'Baba', 'Gaziantep', NULL),
            (8, 'Elif Yıldız', 'Anne', 'Konya', NULL),
            (9, 'Burak Kurt', 'Baba', 'Kayseri', NULL),
            (10, 'Selin Aydın', 'Anne', 'Mersin', NULL),
            (11, 'Can Doğan', 'Kardeş', 'Diyarbakır', NULL),
            (12, 'Ceren Koç', 'Kardeş', 'Eskişehir', NULL),
            (13, 'Emre Şimşek', 'Amca', 'Samsun', NULL),
            (14, 'Derya Bulut', 'Teyze', 'Malatya', NULL),
            (15, 'Onur Yıldırım', 'Dayı', 'Erzurum', NULL),
            (16, 'Gizem Ateş', 'Hala', 'Van', NULL),
            (17, 'Serkan Toprak', 'Dede', 'Trabzon', '2018-03-20'),
            (18, 'Burcu Taş', 'Nine', 'Ordu', '2019-07-10'),
            (19, 'Deniz Su', 'Amca', 'Denizli', NULL),
            (20, 'Can Deniz', 'Teyze', 'Muğla', NULL)
            """);

        // 8. TEST_SIPARISI (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO test_siparisi (kullanici_id, paket_id, toplam_tutar, odeme_durumu) VALUES
            (1, 1, 1500.00, 'Ödendi'),
            (2, 2, 2500.00, 'Ödendi'),
            (3, 3, 3000.00, 'Beklemede'),
            (4, 4, 2000.00, 'Ödendi'),
            (5, 5, 1800.00, 'Beklemede'),
            (6, 6, 3500.00, 'Ödendi'),
            (7, 7, 2800.00, 'Ödendi'),
            (8, 8, 2200.00, 'Beklemede'),
            (9, 9, 2400.00, 'Ödendi'),
            (10, 10, 1900.00, 'Ödendi'),
            (11, 11, 3200.00, 'Beklemede'),
            (12, 12, 2600.00, 'Ödendi'),
            (13, 13, 2100.00, 'Ödendi'),
            (14, 14, 2300.00, 'Beklemede'),
            (15, 15, 2700.00, 'Ödendi'),
            (16, 16, 2000.00, 'Ödendi'),
            (17, 17, 2900.00, 'Beklemede'),
            (18, 18, 2500.00, 'Ödendi'),
            (19, 19, 3400.00, 'Ödendi'),
            (20, 20, 4000.00, 'Beklemede')
            """);

        // 9. NUMUNE (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO numune (siparis_id, barkod_id, numune_tipi, durum) VALUES
            (1, 'NUM-2024-001', 'Tükürük', 'Tamamlandı'),
            (2, 'NUM-2024-002', 'Kan', 'Hazırlanıyor'),
            (3, 'NUM-2024-003', 'Tükürük', 'Beklemede'),
            (4, 'NUM-2024-004', 'Kan', 'Tamamlandı'),
            (5, 'NUM-2024-005', 'Tükürük', 'Beklemede'),
            (6, 'NUM-2024-006', 'Kan', 'Tamamlandı'),
            (7, 'NUM-2024-007', 'Tükürük', 'Hazırlanıyor'),
            (8, 'NUM-2024-008', 'Kan', 'Beklemede'),
            (9, 'NUM-2024-009', 'Tükürük', 'Tamamlandı'),
            (10, 'NUM-2024-010', 'Kan', 'Hazırlanıyor'),
            (11, 'NUM-2024-011', 'Tükürük', 'Beklemede'),
            (12, 'NUM-2024-012', 'Kan', 'Tamamlandı'),
            (13, 'NUM-2024-013', 'Tükürük', 'Hazırlanıyor'),
            (14, 'NUM-2024-014', 'Kan', 'Beklemede'),
            (15, 'NUM-2024-015', 'Tükürük', 'Tamamlandı'),
            (16, 'NUM-2024-016', 'Kan', 'Hazırlanıyor'),
            (17, 'NUM-2024-017', 'Tükürük', 'Beklemede'),
            (18, 'NUM-2024-018', 'Kan', 'Tamamlandı'),
            (19, 'NUM-2024-019', 'Tükürük', 'Hazırlanıyor'),
            (20, 'NUM-2024-020', 'Kan', 'Beklemede')
            """);

        // 10. LABORATUVAR_ANALIZI (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO laboratuvar_analizi (numune_id, analiz_baslangic, analiz_bitis, teknisyen_adi, kalite_kontrol_sonucu) VALUES
            (1, '2024-01-10 09:00:00', '2024-01-15 17:00:00', 'Dr. Ayşe Yılmaz', 'Başarılı'),
            (2, '2024-01-11 10:00:00', '2024-01-16 18:00:00', 'Dr. Mehmet Kaya', 'Başarılı'),
            (3, '2024-01-12 11:00:00', NULL, 'Dr. Fatma Demir', 'Beklemede'),
            (4, '2024-01-13 09:30:00', '2024-01-18 16:30:00', 'Dr. Ali Şahin', 'Başarılı'),
            (5, '2024-01-14 10:30:00', NULL, 'Dr. Zeynep Çelik', 'Beklemede'),
            (6, '2024-01-15 08:00:00', '2024-01-20 15:00:00', 'Dr. Mustafa Arslan', 'Başarılı'),
            (7, '2024-01-16 09:00:00', NULL, 'Dr. Elif Öztürk', 'Beklemede'),
            (8, '2024-01-17 10:00:00', NULL, 'Dr. Hasan Yıldız', 'Beklemede'),
            (9, '2024-01-18 11:00:00', '2024-01-23 17:30:00', 'Dr. Selin Kurt', 'Başarılı'),
            (10, '2024-01-19 08:30:00', NULL, 'Dr. Burak Aydın', 'Beklemede'),
            (11, '2024-01-20 09:30:00', NULL, 'Dr. Ceren Doğan', 'Beklemede'),
            (12, '2024-01-21 10:30:00', '2024-01-26 16:00:00', 'Dr. Emre Koç', 'Başarılı'),
            (13, '2024-01-22 09:00:00', NULL, 'Dr. Derya Şimşek', 'Beklemede'),
            (14, '2024-01-23 10:00:00', NULL, 'Dr. Onur Bulut', 'Beklemede'),
            (15, '2024-01-24 11:00:00', '2024-01-29 18:00:00', 'Dr. Gizem Yıldırım', 'Başarılı'),
            (16, '2024-01-25 08:00:00', NULL, 'Dr. Serkan Ateş', 'Beklemede'),
            (17, '2024-01-26 09:00:00', NULL, 'Dr. Burcu Toprak', 'Beklemede'),
            (18, '2024-01-27 10:00:00', '2024-02-01 17:00:00', 'Dr. Can Taş', 'Başarılı'),
            (19, '2024-01-28 11:00:00', NULL, 'Dr. Deniz Su', 'Beklemede'),
            (20, '2024-01-29 08:30:00', NULL, 'Dr. Ahmet Deniz', 'Beklemede')
            """);

        // 11. GENETIK_TEST_SONUCU (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO genetik_test_sonucu (kullanici_id, analiz_id, yayim_tarihi, veri_surumu) VALUES
            (1, 1, '2024-01-16 10:00:00', 'v1.0'),
            (2, 2, '2024-01-17 11:00:00', 'v1.0'),
            (4, 4, '2024-01-19 12:00:00', 'v1.1'),
            (6, 6, '2024-01-21 13:00:00', 'v1.0'),
            (9, 9, '2024-01-24 14:00:00', 'v1.2'),
            (12, 12, '2024-01-27 15:00:00', 'v1.0'),
            (15, 15, '2024-01-30 16:00:00', 'v1.1'),
            (18, 18, '2024-02-02 17:00:00', 'v1.0'),
            (1, 1, '2024-02-05 10:00:00', 'v1.3'),
            (2, 2, '2024-02-06 11:00:00', 'v1.2'),
            (4, 4, '2024-02-07 12:00:00', 'v1.3'),
            (6, 6, '2024-02-08 13:00:00', 'v1.1'),
            (9, 9, '2024-02-09 14:00:00', 'v1.4'),
            (12, 12, '2024-02-10 15:00:00', 'v1.2'),
            (15, 15, '2024-02-11 16:00:00', 'v1.3'),
            (18, 18, '2024-02-12 17:00:00', 'v1.1'),
            (1, 1, '2024-02-13 10:00:00', 'v1.5'),
            (2, 2, '2024-02-14 11:00:00', 'v1.4'),
            (4, 4, '2024-02-15 12:00:00', 'v1.5'),
            (6, 6, '2024-02-16 13:00:00', 'v1.2')
            """);

        // 12. KULLANICI_GENETIK_VERISI (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO kullanici_genetik_verisi (sonuc_id, ham_veri_depolama_yolu, dosya_boyutu_mb) VALUES
            (1, '/data/genetik/ham_veri_001.vcf', 250),
            (2, '/data/genetik/ham_veri_002.vcf', 280),
            (3, '/data/genetik/ham_veri_003.vcf', 265),
            (4, '/data/genetik/ham_veri_004.vcf', 290),
            (5, '/data/genetik/ham_veri_005.vcf', 275),
            (6, '/data/genetik/ham_veri_006.vcf', 300),
            (7, '/data/genetik/ham_veri_007.vcf', 255),
            (8, '/data/genetik/ham_veri_008.vcf', 285),
            (9, '/data/genetik/ham_veri_009.vcf', 270),
            (10, '/data/genetik/ham_veri_010.vcf', 295),
            (11, '/data/genetik/ham_veri_011.vcf', 260),
            (12, '/data/genetik/ham_veri_012.vcf', 310),
            (13, '/data/genetik/ham_veri_013.vcf', 275),
            (14, '/data/genetik/ham_veri_014.vcf', 290),
            (15, '/data/genetik/ham_veri_015.vcf', 280),
            (16, '/data/genetik/ham_veri_016.vcf', 300),
            (17, '/data/genetik/ham_veri_017.vcf', 265),
            (18, '/data/genetik/ham_veri_018.vcf', 285),
            (19, '/data/genetik/ham_veri_019.vcf', 270),
            (20, '/data/genetik/ham_veri_020.vcf', 295)
            """);

        // 13. KULLANICI_VARYANT_SONUCU (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO kullanici_varyant_sonucu (sonuc_id, varyant_id, tespit_edilen_alel) VALUES
            (1, 1, 'A'),
            (1, 2, 'G'),
            (2, 3, 'T'),
            (2, 4, 'C'),
            (3, 5, 'A'),
            (3, 6, 'G'),
            (4, 7, 'T'),
            (4, 8, 'C'),
            (5, 9, 'A'),
            (5, 10, 'G'),
            (6, 11, 'T'),
            (6, 12, 'C'),
            (7, 13, 'A'),
            (7, 14, 'G'),
            (8, 15, 'T'),
            (8, 16, 'C'),
            (9, 17, 'A'),
            (9, 18, 'G'),
            (10, 19, 'T'),
            (10, 20, 'C')
            """);

        // 14. HASTALIK_RISK_SKORU (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO hastalik_risk_skoru (sonuc_id, hastalik_id, risk_yuzdesi, risk_seviyesi) VALUES
            (1, 1, 15.5, 'Orta'),
            (1, 2, 12.3, 'Düşük'),
            (2, 3, 25.8, 'Yüksek'),
            (2, 4, 18.7, 'Orta'),
            (3, 5, 8.2, 'Düşük'),
            (3, 6, 30.5, 'Yüksek'),
            (4, 7, 22.1, 'Yüksek'),
            (4, 8, 14.6, 'Orta'),
            (5, 9, 19.3, 'Orta'),
            (5, 10, 11.2, 'Düşük'),
            (6, 11, 27.4, 'Yüksek'),
            (6, 12, 16.8, 'Orta'),
            (7, 13, 9.5, 'Düşük'),
            (7, 14, 23.6, 'Yüksek'),
            (8, 15, 17.9, 'Orta'),
            (8, 16, 13.4, 'Düşük'),
            (9, 17, 28.2, 'Yüksek'),
            (9, 18, 20.1, 'Yüksek'),
            (10, 19, 10.7, 'Düşük'),
            (10, 20, 15.9, 'Orta')
            """);

        // 15. TEDAVIYE_YANIT (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO tedaviye_yanit (sonuc_id, ilac_adi, yanit_tahmini, oneriler) VALUES
            (1, 'Metformin', 'İyi', 'Düzenli egzersiz ve sağlıklı beslenme önerilir'),
            (2, 'Aspirin', 'Orta', 'Düşük doz aspirin kullanımı önerilir'),
            (3, 'Atorvastatin', 'İyi', 'Kolesterol seviyesini düzenli kontrol edin'),
            (4, 'Alendronat', 'İyi', 'Kalsiyum ve D vitamini takviyesi alın'),
            (5, 'Donepezil', 'Orta', 'Erken dönemde başlanması önerilir'),
            (6, '5-FU', 'İyi', 'Düzenli tarama yaptırın'),
            (7, 'Tamoxifen', 'İyi', 'Meme kanseri taraması yaptırın'),
            (8, 'Bicalutamide', 'Orta', 'Prostat kanseri taraması önerilir'),
            (9, 'Cisplatin', 'İyi', 'Sigara kullanımından kaçının'),
            (10, 'Insulin', 'İyi', 'Kan şekeri takibi yapın'),
            (11, 'Methotrexate', 'Orta', 'Düzenli kan testleri yaptırın'),
            (12, 'Interferon', 'İyi', 'Yan etkileri doktorunuzla görüşün'),
            (13, 'Levodopa', 'İyi', 'Erken dönemde başlanması önerilir'),
            (14, 'Salbutamol', 'İyi', 'Alerjenlerden kaçının'),
            (15, 'Orlistat', 'Orta', 'Diyet ve egzersiz programı uygulayın'),
            (16, 'Sertraline', 'İyi', 'Düzenli psikiyatrik takip önerilir'),
            (17, 'Buspirone', 'Orta', 'Stres yönetimi teknikleri öğrenin'),
            (18, 'Sumatriptan', 'İyi', 'Migren tetikleyicilerinden kaçının'),
            (19, 'Timolol', 'İyi', 'Göz basıncı takibi yapın'),
            (20, 'Levothyroxine', 'İyi', 'Tiroid hormon seviyelerini düzenli kontrol edin')
            """);

        // 16. ETNIK_KOKEN_RAPORU (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO etnik_koken_raporu (sonuc_id, bolge_adi, yuzde_orani, rapor_detayi) VALUES
            (1, 'Anadolu', 45.5, 'Anadolu kökenli genetik yapı'),
            (2, 'Balkan', 30.2, 'Balkan kökenli genetik yapı'),
            (3, 'Kafkas', 25.8, 'Kafkas kökenli genetik yapı'),
            (4, 'Orta Asya', 35.4, 'Orta Asya kökenli genetik yapı'),
            (5, 'Akdeniz', 40.1, 'Akdeniz kökenli genetik yapı'),
            (6, 'Avrupa', 28.7, 'Avrupa kökenli genetik yapı'),
            (7, 'Ortadoğu', 32.3, 'Ortadoğu kökenli genetik yapı'),
            (8, 'Asya', 22.9, 'Asya kökenli genetik yapı'),
            (9, 'Anadolu', 38.6, 'Anadolu kökenli genetik yapı'),
            (10, 'Balkan', 27.4, 'Balkan kökenli genetik yapı'),
            (11, 'Kafkas', 31.2, 'Kafkas kökenli genetik yapı'),
            (12, 'Orta Asya', 29.8, 'Orta Asya kökenli genetik yapı'),
            (13, 'Akdeniz', 42.5, 'Akdeniz kökenli genetik yapı'),
            (14, 'Avrupa', 26.1, 'Avrupa kökenli genetik yapı'),
            (15, 'Ortadoğu', 33.7, 'Ortadoğu kökenli genetik yapı'),
            (16, 'Asya', 24.3, 'Asya kökenli genetik yapı'),
            (17, 'Anadolu', 41.9, 'Anadolu kökenli genetik yapı'),
            (18, 'Balkan', 28.5, 'Balkan kökenli genetik yapı'),
            (19, 'Kafkas', 30.6, 'Kafkas kökenli genetik yapı'),
            (20, 'Orta Asya', 36.2, 'Orta Asya kökenli genetik yapı')
            """);

        // 17. GENETIK_DANISMANLIK (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO genetik_danismanlik (kullanici_id, danisman_adi, gorusme_tarihi, gorusme_ozeti) VALUES
            (1, 'Dr. Ayşe Yılmaz', '2024-01-20 10:00:00', 'Test sonuçları hakkında detaylı danışmanlık verildi'),
            (2, 'Dr. Mehmet Kaya', '2024-01-21 11:00:00', 'Risk faktörleri ve önleme yöntemleri tartışıldı'),
            (3, 'Dr. Fatma Demir', '2024-01-22 14:00:00', 'Genetik yatkınlıklar ve yaşam tarzı önerileri'),
            (4, 'Dr. Ali Şahin', '2024-01-23 15:00:00', 'Hastalık riskleri ve takip protokolleri'),
            (5, 'Dr. Zeynep Çelik', '2024-01-24 09:00:00', 'Farmakogenetik ve ilaç yanıtları'),
            (6, 'Dr. Mustafa Arslan', '2024-01-25 10:30:00', 'Kanser risk analizi ve önleme stratejileri'),
            (7, 'Dr. Elif Öztürk', '2024-01-26 13:00:00', 'Kalp sağlığı ve genetik faktörler'),
            (8, 'Dr. Hasan Yıldız', '2024-01-27 16:00:00', 'Beslenme genetiği ve diyet önerileri'),
            (9, 'Dr. Selin Kurt', '2024-01-28 11:00:00', 'Spor genetiği ve performans optimizasyonu'),
            (10, 'Dr. Burak Aydın', '2024-01-29 14:30:00', 'Cilt sağlığı ve yaşlanma genetiği'),
            (11, 'Dr. Ceren Doğan', '2024-01-30 10:00:00', 'Beyin sağlığı ve nörolojik riskler'),
            (12, 'Dr. Emre Koç', '2024-01-31 15:00:00', 'Bağışıklık sistemi ve genetik yatkınlıklar'),
            (13, 'Dr. Derya Şimşek', '2024-02-01 09:30:00', 'Metabolizma ve genetik faktörler'),
            (14, 'Dr. Onur Bulut', '2024-02-02 12:00:00', 'Göz sağlığı ve genetik riskler'),
            (15, 'Dr. Gizem Yıldırım', '2024-02-03 16:30:00', 'Kemik sağlığı ve osteoporoz riski'),
            (16, 'Dr. Serkan Ateş', '2024-02-04 11:30:00', 'Tiroid hastalıkları ve genetik'),
            (17, 'Dr. Burcu Toprak', '2024-02-05 14:00:00', 'Diyabet riski ve yaşam tarzı'),
            (18, 'Dr. Can Taş', '2024-02-06 10:00:00', 'Obezite genetiği ve beslenme'),
            (19, 'Dr. Deniz Su', '2024-02-07 13:30:00', 'Alzheimer riski ve önleme'),
            (20, 'Dr. Ahmet Deniz', '2024-02-08 15:30:00', 'Genel sağlık ve genetik yatkınlıklar')
            """);

        // 18. SOYAGACI_BAGLANTISI (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO soyagaci_baglantisi (kullanici_bir_id, kullanici_iki_id, tahmini_iliski, paylasilan_dna) VALUES
            (1, 2, 'Kardeş', 50.0),
            (3, 4, 'Kuzen', 12.5),
            (5, 6, 'Kardeş', 50.0),
            (7, 8, 'Amca/Yeğen', 25.0),
            (9, 10, 'Kuzen', 12.5),
            (11, 12, 'Kardeş', 50.0),
            (13, 14, 'Teyze/Yeğen', 25.0),
            (15, 16, 'Kuzen', 12.5),
            (17, 18, 'Kardeş', 50.0),
            (19, 20, 'Amca/Yeğen', 25.0),
            (1, 3, 'Kuzen', 12.5),
            (2, 4, 'Kuzen', 12.5),
            (5, 7, 'Amca/Yeğen', 25.0),
            (6, 8, 'Teyze/Yeğen', 25.0),
            (9, 11, 'Kuzen', 12.5),
            (10, 12, 'Kuzen', 12.5),
            (13, 15, 'Kardeş', 50.0),
            (14, 16, 'Kardeş', 50.0),
            (17, 19, 'Kuzen', 12.5),
            (18, 20, 'Kuzen', 12.5)
            """);

        // 19. VERI_ERISIM_IZNI (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO veri_erisim_izni (kullanici_id, izin_tipi, izin_verildi, izin_tarihi) VALUES
            (1, 'Okuma', true, '2024-01-15 10:00:00'),
            (2, 'Yazma', true, '2024-01-16 11:00:00'),
            (3, 'Okuma', true, '2024-01-17 12:00:00'),
            (4, 'Silme', false, '2024-01-18 13:00:00'),
            (5, 'Okuma', true, '2024-01-19 14:00:00'),
            (6, 'Yazma', true, '2024-01-20 15:00:00'),
            (7, 'Okuma', true, '2024-01-21 16:00:00'),
            (8, 'Silme', false, '2024-01-22 17:00:00'),
            (9, 'Okuma', true, '2024-01-23 10:00:00'),
            (10, 'Yazma', true, '2024-01-24 11:00:00'),
            (11, 'Okuma', true, '2024-01-25 12:00:00'),
            (12, 'Silme', false, '2024-01-26 13:00:00'),
            (13, 'Okuma', true, '2024-01-27 14:00:00'),
            (14, 'Yazma', true, '2024-01-28 15:00:00'),
            (15, 'Okuma', true, '2024-01-29 16:00:00'),
            (16, 'Silme', false, '2024-01-30 17:00:00'),
            (17, 'Okuma', true, '2024-01-31 10:00:00'),
            (18, 'Yazma', true, '2024-02-01 11:00:00'),
            (19, 'Okuma', true, '2024-02-02 12:00:00'),
            (20, 'Silme', false, '2024-02-03 13:00:00')
            """);

        // 20. DENETIM_KAYDI (20 kayıt)
        jdbcTemplate.execute("""
            INSERT INTO denetim_kaydi (kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama) VALUES
            (1, 'kullanici', 1, 'INSERT', 'Yeni kullanıcı eklendi'),
            (2, 'test_siparisi', 1, 'INSERT', 'Yeni sipariş oluşturuldu'),
            (3, 'numune', 1, 'INSERT', 'Numune kaydı eklendi'),
            (4, 'genetik_test_sonucu', 1, 'INSERT', 'Test sonucu yayınlandı'),
            (5, 'hastalik_risk_skoru', 1, 'INSERT', 'Risk skoru hesaplandı'),
            (6, 'kullanici', 2, 'UPDATE', 'Kullanıcı bilgileri güncellendi'),
            (7, 'test_siparisi', 2, 'UPDATE', 'Sipariş durumu güncellendi'),
            (8, 'numune', 2, 'UPDATE', 'Numune durumu güncellendi'),
            (9, 'genetik_test_sonucu', 2, 'UPDATE', 'Test sonucu güncellendi'),
            (10, 'hastalik_risk_skoru', 2, 'UPDATE', 'Risk skoru güncellendi'),
            (11, 'kullanici', 3, 'DELETE', 'Kullanıcı silindi'),
            (12, 'test_siparisi', 3, 'DELETE', 'Sipariş iptal edildi'),
            (13, 'numune', 3, 'DELETE', 'Numune kaydı silindi'),
            (14, 'genetik_test_sonucu', 3, 'DELETE', 'Test sonucu silindi'),
            (15, 'hastalik_risk_skoru', 3, 'DELETE', 'Risk skoru silindi'),
            (16, 'kullanici', 4, 'INSERT', 'Yeni kullanıcı eklendi'),
            (17, 'test_siparisi', 4, 'INSERT', 'Yeni sipariş oluşturuldu'),
            (18, 'numune', 4, 'INSERT', 'Numune kaydı eklendi'),
            (19, 'genetik_test_sonucu', 4, 'INSERT', 'Test sonucu yayınlandı'),
            (20, 'hastalik_risk_skoru', 4, 'INSERT', 'Risk skoru hesaplandı')
            """);

        System.out.println("Örnek veriler başarıyla eklendi! (Her tabloya 20 kayıt)");
    }
}

