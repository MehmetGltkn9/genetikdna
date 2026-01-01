package org.example.genetikdna.Entity;

public class VeriErisimIzni {
    private Integer id;
    private Integer kullaniciId; // FK: Kullanici.id
    private String izinTipi;
    private boolean izinVerildi;
    private java.sql.Timestamp izinTarihi;

    public VeriErisimIzni() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(Integer kullaniciId) { this.kullaniciId = kullaniciId; }
    public String getIzinTipi() { return izinTipi; }
    public void setIzinTipi(String izinTipi) { this.izinTipi = izinTipi; }
    public boolean isIzinVerildi() { return izinVerildi; }
    public void setIzinVerildi(boolean izinVerildi) { this.izinVerildi = izinVerildi; }
    public java.sql.Timestamp getIzinTarihi() { return izinTarihi; }
    public void setIzinTarihi(java.sql.Timestamp izinTarihi) { this.izinTarihi = izinTarihi; }
}