package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.GenetikDanismanlik;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class GenetikDanismanlikDAO {

    private final JdbcTemplate jdbcTemplate;

    public GenetikDanismanlikDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addGenetikDanismanlik(GenetikDanismanlik danismanlik) {
        String sql = "INSERT INTO genetik_danismanlik (kullanici_id, danisman_adi, gorusme_tarihi, gorusme_ozeti) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                danismanlik.getKullaniciId(),
                danismanlik.getDanismanAdi(),
                danismanlik.getGorusmeTarihi(),
                danismanlik.getGorusmeOzeti());
    }

    public List<GenetikDanismanlik> getAllGenetikDanismanliklar() {
        String sql = "SELECT * FROM genetik_danismanlik";
        return jdbcTemplate.query(sql, new GenetikDanismanlikRowMapper());
    }

    public List<Map<String, Object>> getGenetikDanismanliklarByKullaniciId(Integer kullaniciId) {
        String sql = """
            SELECT 
                gd.id, 
                gd.danisman_adi, 
                gd.gorusme_tarihi, 
                gd.gorusme_ozeti,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM genetik_danismanlik gd
            JOIN kullanici k ON gd.kullanici_id = k.id
            WHERE gd.kullanici_id = ?
        """;
        return jdbcTemplate.queryForList(sql, kullaniciId);
    }

    public GenetikDanismanlik getGenetikDanismanlikById(Integer id) {
        String sql = "SELECT * FROM genetik_danismanlik WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new GenetikDanismanlikRowMapper(), id);
    }

    public void deleteGenetikDanismanlik(Integer id) {
        String sql = "DELETE FROM genetik_danismanlik WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class GenetikDanismanlikRowMapper implements RowMapper<GenetikDanismanlik> {
        @Override
        public GenetikDanismanlik mapRow(ResultSet rs, int rowNum) throws SQLException {
            GenetikDanismanlik danismanlik = new GenetikDanismanlik();
            danismanlik.setId(rs.getInt("id"));
            danismanlik.setKullaniciId(rs.getInt("kullanici_id"));
            danismanlik.setDanismanAdi(rs.getString("danisman_adi"));
            danismanlik.setGorusmeTarihi(rs.getTimestamp("gorusme_tarihi"));
            danismanlik.setGorusmeOzeti(rs.getString("gorusme_ozeti"));
            return danismanlik;
        }
    }
}

