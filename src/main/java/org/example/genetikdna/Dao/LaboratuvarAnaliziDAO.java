package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.LaboratuvarAnalizi;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class LaboratuvarAnaliziDAO {

    private final JdbcTemplate jdbcTemplate;

    public LaboratuvarAnaliziDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLaboratuvarAnalizi(LaboratuvarAnalizi analiz) {
        String sql = "INSERT INTO laboratuvar_analizi (numune_id, analiz_baslangic, analiz_bitis, teknisyen_adi, cihaz_bilgisi, kalite_kontrol_sonucu) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                analiz.getNumuneId(),
                analiz.getAnalizBaslangic(),
                analiz.getAnalizBitis(),
                analiz.getTeknisyenAdi(),
                analiz.getCihazBilgisi(),
                analiz.getKaliteKontrolSonucu());
    }

    public List<LaboratuvarAnalizi> getAllLaboratuvarAnalizleri() {
        String sql = "SELECT * FROM laboratuvar_analizi";
        return jdbcTemplate.query(sql, new LaboratuvarAnaliziRowMapper());
    }

    public List<Map<String, Object>> getLaboratuvarAnalizleriByNumuneId(Integer numuneId) {
        String sql = """
            SELECT 
                la.id, 
                la.analiz_baslangic, 
                la.analiz_bitis, 
                la.teknisyen_adi, 
                la.cihaz_bilgisi, 
                la.kalite_kontrol_sonucu,
                n.barkod_id,
                n.numune_tipi,
                n.durum
            FROM laboratuvar_analizi la
            JOIN numune n ON la.numune_id = n.id
            WHERE la.numune_id = ?
        """;
        return jdbcTemplate.queryForList(sql, numuneId);
    }

    public List<Map<String, Object>> getLaboratuvarAnalizleriByTeknisyen(String teknisyenAdi) {
        String sql = """
            SELECT 
                la.id, 
                la.analiz_baslangic, 
                la.analiz_bitis, 
                la.teknisyen_adi, 
                la.cihaz_bilgisi, 
                la.kalite_kontrol_sonucu,
                n.barkod_id
            FROM laboratuvar_analizi la
            JOIN numune n ON la.numune_id = n.id
            WHERE la.teknisyen_adi = ?
        """;
        return jdbcTemplate.queryForList(sql, teknisyenAdi);
    }

    public LaboratuvarAnalizi getLaboratuvarAnaliziById(Integer id) {
        String sql = "SELECT * FROM laboratuvar_analizi WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new LaboratuvarAnaliziRowMapper(), id);
    }

    public void deleteLaboratuvarAnalizi(Integer id) {
        String sql = "DELETE FROM laboratuvar_analizi WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Stored Procedure: Laboratuvar Analizi Tamamlama ve Test Sonucu Oluşturma
    public Map<String, Object> laboratuvarAnaliziTamamla(Integer numuneId, java.sql.Timestamp analizBitis, 
                                                           String kaliteKontrolSonucu, Integer kullaniciId, String veriSurumu) {
        String sql = "SELECT * FROM sp_laboratuvar_analizi_tamamla(?, ?, ?, ?, ?)";
        return jdbcTemplate.queryForMap(sql, numuneId, analizBitis, kaliteKontrolSonucu, kullaniciId, veriSurumu);
    }

    private static class LaboratuvarAnaliziRowMapper implements RowMapper<LaboratuvarAnalizi> {
        @Override
        public LaboratuvarAnalizi mapRow(ResultSet rs, int rowNum) throws SQLException {
            LaboratuvarAnalizi analiz = new LaboratuvarAnalizi();
            analiz.setId(rs.getInt("id"));
            analiz.setNumuneId(rs.getInt("numune_id"));
            analiz.setAnalizBaslangic(rs.getTimestamp("analiz_baslangic"));
            analiz.setAnalizBitis(rs.getTimestamp("analiz_bitis"));
            analiz.setTeknisyenAdi(rs.getString("teknisyen_adi"));
            analiz.setCihazBilgisi(rs.getString("cihaz_bilgisi"));
            analiz.setKaliteKontrolSonucu(rs.getString("kalite_kontrol_sonucu"));
            return analiz;
        }
    }
}

