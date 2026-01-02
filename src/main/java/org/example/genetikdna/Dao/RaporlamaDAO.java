package org.example.genetikdna.Dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Repository
public class RaporlamaDAO {

    private final JdbcTemplate jdbcTemplate;

    public RaporlamaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Fonksiyonun veritabanında mevcut olup olmadığını kontrol eder
     */
    private boolean functionExists(String functionName) {
        try {
            // PostgreSQL'de fonksiyon adını kontrol et (schema ile birlikte)
            String sql = "SELECT COUNT(*) FROM pg_proc p " +
                        "JOIN pg_namespace n ON p.pronamespace = n.oid " +
                        "WHERE p.proname = ? AND n.nspname = 'public'";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, functionName);
            boolean exists = count != null && count > 0;
            
            // Debug için: Fonksiyonun parametre tiplerini de kontrol et
            if (exists) {
                String paramSql = "SELECT pg_get_function_arguments(p.oid) " +
                                 "FROM pg_proc p " +
                                 "JOIN pg_namespace n ON p.pronamespace = n.oid " +
                                 "WHERE p.proname = ? AND n.nspname = 'public' " +
                                 "LIMIT 1";
                try {
                    String params = jdbcTemplate.queryForObject(paramSql, String.class, functionName);
                    System.out.println("DEBUG: Fonksiyon " + functionName + " parametreleri: " + params);
                } catch (Exception e) {
                    // Parametre bilgisi alınamazsa devam et
                }
            }
            
            return exists;
        } catch (Exception e) {
            System.err.println("DEBUG: Fonksiyon kontrolü sırasında hata: " + e.getMessage());
            return false;
        }
    }

    /**
     * Kullanıcı detaylı raporu - Fonksiyon ile (fallback: doğrudan SQL)
     * @param kullaniciId Kullanıcı ID
     * @return Kullanıcının detaylı istatistikleri
     */
    public Map<String, Object> getKullaniciDetayliRaporu(Integer kullaniciId) {
        // Önce fonksiyon çağrısını dene
        if (functionExists("fn_kullanici_detayli_raporu")) {
            // PostgreSQL'de RETURNS TABLE olan fonksiyonlar için farklı syntax'ları dene
            String[] sqlVariants = {
                "SELECT * FROM fn_kullanici_detayli_raporu(?::INTEGER)",
                "SELECT * FROM public.fn_kullanici_detayli_raporu(?::INTEGER)",
                "SELECT * FROM fn_kullanici_detayli_raporu(?)",
                "SELECT * FROM fn_kullanici_detayli_raporu(CAST(? AS INTEGER))"
            };
            
            for (String sql : sqlVariants) {
                try {
                    List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, kullaniciId);
                    if (results != null && !results.isEmpty()) {
                        return results.get(0);
                    }
                } catch (org.springframework.jdbc.BadSqlGrammarException e) {
                    // Bu syntax çalışmadı, bir sonrakini dene
                    continue;
                } catch (Exception e) {
                    // Beklenmeyen hata, fallback'e geç
                    System.err.println("Fonksiyon çağrısı sırasında hata: " + e.getMessage());
                    break;
                }
            }
        }
        
        // Fonksiyon çağrısı başarısız oldu, doğrudan SQL sorgusu kullan (fallback)
        System.out.println("Fonksiyon çağrısı başarısız oldu, doğrudan SQL sorgusu kullanılıyor (fallback)...");
        return getKullaniciDetayliRaporuDirectSQL(kullaniciId);
    }
    
    /**
     * Kullanıcı detaylı raporu - Doğrudan SQL sorgusu (fallback)
     * Fonksiyon çağrısı başarısız olduğunda kullanılır
     */
    private Map<String, Object> getKullaniciDetayliRaporuDirectSQL(Integer kullaniciId) {
        String sql = """
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
            WHERE k.id = ?
            GROUP BY k.id, k.ad, k.soyad, k.kayit_tarihi
            """;
        
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, kullaniciId);
            if (results == null || results.isEmpty()) {
                throw new RuntimeException("Kullanıcı ID " + kullaniciId + " için rapor bulunamadı. Kullanıcı mevcut olmayabilir.");
            }
            return results.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Kullanıcı raporu alınırken hata: " + e.getMessage(), e);
        }
    }

    /**
     * Test sonuçları analiz raporu - Fonksiyon ile
     * @param baslangicTarihi Başlangıç tarihi (opsiyonel)
     * @param bitisTarihi Bitiş tarihi (opsiyonel)
     * @return Test sonuçları analiz raporu
     */
    public Map<String, Object> getTestSonuclariAnalizRaporu(Date baslangicTarihi, Date bitisTarihi) {
        // PostgreSQL'de fonksiyon çağrısı - Spring JDBC otomatik tip dönüşümü yapar
        String sql = "SELECT * FROM fn_test_sonuclari_analiz_raporu(?, ?)";
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, baslangicTarihi, bitisTarihi);
            if (results == null || results.isEmpty()) {
                // Boş sonuç döndür, hata fırlatma
                return java.util.Collections.emptyMap();
            }
            return results.get(0);
        } catch (org.springframework.jdbc.BadSqlGrammarException e) {
            throw new RuntimeException("Raporlama fonksiyonu çağrılırken hata oluştu: " + e.getMessage(), e);
        }
    }

    /**
     * Tüm test sonuçları analiz raporu (tarih filtresi olmadan)
     */
    public Map<String, Object> getTestSonuclariAnalizRaporu() {
        String sql = "SELECT * FROM fn_test_sonuclari_analiz_raporu(NULL, NULL)";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
        if (results.isEmpty()) {
            // Boş sonuç döndür, hata fırlatma
            return java.util.Collections.emptyMap();
        }
        return results.get(0);
    }

    /**
     * Hastalık risk analiz raporu - Fonksiyon ile
     * @param hastalikId Hastalık ID (opsiyonel, null ise tüm hastalıklar)
     * @return Hastalık risk analiz raporu
     */
    public List<Map<String, Object>> getHastalikRiskAnalizRaporu(Integer hastalikId) {
        // PostgreSQL'de fonksiyon çağrısı - Spring JDBC otomatik tip dönüşümü yapar
        String sql = "SELECT * FROM fn_hastalik_risk_analiz_raporu(?)";
        try {
            return jdbcTemplate.queryForList(sql, hastalikId);
        } catch (org.springframework.jdbc.BadSqlGrammarException e) {
            throw new RuntimeException("Raporlama fonksiyonu çağrılırken hata oluştu: " + e.getMessage(), e);
        }
    }

    /**
     * Tüm hastalıklar için risk analiz raporu
     */
    public List<Map<String, Object>> getHastalikRiskAnalizRaporu() {
        String sql = "SELECT * FROM fn_hastalik_risk_analiz_raporu(NULL)";
        return jdbcTemplate.queryForList(sql);
    }
}

