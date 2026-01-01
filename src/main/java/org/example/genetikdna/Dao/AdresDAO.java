package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.Adres;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class AdresDAO {

    private final JdbcTemplate jdbcTemplate;

    public AdresDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addAdres(Adres adres) {
        String sql = "INSERT INTO adres (kullanici_id, adres_tipi, ulke, sehir, posta_kodu, detayli_adres) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                adres.getKullaniciId(),
                adres.getAdresTipi(),
                adres.getUlke(),
                adres.getSehir(),
                adres.getPostaKodu(),
                adres.getDetayliAdres());
    }

    public List<Adres> getAllAdresler() {
        String sql = "SELECT * FROM adres";
        return jdbcTemplate.query(sql, new AdresRowMapper());
    }

    public List<Map<String, Object>> getAdreslerByKullaniciId(Integer kullaniciId) {
        String sql = """
            SELECT 
                a.id, 
                a.adres_tipi, 
                a.ulke, 
                a.sehir, 
                a.posta_kodu, 
                a.detayli_adres,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM adres a
            JOIN kullanici k ON a.kullanici_id = k.id
            WHERE a.kullanici_id = ?
        """;
        return jdbcTemplate.queryForList(sql, kullaniciId);
    }

    public Adres getAdresById(Integer id) {
        String sql = "SELECT * FROM adres WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new AdresRowMapper(), id);
    }

    public void deleteAdres(Integer id) {
        String sql = "DELETE FROM adres WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class AdresRowMapper implements RowMapper<Adres> {
        @Override
        public Adres mapRow(ResultSet rs, int rowNum) throws SQLException {
            Adres adres = new Adres();
            adres.setId(rs.getInt("id"));
            adres.setKullaniciId(rs.getInt("kullanici_id"));
            adres.setAdresTipi(rs.getString("adres_tipi"));
            adres.setUlke(rs.getString("ulke"));
            adres.setSehir(rs.getString("sehir"));
            adres.setPostaKodu(rs.getString("posta_kodu"));
            adres.setDetayliAdres(rs.getString("detayli_adres"));
            return adres;
        }
    }
}

