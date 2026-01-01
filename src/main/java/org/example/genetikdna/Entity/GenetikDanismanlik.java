package org.example.genetikdna.Entity;

public class GenetikDanismanlik {
    private Integer id;
    private Integer kullaniciId; // FK: Kullanici.id
    private String danismanAdi;
    private java.sql.Timestamp gorusmeTarihi;
    private String gorusmeOzeti;

    public GenetikDanismanlik() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(Integer kullaniciId) { this.kullaniciId = kullaniciId; }
    public String getDanismanAdi() { return danismanAdi; }
    public void setDanismanAdi(String danismanAdi) { this.danismanAdi = danismanAdi; }
    public java.sql.Timestamp getGorusmeTarihi() { return gorusmeTarihi; }
    public void setGorusmeTarihi(java.sql.Timestamp gorusmeTarihi) { this.gorusmeTarihi = gorusmeTarihi; }
    public String getGorusmeOzeti() { return gorusmeOzeti; }
    public void setGorusmeOzeti(String gorusmeOzeti) { this.gorusmeOzeti = gorusmeOzeti; }
}