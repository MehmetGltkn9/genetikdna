package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.HastalikRiskSkoru;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class HastalikRiskSkoruDAO {

    private final JdbcTemplate jdbcTemplate;

    public HastalikRiskSkoruDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addHastalikRiskSkoru(HastalikRiskSkoru riskSkoru) {
        String sql = "INSERT INTO hastalik_risk_skoru (sonuc_id, hastalik_id, risk_yuzdesi, risk_seviyesi) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                riskSkoru.getSonucId(),
                riskSkoru.getHastalikId(),
                riskSkoru.getRiskYuzdesi(),
                riskSkoru.getRiskSeviyesi());
    }

    public List<HastalikRiskSkoru> getAllHastalikRiskSkorlari() {
        String sql = "SELECT * FROM hastalik_risk_skoru";
        return jdbcTemplate.query(sql, new HastalikRiskSkoruRowMapper());
    }

    public List<Map<String, Object>> getHastalikRiskSkorlariBySonucId(Integer sonucId) {
        String sql = """
            SELECT 
                hrs.id, 
                hrs.risk_yuzdesi, 
                hrs.risk_seviyesi,
                ht.hastalik_adi,
                ht.icd_kodu,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM hastalik_risk_skoru hrs
            JOIN hastalik_tanimi ht ON hrs.hastalik_id = ht.id
            JOIN genetik_test_sonucu gts ON hrs.sonuc_id = gts.id
            JOIN kullanici k ON gts.kullanici_id = k.id
            WHERE hrs.sonuc_id = ?
        """;
        return jdbcTemplate.queryForList(sql, sonucId);
    }

    public List<Map<String, Object>> getHastalikRiskSkorlariByHastalikId(Integer hastalikId) {
        String sql = """
            SELECT 
                hrs.id, 
                hrs.risk_yuzdesi, 
                hrs.risk_seviyesi,
                ht.hastalik_adi,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM hastalik_risk_skoru hrs
            JOIN hastalik_tanimi ht ON hrs.hastalik_id = ht.id
            JOIN genetik_test_sonucu gts ON hrs.sonuc_id = gts.id
            JOIN kullanici k ON gts.kullanici_id = k.id
            WHERE hrs.hastalik_id = ?
        """;
        return jdbcTemplate.queryForList(sql, hastalikId);
    }

    public HastalikRiskSkoru getHastalikRiskSkoruById(Integer id) {
        String sql = "SELECT * FROM hastalik_risk_skoru WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new HastalikRiskSkoruRowMapper(), id);
    }

    public void deleteHastalikRiskSkoru(Integer id) {
        String sql = "DELETE FROM hastalik_risk_skoru WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Stored Procedure: Hastalık Risk Skoru ve Tedaviye Yanıt Ekleme
    public Map<String, Object> hastalikRiskVeTedaviEkle(Integer sonucId, Integer hastalikId, Double riskYuzdesi, 
                                                          String riskSeviyesi, String ilacAdi, String yanitTahmini, String oneriler) {
        String sql = "SELECT * FROM sp_hastalik_risk_ve_tedavi_ekle(?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.queryForMap(sql, sonucId, hastalikId, riskYuzdesi, riskSeviyesi, ilacAdi, yanitTahmini, oneriler);
    }

    private static class HastalikRiskSkoruRowMapper implements RowMapper<HastalikRiskSkoru> {
        @Override
        public HastalikRiskSkoru mapRow(ResultSet rs, int rowNum) throws SQLException {
            HastalikRiskSkoru riskSkoru = new HastalikRiskSkoru();
            riskSkoru.setId(rs.getInt("id"));
            riskSkoru.setSonucId(rs.getInt("sonuc_id"));
            riskSkoru.setHastalikId(rs.getInt("hastalik_id"));
            riskSkoru.setRiskYuzdesi(rs.getDouble("risk_yuzdesi"));
            riskSkoru.setRiskSeviyesi(rs.getString("risk_seviyesi"));
            return riskSkoru;
        }
    }
}

