package org.example.genetikdna.Entity;

public class TestSiparisi {
    private Integer id;
    private Integer kullaniciId;
    private Integer paketId;
    private java.sql.Timestamp siparisTarihi;
    private double toplamTutar;
    private String odemeDurumu;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(Integer kullaniciId) { this.kullaniciId = kullaniciId; }
    public Integer getPaketId() { return paketId; }
    public void setPaketId(Integer paketId) { this.paketId = paketId; }
    public java.sql.Timestamp getSiparisTarihi() { return siparisTarihi; }
    public void setSiparisTarihi(java.sql.Timestamp siparisTarihi) { this.siparisTarihi = siparisTarihi; }
    public double getToplamTutar() { return toplamTutar; }
    public void setToplamTutar(double toplamTutar) { this.toplamTutar = toplamTutar; }
    public String getOdemeDurumu() { return odemeDurumu; }
    public void setOdemeDurumu(String odemeDurumu) { this.odemeDurumu = odemeDurumu; }
}