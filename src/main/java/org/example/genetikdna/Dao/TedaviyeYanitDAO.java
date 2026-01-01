package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.TedaviyeYanit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class TedaviyeYanitDAO {

    private final JdbcTemplate jdbcTemplate;

    public TedaviyeYanitDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTedaviyeYanit(TedaviyeYanit yanit) {
        String sql = "INSERT INTO tedaviye_yanit (sonuc_id, ilac_adi, yanit_tahmini, oneriler) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                yanit.getSonucId(),
                yanit.getIlacAdi(),
                yanit.getYanitTahmini(),
                yanit.getOneriler());
    }

    public List<TedaviyeYanit> getAllTedaviyeYanitlar() {
        String sql = "SELECT * FROM tedaviye_yanit";
        return jdbcTemplate.query(sql, new TedaviyeYanitRowMapper());
    }

    public List<Map<String, Object>> getTedaviyeYanitlarBySonucId(Integer sonucId) {
        String sql = """
            SELECT 
                ty.id, 
                ty.ilac_adi, 
                ty.yanit_tahmini, 
                ty.oneriler,
                gts.yayim_tarihi,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM tedaviye_yanit ty
            JOIN genetik_test_sonucu gts ON ty.sonuc_id = gts.id
            JOIN kullanici k ON gts.kullanici_id = k.id
            WHERE ty.sonuc_id = ?
        """;
        return jdbcTemplate.queryForList(sql, sonucId);
    }

    public TedaviyeYanit getTedaviyeYanitById(Integer id) {
        String sql = "SELECT * FROM tedaviye_yanit WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new TedaviyeYanitRowMapper(), id);
    }

    public void deleteTedaviyeYanit(Integer id) {
        String sql = "DELETE FROM tedaviye_yanit WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class TedaviyeYanitRowMapper implements RowMapper<TedaviyeYanit> {
        @Override
        public TedaviyeYanit mapRow(ResultSet rs, int rowNum) throws SQLException {
            TedaviyeYanit yanit = new TedaviyeYanit();
            yanit.setId(rs.getInt("id"));
            yanit.setSonucId(rs.getInt("sonuc_id"));
            yanit.setIlacAdi(rs.getString("ilac_adi"));
            yanit.setYanitTahmini(rs.getString("yanit_tahmini"));
            yanit.setOneriler(rs.getString("oneriler"));
            return yanit;
        }
    }
}

