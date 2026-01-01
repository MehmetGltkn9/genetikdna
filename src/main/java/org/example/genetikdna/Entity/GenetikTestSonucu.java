package org.example.genetikdna.Entity;

public class GenetikTestSonucu {
    private Integer id;
    private Integer kullaniciId;
    private Integer analizId;
    private java.sql.Timestamp yayimTarihi;
    private String veriSurumu;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(Integer kullaniciId) { this.kullaniciId = kullaniciId; }
    public Integer getAnalizId() { return analizId; }
    public void setAnalizId(Integer analizId) { this.analizId = analizId; }
    public java.sql.Timestamp getYayimTarihi() { return yayimTarihi; }
    public void setYayimTarihi(java.sql.Timestamp yayimTarihi) { this.yayimTarihi = yayimTarihi; }
    public String getVeriSurumu() { return veriSurumu; }
    public void setVeriSurumu(String veriSurumu) { this.veriSurumu = veriSurumu; }
}