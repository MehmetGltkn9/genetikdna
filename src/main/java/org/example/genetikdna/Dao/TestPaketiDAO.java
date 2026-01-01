package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.TestPaketi;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class TestPaketiDAO {

    private final JdbcTemplate jdbcTemplate;

    public TestPaketiDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTestPaketi(TestPaketi paket) {
        String sql = "INSERT INTO test_paketi (paket_adi, fiyat, icerik_aciklamasi) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                paket.getPaketAdi(),
                paket.getFiyat(),
                paket.getIcerikAciklamasi());
    }

    public List<TestPaketi> getAllTestPaketleri() {
        String sql = "SELECT * FROM test_paketi";
        return jdbcTemplate.query(sql, new TestPaketiRowMapper());
    }

    public List<Map<String, Object>> getTestPaketiSiparisleri(Integer paketId) {
        String sql = """
            SELECT 
                tp.id, 
                tp.paket_adi, 
                tp.fiyat,
                COUNT(ts.id) AS siparis_sayisi,
                SUM(ts.toplam_tutar) AS toplam_gelir
            FROM test_paketi tp
            LEFT JOIN test_siparisi ts ON tp.id = ts.paket_id
            WHERE tp.id = ?
            GROUP BY tp.id, tp.paket_adi, tp.fiyat
        """;
        return jdbcTemplate.queryForList(sql, paketId);
    }

    public TestPaketi getTestPaketiById(Integer id) {
        String sql = "SELECT * FROM test_paketi WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new TestPaketiRowMapper(), id);
    }

    public void deleteTestPaketi(Integer id) {
        String sql = "DELETE FROM test_paketi WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class TestPaketiRowMapper implements RowMapper<TestPaketi> {
        @Override
        public TestPaketi mapRow(ResultSet rs, int rowNum) throws SQLException {
            TestPaketi paket = new TestPaketi();
            paket.setId(rs.getInt("id"));
            paket.setPaketAdi(rs.getString("paket_adi"));
            paket.setFiyat(rs.getDouble("fiyat"));
            paket.setIcerikAciklamasi(rs.getString("icerik_aciklamasi"));
            return paket;
        }
    }
}

