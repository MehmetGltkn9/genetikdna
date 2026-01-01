package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.AileUyeleri;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class AileUyeleriDAO {

    private final JdbcTemplate jdbcTemplate;

    public AileUyeleriDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addAileUyesi(AileUyeleri aileUyesi) {
        String sql = "INSERT INTO aile_uyeleri (kullanici_id, ad_soyad, iliski_turu, dogum_yeri, vefat_tarihi) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                aileUyesi.getKullaniciId(),
                aileUyesi.getAdSoyad(),
                aileUyesi.getIliskiTuru(),
                aileUyesi.getDogumYeri(),
                aileUyesi.getVefatTarihi());
    }

    public List<AileUyeleri> getAllAileUyeleri() {
        String sql = "SELECT * FROM aile_uyeleri";
        return jdbcTemplate.query(sql, new AileUyeleriRowMapper());
    }

    public List<Map<String, Object>> getAileUyeleriByKullaniciId(Integer kullaniciId) {
        String sql = """
            SELECT 
                au.id, 
                au.ad_soyad, 
                au.iliski_turu, 
                au.dogum_yeri, 
                au.vefat_tarihi,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM aile_uyeleri au
            JOIN kullanici k ON au.kullanici_id = k.id
            WHERE au.kullanici_id = ?
        """;
        return jdbcTemplate.queryForList(sql, kullaniciId);
    }

    public AileUyeleri getAileUyesiById(Integer id) {
        String sql = "SELECT * FROM aile_uyeleri WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new AileUyeleriRowMapper(), id);
    }

    public void deleteAileUyesi(Integer id) {
        String sql = "DELETE FROM aile_uyeleri WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class AileUyeleriRowMapper implements RowMapper<AileUyeleri> {
        @Override
        public AileUyeleri mapRow(ResultSet rs, int rowNum) throws SQLException {
            AileUyeleri aileUyesi = new AileUyeleri();
            aileUyesi.setId(rs.getInt("id"));
            aileUyesi.setKullaniciId(rs.getInt("kullanici_id"));
            aileUyesi.setAdSoyad(rs.getString("ad_soyad"));
            aileUyesi.setIliskiTuru(rs.getString("iliski_turu"));
            aileUyesi.setDogumYeri(rs.getString("dogum_yeri"));
            aileUyesi.setVefatTarihi(rs.getDate("vefat_tarihi"));
            return aileUyesi;
        }
    }
}

