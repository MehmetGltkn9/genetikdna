package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.GenetikVaryant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class GenetikVaryantDAO {

    private final JdbcTemplate jdbcTemplate;

    public GenetikVaryantDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addGenetikVaryant(GenetikVaryant varyant) {
        String sql = "INSERT INTO genetik_varyant (rs_id, kromozom, konum, referans_alel) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                varyant.getRsId(),
                varyant.getKromozom(),
                varyant.getKonum(),
                varyant.getReferansAlel());
    }

    public List<GenetikVaryant> getAllGenetikVaryantlar() {
        String sql = "SELECT * FROM genetik_varyant";
        return jdbcTemplate.query(sql, new GenetikVaryantRowMapper());
    }

    public List<Map<String, Object>> getGenetikVaryantlarByKromozom(String kromozom) {
        String sql = """
            SELECT 
                gv.id, 
                gv.rs_id, 
                gv.kromozom, 
                gv.konum, 
                gv.referans_alel,
                COUNT(kvs.id) AS kullanici_sayisi
            FROM genetik_varyant gv
            LEFT JOIN kullanici_varyant_sonucu kvs ON gv.id = kvs.varyant_id
            WHERE gv.kromozom = ?
            GROUP BY gv.id, gv.rs_id, gv.kromozom, gv.konum, gv.referans_alel
        """;
        return jdbcTemplate.queryForList(sql, kromozom);
    }

    public GenetikVaryant getGenetikVaryantById(Integer id) {
        String sql = "SELECT * FROM genetik_varyant WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new GenetikVaryantRowMapper(), id);
    }

    public GenetikVaryant getGenetikVaryantByRsId(String rsId) {
        String sql = "SELECT * FROM genetik_varyant WHERE rs_id = ?";
        return jdbcTemplate.queryForObject(sql, new GenetikVaryantRowMapper(), rsId);
    }

    public void deleteGenetikVaryant(Integer id) {
        String sql = "DELETE FROM genetik_varyant WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class GenetikVaryantRowMapper implements RowMapper<GenetikVaryant> {
        @Override
        public GenetikVaryant mapRow(ResultSet rs, int rowNum) throws SQLException {
            GenetikVaryant varyant = new GenetikVaryant();
            varyant.setId(rs.getInt("id"));
            varyant.setRsId(rs.getString("rs_id"));
            varyant.setKromozom(rs.getString("kromozom"));
            varyant.setKonum(rs.getInt("konum"));
            varyant.setReferansAlel(rs.getString("referans_alel"));
            return varyant;
        }
    }
}

