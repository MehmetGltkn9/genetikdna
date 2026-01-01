package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.TestSiparisi;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class TestSiparisiDAO {

    private final JdbcTemplate jdbcTemplate;

    public TestSiparisiDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTestSiparisi(TestSiparisi siparis) {
        String sql = "INSERT INTO test_siparisi (kullanici_id, paket_id, siparis_tarihi, toplam_tutar, odeme_durumu) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                siparis.getKullaniciId(),
                siparis.getPaketId(),
                siparis.getSiparisTarihi(),
                siparis.getToplamTutar(),
                siparis.getOdemeDurumu());
    }

    // Transaction için: Yeni eklenen siparişin ID'sini döndürür
    public Integer addTestSiparisiAndGetId(TestSiparisi siparis) {
        String sql = "INSERT INTO test_siparisi (kullanici_id, paket_id, siparis_tarihi, toplam_tutar, odeme_durumu) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Integer.class,
                siparis.getKullaniciId(),
                siparis.getPaketId(),
                siparis.getSiparisTarihi(),
                siparis.getToplamTutar(),
                siparis.getOdemeDurumu());
    }

    public List<TestSiparisi> getAllTestSiparisleri() {
        String sql = "SELECT * FROM test_siparisi";
        return jdbcTemplate.query(sql, new TestSiparisiRowMapper());
    }

    public List<Map<String, Object>> getTestSiparisleriByKullaniciId(Integer kullaniciId) {
        String sql = """
            SELECT 
                ts.id, 
                ts.siparis_tarihi, 
                ts.toplam_tutar, 
                ts.odeme_durumu,
                tp.paket_adi,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM test_siparisi ts
            JOIN test_paketi tp ON ts.paket_id = tp.id
            JOIN kullanici k ON ts.kullanici_id = k.id
            WHERE ts.kullanici_id = ?
        """;
        return jdbcTemplate.queryForList(sql, kullaniciId);
    }

    public List<Map<String, Object>> getTestSiparisiDetaylari(Integer siparisId) {
        String sql = """
            SELECT 
                ts.id, 
                ts.siparis_tarihi, 
                ts.toplam_tutar, 
                ts.odeme_durumu,
                tp.paket_adi,
                tp.fiyat,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi,
                COUNT(n.id) AS numune_sayisi
            FROM test_siparisi ts
            JOIN test_paketi tp ON ts.paket_id = tp.id
            JOIN kullanici k ON ts.kullanici_id = k.id
            LEFT JOIN numune n ON ts.id = n.siparis_id
            WHERE ts.id = ?
            GROUP BY ts.id, ts.siparis_tarihi, ts.toplam_tutar, ts.odeme_durumu, tp.paket_adi, tp.fiyat, k.ad, k.soyad
        """;
        return jdbcTemplate.queryForList(sql, siparisId);
    }

    public TestSiparisi getTestSiparisiById(Integer id) {
        String sql = "SELECT * FROM test_siparisi WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new TestSiparisiRowMapper(), id);
    }

    public void deleteTestSiparisi(Integer id) {
        String sql = "DELETE FROM test_siparisi WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Stored Procedure: Test Siparişi ve Numune Ekleme
    public Map<String, Object> testSiparisiVeNumuneEkle(Integer kullaniciId, Integer paketId, 
                                                          Double toplamTutar, String barkodId, String numuneTipi) {
        String sql = "SELECT * FROM sp_test_siparisi_ve_numune_ekle(?, ?, ?, ?, ?)";
        return jdbcTemplate.queryForMap(sql, kullaniciId, paketId, toplamTutar, barkodId, numuneTipi);
    }

    // Stored Procedure: Test Siparişi Ödeme
    public Map<String, Object> testSiparisiOdeme(Integer siparisId, String odemeDurumu) {
        String sql = "SELECT * FROM sp_test_siparisi_odeme(?, ?)";
        return jdbcTemplate.queryForMap(sql, siparisId, odemeDurumu);
    }

    private static class TestSiparisiRowMapper implements RowMapper<TestSiparisi> {
        @Override
        public TestSiparisi mapRow(ResultSet rs, int rowNum) throws SQLException {
            TestSiparisi siparis = new TestSiparisi();
            siparis.setId(rs.getInt("id"));
            siparis.setKullaniciId(rs.getInt("kullanici_id"));
            siparis.setPaketId(rs.getInt("paket_id"));
            siparis.setSiparisTarihi(rs.getTimestamp("siparis_tarihi"));
            siparis.setToplamTutar(rs.getDouble("toplam_tutar"));
            siparis.setOdemeDurumu(rs.getString("odeme_durumu"));
            return siparis;
        }
    }
}

