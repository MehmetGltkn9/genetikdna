package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.Kullanici;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class KullaniciDAO {

    private final JdbcTemplate jdbcTemplate;

    public KullaniciDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addKullanici(Kullanici kullanici) {
        String sql = "INSERT INTO kullanici (ad, soyad, dogum_tarihi, cinsiyet, kayit_tarihi) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                kullanici.getAd(),
                kullanici.getSoyad(),
                kullanici.getDogumTarihi(),
                kullanici.getCinsiyet(),
                kullanici.getKayitTarihi());
    }

    // Transaction için: Yeni eklenen kullanıcının ID'sini döndürür
    public Integer addKullaniciAndGetId(Kullanici kullanici) {
        String sql = "INSERT INTO kullanici (ad, soyad, dogum_tarihi, cinsiyet, kayit_tarihi) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Integer.class,
                kullanici.getAd(),
                kullanici.getSoyad(),
                kullanici.getDogumTarihi(),
                kullanici.getCinsiyet(),
                kullanici.getKayitTarihi());
    }

    public List<Kullanici> getAllKullanicilar() {
        String sql = "SELECT * FROM kullanici";
        return jdbcTemplate.query(sql, new KullaniciRowMapper());
    }

    public List<Map<String, Object>> getKullaniciDetaylari(Integer kullaniciId) {
        String sql = """
            SELECT 
                k.id, 
                k.ad, 
                k.soyad, 
                k.dogum_tarihi, 
                k.cinsiyet, 
                k.kayit_tarihi,
                kh.eposta,
                kh.son_giris,
                kh.aktif_mi,
                COUNT(DISTINCT ts.id) AS siparis_sayisi,
                COUNT(DISTINCT gts.id) AS test_sonucu_sayisi
            FROM kullanici k
            LEFT JOIN kullanici_hesap kh ON k.id = kh.kullanici_id
            LEFT JOIN test_siparisi ts ON k.id = ts.kullanici_id
            LEFT JOIN genetik_test_sonucu gts ON k.id = gts.kullanici_id
            WHERE k.id = ?
            GROUP BY k.id, k.ad, k.soyad, k.dogum_tarihi, k.cinsiyet, k.kayit_tarihi, kh.eposta, kh.son_giris, kh.aktif_mi
        """;
        return jdbcTemplate.queryForList(sql, kullaniciId);
    }

    public Kullanici getKullaniciById(Integer id) {
        String sql = "SELECT * FROM kullanici WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new KullaniciRowMapper(), id);
    }

    public void deleteKullanici(Integer id) {
        String sql = "DELETE FROM kullanici WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Stored Procedure: Kullanıcı ve Hesap Ekleme
    public Map<String, Object> kullaniciVeHesapEkle(String ad, String soyad, java.sql.Date dogumTarihi, 
                                                      String cinsiyet, String eposta, String parolaHash) {
        String sql = "SELECT * FROM sp_kullanici_ve_hesap_ekle(?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.queryForMap(sql, ad, soyad, dogumTarihi, cinsiyet, eposta, parolaHash);
    }

    // Stored Procedure: Kullanıcı Güncelleme
    public Map<String, Object> kullaniciGuncelle(Integer id, String ad, String soyad, 
                                                java.sql.Date dogumTarihi, String cinsiyet, Integer kullaniciId) {
        String sql = "SELECT * FROM sp_kullanici_guncelle(?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.queryForMap(sql, id, ad, soyad, dogumTarihi, cinsiyet, kullaniciId);
    }

    private static class KullaniciRowMapper implements RowMapper<Kullanici> {
        @Override
        public Kullanici mapRow(ResultSet rs, int rowNum) throws SQLException {
            Kullanici kullanici = new Kullanici();
            kullanici.setId(rs.getInt("id"));
            kullanici.setAd(rs.getString("ad"));
            kullanici.setSoyad(rs.getString("soyad"));
            kullanici.setDogumTarihi(rs.getDate("dogum_tarihi"));
            kullanici.setCinsiyet(rs.getString("cinsiyet"));
            kullanici.setKayitTarihi(rs.getTimestamp("kayit_tarihi"));
            return kullanici;
        }
    }
}

