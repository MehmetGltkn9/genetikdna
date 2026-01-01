package org.example.genetikdna.Entity;

public class Kullanici {
    private Integer id;
    private String ad;
    private String soyad;
    private java.sql.Date dogumTarihi;
    private String cinsiyet;
    private java.sql.Timestamp kayitTarihi;

    public Kullanici() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public String getSoyad() { return soyad; }
    public void setSoyad(String soyad) { this.soyad = soyad; }
    public java.sql.Date getDogumTarihi() { return dogumTarihi; }
    public void setDogumTarihi(java.sql.Date dogumTarihi) { this.dogumTarihi = dogumTarihi; }
    public String getCinsiyet() { return cinsiyet; }
    public void setCinsiyet(String cinsiyet) { this.cinsiyet = cinsiyet; }
    public java.sql.Timestamp getKayitTarihi() { return kayitTarihi; }
    public void setKayitTarihi(java.sql.Timestamp kayitTarihi) { this.kayitTarihi = kayitTarihi; }
}