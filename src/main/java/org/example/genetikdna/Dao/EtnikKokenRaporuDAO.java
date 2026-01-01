package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.EtnikKokenRaporu;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class EtnikKokenRaporuDAO {

    private final JdbcTemplate jdbcTemplate;

    public EtnikKokenRaporuDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addEtnikKokenRaporu(EtnikKokenRaporu rapor) {
        String sql = "INSERT INTO etnik_koken_raporu (sonuc_id, bolge_adi, yuzde_orani, rapor_detayi) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                rapor.getSonucId(),
                rapor.getBolgeAdi(),
                rapor.getYuzdeOrani(),
                rapor.getRaporDetayi());
    }

    public List<EtnikKokenRaporu> getAllEtnikKokenRaporlari() {
        String sql = "SELECT * FROM etnik_koken_raporu";
        return jdbcTemplate.query(sql, new EtnikKokenRaporuRowMapper());
    }

    public List<Map<String, Object>> getEtnikKokenRaporlariBySonucId(Integer sonucId) {
        String sql = """
            SELECT 
                e.id, 
                e.bolge_adi, 
                e.yuzde_orani, 
                e.rapor_detayi,
                gts.yayim_tarihi,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM etnik_koken_raporu e
            JOIN genetik_test_sonucu gts ON e.sonuc_id = gts.id
            JOIN kullanici k ON gts.kullanici_id = k.id
            WHERE e.sonuc_id = ?
        """;
        return jdbcTemplate.queryForList(sql, sonucId);
    }

    public EtnikKokenRaporu getEtnikKokenRaporuById(Integer id) {
        String sql = "SELECT * FROM etnik_koken_raporu WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new EtnikKokenRaporuRowMapper(), id);
    }

    public void deleteEtnikKokenRaporu(Integer id) {
        String sql = "DELETE FROM etnik_koken_raporu WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class EtnikKokenRaporuRowMapper implements RowMapper<EtnikKokenRaporu> {
        @Override
        public EtnikKokenRaporu mapRow(ResultSet rs, int rowNum) throws SQLException {
            EtnikKokenRaporu rapor = new EtnikKokenRaporu();
            rapor.setId(rs.getInt("id"));
            rapor.setSonucId(rs.getInt("sonuc_id"));
            rapor.setBolgeAdi(rs.getString("bolge_adi"));
            rapor.setYuzdeOrani(rs.getDouble("yuzde_orani"));
            rapor.setRaporDetayi(rs.getString("rapor_detayi"));
            return rapor;
        }
    }
}

