package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.KullaniciVaryantSonucu;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class KullaniciVaryantSonucuDAO {

    private final JdbcTemplate jdbcTemplate;

    public KullaniciVaryantSonucuDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addKullaniciVaryantSonucu(KullaniciVaryantSonucu sonuc) {
        String sql = "INSERT INTO kullanici_varyant_sonucu (sonuc_id, varyant_id, tespit_edilen_alel) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                sonuc.getSonucId(),
                sonuc.getVaryantId(),
                sonuc.getTespitEdilenAlel());
    }

    public List<KullaniciVaryantSonucu> getAllKullaniciVaryantSonuclari() {
        String sql = "SELECT * FROM kullanici_varyant_sonucu";
        return jdbcTemplate.query(sql, new KullaniciVaryantSonucuRowMapper());
    }

    public List<Map<String, Object>> getKullaniciVaryantSonuclariBySonucId(Integer sonucId) {
        String sql = """
            SELECT 
                kvs.id, 
                kvs.tespit_edilen_alel,
                gv.rs_id,
                gv.kromozom,
                gv.konum,
                gv.referans_alel,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM kullanici_varyant_sonucu kvs
            JOIN genetik_varyant gv ON kvs.varyant_id = gv.id
            JOIN genetik_test_sonucu gts ON kvs.sonuc_id = gts.id
            JOIN kullanici k ON gts.kullanici_id = k.id
            WHERE kvs.sonuc_id = ?
        """;
        return jdbcTemplate.queryForList(sql, sonucId);
    }

    public List<Map<String, Object>> getKullaniciVaryantSonuclariByVaryantId(Integer varyantId) {
        String sql = """
            SELECT 
                kvs.id, 
                kvs.tespit_edilen_alel,
                gv.rs_id,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM kullanici_varyant_sonucu kvs
            JOIN genetik_varyant gv ON kvs.varyant_id = gv.id
            JOIN genetik_test_sonucu gts ON kvs.sonuc_id = gts.id
            JOIN kullanici k ON gts.kullanici_id = k.id
            WHERE kvs.varyant_id = ?
        """;
        return jdbcTemplate.queryForList(sql, varyantId);
    }

    public KullaniciVaryantSonucu getKullaniciVaryantSonucuById(Long id) {
        String sql = "SELECT * FROM kullanici_varyant_sonucu WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new KullaniciVaryantSonucuRowMapper(), id);
    }

    public void deleteKullaniciVaryantSonucu(Long id) {
        String sql = "DELETE FROM kullanici_varyant_sonucu WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Stored Procedure: Varyant Sonuçlarını Toplu Ekleme (JSON formatında)
    public Map<String, Object> varyantSonuclariTopluEkle(Integer sonucId, String varyantVerileriJson) {
        String sql = "SELECT * FROM sp_varyant_sonuclari_toplu_ekle(?, ?)";
        return jdbcTemplate.queryForMap(sql, sonucId, varyantVerileriJson);
    }

    private static class KullaniciVaryantSonucuRowMapper implements RowMapper<KullaniciVaryantSonucu> {
        @Override
        public KullaniciVaryantSonucu mapRow(ResultSet rs, int rowNum) throws SQLException {
            KullaniciVaryantSonucu sonuc = new KullaniciVaryantSonucu();
            sonuc.setId(rs.getLong("id"));
            sonuc.setSonucId(rs.getInt("sonuc_id"));
            sonuc.setVaryantId(rs.getInt("varyant_id"));
            sonuc.setTespitEdilenAlel(rs.getString("tespit_edilen_alel"));
            return sonuc;
        }
    }
}

