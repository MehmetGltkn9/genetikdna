package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.KullaniciGenetikVerisi;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class KullaniciGenetikVerisiDAO {

    private final JdbcTemplate jdbcTemplate;

    public KullaniciGenetikVerisiDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addKullaniciGenetikVerisi(KullaniciGenetikVerisi veri) {
        String sql = "INSERT INTO kullanici_genetik_verisi (sonuc_id, ham_veri_depolama_yolu, dosya_boyutu_mb) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                veri.getSonucId(),
                veri.getHamVeriDepolamaYolu(),
                veri.getDosyaBoyutuMb());
    }

    public List<KullaniciGenetikVerisi> getAllKullaniciGenetikVerileri() {
        String sql = "SELECT * FROM kullanici_genetik_verisi";
        return jdbcTemplate.query(sql, new KullaniciGenetikVerisiRowMapper());
    }

    public List<Map<String, Object>> getKullaniciGenetikVerileriBySonucId(Integer sonucId) {
        String sql = """
            SELECT 
                kgv.id, 
                kgv.ham_veri_depolama_yolu, 
                kgv.dosya_boyutu_mb,
                gts.yayim_tarihi,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM kullanici_genetik_verisi kgv
            JOIN genetik_test_sonucu gts ON kgv.sonuc_id = gts.id
            JOIN kullanici k ON gts.kullanici_id = k.id
            WHERE kgv.sonuc_id = ?
        """;
        return jdbcTemplate.queryForList(sql, sonucId);
    }

    public KullaniciGenetikVerisi getKullaniciGenetikVerisiById(Integer id) {
        String sql = "SELECT * FROM kullanici_genetik_verisi WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new KullaniciGenetikVerisiRowMapper(), id);
    }

    public void deleteKullaniciGenetikVerisi(Integer id) {
        String sql = "DELETE FROM kullanici_genetik_verisi WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class KullaniciGenetikVerisiRowMapper implements RowMapper<KullaniciGenetikVerisi> {
        @Override
        public KullaniciGenetikVerisi mapRow(ResultSet rs, int rowNum) throws SQLException {
            KullaniciGenetikVerisi veri = new KullaniciGenetikVerisi();
            veri.setId(rs.getInt("id"));
            veri.setSonucId(rs.getInt("sonuc_id"));
            veri.setHamVeriDepolamaYolu(rs.getString("ham_veri_depolama_yolu"));
            veri.setDosyaBoyutuMb(rs.getInt("dosya_boyutu_mb"));
            return veri;
        }
    }
}

