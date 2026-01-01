package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.KullaniciHesap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class KullaniciHesapDAO {

    private final JdbcTemplate jdbcTemplate;

    public KullaniciHesapDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addKullaniciHesap(KullaniciHesap hesap) {
        String sql = "INSERT INTO kullanici_hesap (kullanici_id, eposta, parola_hash, son_giris, aktif_mi) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                hesap.getKullaniciId(),
                hesap.getEposta(),
                hesap.getParolaHash(),
                hesap.getSonGiris(),
                hesap.isAktifMi());
    }

    public List<KullaniciHesap> getAllKullaniciHesaplari() {
        String sql = "SELECT * FROM kullanici_hesap";
        return jdbcTemplate.query(sql, new KullaniciHesapRowMapper());
    }

    public List<Map<String, Object>> getKullaniciHesapByKullaniciId(Integer kullaniciId) {
        String sql = """
            SELECT 
                kh.id, 
                kh.eposta, 
                kh.son_giris, 
                kh.aktif_mi,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM kullanici_hesap kh
            JOIN kullanici k ON kh.kullanici_id = k.id
            WHERE kh.kullanici_id = ?
        """;
        return jdbcTemplate.queryForList(sql, kullaniciId);
    }

    public KullaniciHesap getKullaniciHesapByEposta(String eposta) {
        String sql = "SELECT * FROM kullanici_hesap WHERE eposta = ?";
        return jdbcTemplate.queryForObject(sql, new KullaniciHesapRowMapper(), eposta);
    }

    public KullaniciHesap getKullaniciHesapById(Integer id) {
        String sql = "SELECT * FROM kullanici_hesap WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new KullaniciHesapRowMapper(), id);
    }

    public void deleteKullaniciHesap(Integer id) {
        String sql = "DELETE FROM kullanici_hesap WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class KullaniciHesapRowMapper implements RowMapper<KullaniciHesap> {
        @Override
        public KullaniciHesap mapRow(ResultSet rs, int rowNum) throws SQLException {
            KullaniciHesap hesap = new KullaniciHesap();
            hesap.setId(rs.getInt("id"));
            hesap.setKullaniciId(rs.getInt("kullanici_id"));
            hesap.setEposta(rs.getString("eposta"));
            hesap.setParolaHash(rs.getString("parola_hash"));
            hesap.setSonGiris(rs.getTimestamp("son_giris"));
            hesap.setAktifMi(rs.getBoolean("aktif_mi"));
            return hesap;
        }
    }
}

