package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.DenetimKaydi;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class DenetimKaydiDAO {

    private final JdbcTemplate jdbcTemplate;

    public DenetimKaydiDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addDenetimKaydi(DenetimKaydi denetimKaydi) {
        String sql = "INSERT INTO denetim_kaydi (islem_tarihi, kullanici_id, etkilenen_tablo, etkilenen_id, islem_tipi, aciklama) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                denetimKaydi.getIslemTarihi(),
                denetimKaydi.getKullaniciId(),
                denetimKaydi.getEtkilenenTablo(),
                denetimKaydi.getEtkilenenId(),
                denetimKaydi.getIslemTipi(),
                denetimKaydi.getAciklama());
    }

    public List<DenetimKaydi> getAllDenetimKayitlari() {
        String sql = "SELECT * FROM denetim_kaydi";
        return jdbcTemplate.query(sql, new DenetimKaydiRowMapper());
    }

    public List<Map<String, Object>> getDenetimKayitlariByKullaniciId(Integer kullaniciId) {
        String sql = """
            SELECT 
                d.id, 
                d.islem_tarihi, 
                d.etkilenen_tablo, 
                d.etkilenen_id, 
                d.islem_tipi, 
                d.aciklama,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM denetim_kaydi d
            JOIN kullanici k ON d.kullanici_id = k.id
            WHERE d.kullanici_id = ?
        """;
        return jdbcTemplate.queryForList(sql, kullaniciId);
    }

    public List<Map<String, Object>> getDenetimKayitlariByTablo(String tabloAdi) {
        String sql = """
            SELECT 
                d.id, 
                d.islem_tarihi, 
                d.etkilenen_tablo, 
                d.etkilenen_id, 
                d.islem_tipi, 
                d.aciklama,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM denetim_kaydi d
            JOIN kullanici k ON d.kullanici_id = k.id
            WHERE d.etkilenen_tablo = ?
        """;
        return jdbcTemplate.queryForList(sql, tabloAdi);
    }

    public DenetimKaydi getDenetimKaydiById(Long id) {
        String sql = "SELECT * FROM denetim_kaydi WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new DenetimKaydiRowMapper(), id);
    }

    public void deleteDenetimKaydi(Long id) {
        String sql = "DELETE FROM denetim_kaydi WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class DenetimKaydiRowMapper implements RowMapper<DenetimKaydi> {
        @Override
        public DenetimKaydi mapRow(ResultSet rs, int rowNum) throws SQLException {
            DenetimKaydi denetimKaydi = new DenetimKaydi();
            denetimKaydi.setId(rs.getLong("id"));
            denetimKaydi.setIslemTarihi(rs.getTimestamp("islem_tarihi"));
            denetimKaydi.setKullaniciId(rs.getInt("kullanici_id"));
            denetimKaydi.setEtkilenenTablo(rs.getString("etkilenen_tablo"));
            denetimKaydi.setEtkilenenId(rs.getInt("etkilenen_id"));
            denetimKaydi.setIslemTipi(rs.getString("islem_tipi"));
            denetimKaydi.setAciklama(rs.getString("aciklama"));
            return denetimKaydi;
        }
    }
}

