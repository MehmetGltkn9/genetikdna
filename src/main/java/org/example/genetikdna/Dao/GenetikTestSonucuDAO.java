package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.GenetikTestSonucu;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class GenetikTestSonucuDAO {

    private final JdbcTemplate jdbcTemplate;

    public GenetikTestSonucuDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addGenetikTestSonucu(GenetikTestSonucu sonuc) {
        String sql = "INSERT INTO genetik_test_sonucu (kullanici_id, analiz_id, yayim_tarihi, veri_surumu) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                sonuc.getKullaniciId(),
                sonuc.getAnalizId(),
                sonuc.getYayimTarihi(),
                sonuc.getVeriSurumu());
    }

    // Transaction için: Yeni eklenen test sonucunun ID'sini döndürür
    public Integer addGenetikTestSonucuAndGetId(GenetikTestSonucu sonuc) {
        String sql = "INSERT INTO genetik_test_sonucu (kullanici_id, analiz_id, yayim_tarihi, veri_surumu) " +
                "VALUES (?, ?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Integer.class,
                sonuc.getKullaniciId(),
                sonuc.getAnalizId(),
                sonuc.getYayimTarihi(),
                sonuc.getVeriSurumu());
    }

    public List<GenetikTestSonucu> getAllGenetikTestSonuclari() {
        String sql = "SELECT * FROM genetik_test_sonucu";
        return jdbcTemplate.query(sql, new GenetikTestSonucuRowMapper());
    }

    public List<Map<String, Object>> getGenetikTestSonuclariByKullaniciId(Integer kullaniciId) {
        String sql = """
            SELECT 
                gts.id, 
                gts.yayim_tarihi, 
                gts.veri_surumu,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi,
                la.teknisyen_adi,
                la.analiz_baslangic,
                la.analiz_bitis
            FROM genetik_test_sonucu gts
            JOIN kullanici k ON gts.kullanici_id = k.id
            LEFT JOIN laboratuvar_analizi la ON gts.analiz_id = la.id
            WHERE gts.kullanici_id = ?
        """;
        return jdbcTemplate.queryForList(sql, kullaniciId);
    }

    public List<Map<String, Object>> getGenetikTestSonuclariByAnalizId(Integer analizId) {
        String sql = """
            SELECT 
                gts.id, 
                gts.yayim_tarihi, 
                gts.veri_surumu,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM genetik_test_sonucu gts
            JOIN kullanici k ON gts.kullanici_id = k.id
            WHERE gts.analiz_id = ?
        """;
        return jdbcTemplate.queryForList(sql, analizId);
    }

    public GenetikTestSonucu getGenetikTestSonucuById(Integer id) {
        String sql = "SELECT * FROM genetik_test_sonucu WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new GenetikTestSonucuRowMapper(), id);
    }

    public void deleteGenetikTestSonucu(Integer id) {
        String sql = "DELETE FROM genetik_test_sonucu WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Stored Procedure: Genetik Test Sonucu ve Veri Ekleme
    public Map<String, Object> genetikTestSonucuVeVeriEkle(Integer kullaniciId, Integer analizId, 
                                                             String veriSurumu, String hamVeriYolu, Integer dosyaBoyutuMb) {
        String sql = "SELECT * FROM sp_genetik_test_sonucu_ekle(?, ?, ?, ?, ?)";
        return jdbcTemplate.queryForMap(sql, kullaniciId, analizId, veriSurumu, hamVeriYolu, dosyaBoyutuMb);
    }

    private static class GenetikTestSonucuRowMapper implements RowMapper<GenetikTestSonucu> {
        @Override
        public GenetikTestSonucu mapRow(ResultSet rs, int rowNum) throws SQLException {
            GenetikTestSonucu sonuc = new GenetikTestSonucu();
            sonuc.setId(rs.getInt("id"));
            sonuc.setKullaniciId(rs.getInt("kullanici_id"));
            sonuc.setAnalizId(rs.getInt("analiz_id"));
            sonuc.setYayimTarihi(rs.getTimestamp("yayim_tarihi"));
            sonuc.setVeriSurumu(rs.getString("veri_surumu"));
            return sonuc;
        }
    }
}

