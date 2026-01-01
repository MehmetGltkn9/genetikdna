package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.SoyagaciBaglantisi;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class SoyagaciBaglantisiDAO {

    private final JdbcTemplate jdbcTemplate;

    public SoyagaciBaglantisiDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addSoyagaciBaglantisi(SoyagaciBaglantisi baglanti) {
        String sql = "INSERT INTO soyagaci_baglantisi (kullanici_bir_id, kullanici_iki_id, tahmini_iliski, paylasilan_dna) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                baglanti.getKullaniciBirId(),
                baglanti.getKullaniciIkiId(),
                baglanti.getTahminiIliski(),
                baglanti.getPaylasilanDna());
    }

    public List<SoyagaciBaglantisi> getAllSoyagaciBaglantilari() {
        String sql = "SELECT * FROM soyagaci_baglantisi";
        return jdbcTemplate.query(sql, new SoyagaciBaglantisiRowMapper());
    }

    public List<Map<String, Object>> getSoyagaciBaglantilariByKullaniciId(Integer kullaniciId) {
        String sql = """
            SELECT 
                sb.id, 
                sb.tahmini_iliski, 
                sb.paylasilan_dna,
                k1.ad AS kullanici_bir_adi, 
                k1.soyad AS kullanici_bir_soyadi,
                k2.ad AS kullanici_iki_adi, 
                k2.soyad AS kullanici_iki_soyadi
            FROM soyagaci_baglantisi sb
            JOIN kullanici k1 ON sb.kullanici_bir_id = k1.id
            JOIN kullanici k2 ON sb.kullanici_iki_id = k2.id
            WHERE sb.kullanici_bir_id = ? OR sb.kullanici_iki_id = ?
        """;
        return jdbcTemplate.queryForList(sql, kullaniciId, kullaniciId);
    }

    public SoyagaciBaglantisi getSoyagaciBaglantisiById(Integer id) {
        String sql = "SELECT * FROM soyagaci_baglantisi WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new SoyagaciBaglantisiRowMapper(), id);
    }

    public void deleteSoyagaciBaglantisi(Integer id) {
        String sql = "DELETE FROM soyagaci_baglantisi WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class SoyagaciBaglantisiRowMapper implements RowMapper<SoyagaciBaglantisi> {
        @Override
        public SoyagaciBaglantisi mapRow(ResultSet rs, int rowNum) throws SQLException {
            SoyagaciBaglantisi baglanti = new SoyagaciBaglantisi();
            baglanti.setId(rs.getInt("id"));
            baglanti.setKullaniciBirId(rs.getInt("kullanici_bir_id"));
            baglanti.setKullaniciIkiId(rs.getInt("kullanici_iki_id"));
            baglanti.setTahminiIliski(rs.getString("tahmini_iliski"));
            baglanti.setPaylasilanDna(rs.getDouble("paylasilan_dna"));
            return baglanti;
        }
    }
}

