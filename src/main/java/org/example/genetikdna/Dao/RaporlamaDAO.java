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
     * Kullanıcı detaylı raporu - Fonksiyon ile
     * @param kullaniciId Kullanıcı ID
     * @return Kullanıcının detaylı istatistikleri
     */
    public Map<String, Object> getKullaniciDetayliRaporu(Integer kullaniciId) {
        String sql = "SELECT * FROM fn_kullanici_detayli_raporu(?)";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, kullaniciId);
        if (results.isEmpty()) {
            throw new RuntimeException("Kullanıcı ID " + kullaniciId + " için rapor bulunamadı. Kullanıcı mevcut olmayabilir.");
        }
        return results.get(0);
    }

    /**
     * Test sonuçları analiz raporu - Fonksiyon ile
     * @param baslangicTarihi Başlangıç tarihi (opsiyonel)
     * @param bitisTarihi Bitiş tarihi (opsiyonel)
     * @return Test sonuçları analiz raporu
     */
    public Map<String, Object> getTestSonuclariAnalizRaporu(Date baslangicTarihi, Date bitisTarihi) {
        String sql = "SELECT * FROM fn_test_sonuclari_analiz_raporu(?, ?)";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, baslangicTarihi, bitisTarihi);
        if (results.isEmpty()) {
            // Boş sonuç döndür, hata fırlatma
            return java.util.Collections.emptyMap();
        }
        return results.get(0);
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
        String sql = "SELECT * FROM fn_hastalik_risk_analiz_raporu(?)";
        return jdbcTemplate.queryForList(sql, hastalikId);
    }

    /**
     * Tüm hastalıklar için risk analiz raporu
     */
    public List<Map<String, Object>> getHastalikRiskAnalizRaporu() {
        String sql = "SELECT * FROM fn_hastalik_risk_analiz_raporu(NULL)";
        return jdbcTemplate.queryForList(sql);
    }
}

