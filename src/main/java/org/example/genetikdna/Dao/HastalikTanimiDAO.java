package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.HastalikTanimi;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HastalikTanimiDAO {

    private final JdbcTemplate jdbcTemplate;

    public HastalikTanimiDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addHastalikTanimi(HastalikTanimi hastalikTanimi) {
        String sql = "INSERT INTO hastalik_tanimi (hastalik_adi, icd_kodu, bilimsel_tanim) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                hastalikTanimi.getHastalikAdi(),
                hastalikTanimi.getIcdKodu(),
                hastalikTanimi.getBilimselTanim());
    }

    public List<HastalikTanimi> getAllHastalikTanimlari() {
        String sql = "SELECT * FROM hastalik_tanimi";
        return jdbcTemplate.query(sql, new HastalikTanimiRowMapper());
    }

    public HastalikTanimi getHastalikTanimiById(Integer id) {
        String sql = "SELECT * FROM hastalik_tanimi WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new HastalikTanimiRowMapper(), id);
    }

    public HastalikTanimi getHastalikTanimiByIcdKodu(String icdKodu) {
        String sql = "SELECT * FROM hastalik_tanimi WHERE icd_kodu = ?";
        return jdbcTemplate.queryForObject(sql, new HastalikTanimiRowMapper(), icdKodu);
    }

    public void deleteHastalikTanimi(Integer id) {
        String sql = "DELETE FROM hastalik_tanimi WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class HastalikTanimiRowMapper implements RowMapper<HastalikTanimi> {
        @Override
        public HastalikTanimi mapRow(ResultSet rs, int rowNum) throws SQLException {
            HastalikTanimi hastalikTanimi = new HastalikTanimi();
            hastalikTanimi.setId(rs.getInt("id"));
            hastalikTanimi.setHastalikAdi(rs.getString("hastalik_adi"));
            hastalikTanimi.setIcdKodu(rs.getString("icd_kodu"));
            hastalikTanimi.setBilimselTanim(rs.getString("bilimsel_tanim"));
            return hastalikTanimi;
        }
    }
}

