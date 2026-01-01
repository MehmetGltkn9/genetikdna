package org.example.genetikdna.Dao;

import org.example.genetikdna.Entity.VeriErisimIzni;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class VeriErisimIzniDAO {

    private final JdbcTemplate jdbcTemplate;

    public VeriErisimIzniDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addVeriErisimIzni(VeriErisimIzni izin) {
        String sql = "INSERT INTO veri_erisim_izni (kullanici_id, izin_tipi, izin_verildi, izin_tarihi) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                izin.getKullaniciId(),
                izin.getIzinTipi(),
                izin.isIzinVerildi(),
                izin.getIzinTarihi());
    }

    public List<VeriErisimIzni> getAllVeriErisimIzinleri() {
        String sql = "SELECT * FROM veri_erisim_izni";
        return jdbcTemplate.query(sql, new VeriErisimIzniRowMapper());
    }

    public List<Map<String, Object>> getVeriErisimIzinleriByKullaniciId(Integer kullaniciId) {
        String sql = """
            SELECT 
                vei.id, 
                vei.izin_tipi, 
                vei.izin_verildi, 
                vei.izin_tarihi,
                k.ad AS kullanici_adi, 
                k.soyad AS kullanici_soyadi
            FROM veri_erisim_izni vei
            JOIN kullanici k ON vei.kullanici_id = k.id
            WHERE vei.kullanici_id = ?
        """;
        return jdbcTemplate.queryForList(sql, kullaniciId);
    }

    public VeriErisimIzni getVeriErisimIzniById(Integer id) {
        String sql = "SELECT * FROM veri_erisim_izni WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new VeriErisimIzniRowMapper(), id);
    }

    public void deleteVeriErisimIzni(Integer id) {
        String sql = "DELETE FROM veri_erisim_izni WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class VeriErisimIzniRowMapper implements RowMapper<VeriErisimIzni> {
        @Override
        public VeriErisimIzni mapRow(ResultSet rs, int rowNum) throws SQLException {
            VeriErisimIzni izin = new VeriErisimIzni();
            izin.setId(rs.getInt("id"));
            izin.setKullaniciId(rs.getInt("kullanici_id"));
            izin.setIzinTipi(rs.getString("izin_tipi"));
            izin.setIzinVerildi(rs.getBoolean("izin_verildi"));
            izin.setIzinTarihi(rs.getTimestamp("izin_tarihi"));
            return izin;
        }
    }
}

