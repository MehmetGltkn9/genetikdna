package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.Numune;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class NumuneDAO {

    private final JdbcTemplate jdbcTemplate;

    public NumuneDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addNumune(Numune numune) {
        String sql = "INSERT INTO numune (siparis_id, barkod_id, numune_tipi, laboratuvara_varis, durum) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                numune.getSiparisId(),
                numune.getBarkodId(),
                numune.getNumuneTipi(),
                numune.getLaboratuvaraVaris(),
                numune.getDurum());
    }

    public List<Numune> getAllNumuneler() {
        String sql = "SELECT * FROM numune";
        return jdbcTemplate.query(sql, new NumuneRowMapper());
    }

    public List<Map<String, Object>> getNumunelerBySiparisId(Integer siparisId) {
        String sql = """
            SELECT 
                n.id, 
                n.barkod_id, 
                n.numune_tipi, 
                n.laboratuvara_varis, 
                n.durum,
                ts.siparis_tarihi,
                ts.odeme_durumu,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM numune n
            JOIN test_siparisi ts ON n.siparis_id = ts.id
            JOIN kullanici k ON ts.kullanici_id = k.id
            WHERE n.siparis_id = ?
        """;
        return jdbcTemplate.queryForList(sql, siparisId);
    }

    public List<Map<String, Object>> getNumuneAnalizleri(Integer numuneId) {
        String sql = """
            SELECT 
                n.id, 
                n.barkod_id, 
                n.numune_tipi, 
                n.durum,
                la.analiz_baslangic,
                la.analiz_bitis,
                la.teknisyen_adi,
                la.kalite_kontrol_sonucu
            FROM numune n
            LEFT JOIN laboratuvar_analizi la ON n.id = la.numune_id
            WHERE n.id = ?
        """;
        return jdbcTemplate.queryForList(sql, numuneId);
    }

    public Numune getNumuneById(Integer id) {
        String sql = "SELECT * FROM numune WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new NumuneRowMapper(), id);
    }

    public Numune getNumuneByBarkodId(String barkodId) {
        String sql = "SELECT * FROM numune WHERE barkod_id = ?";
        return jdbcTemplate.queryForObject(sql, new NumuneRowMapper(), barkodId);
    }

    public void deleteNumune(Integer id) {
        String sql = "DELETE FROM numune WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class NumuneRowMapper implements RowMapper<Numune> {
        @Override
        public Numune mapRow(ResultSet rs, int rowNum) throws SQLException {
            Numune numune = new Numune();
            numune.setId(rs.getInt("id"));
            numune.setSiparisId(rs.getInt("siparis_id"));
            numune.setBarkodId(rs.getString("barkod_id"));
            numune.setNumuneTipi(rs.getString("numune_tipi"));
            numune.setLaboratuvaraVaris(rs.getDate("laboratuvara_varis"));
            numune.setDurum(rs.getString("durum"));
            return numune;
        }
    }
}

