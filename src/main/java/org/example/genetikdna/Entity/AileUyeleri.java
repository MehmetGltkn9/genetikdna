package org.example.genetikdna.Entity;

public class AileUyeleri {
    private Integer id;
    private Integer kullaniciId; // Foreign Key: Bu bilgiyi hangi kullanıcı ekledi
    private String adSoyad;
    private String iliskiTuru;   // Anne, Baba, Büyükbaba vb.
    private String dogumYeri;
    private java.sql.Date vefatTarihi;

    public AileUyeleri() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(Integer kullaniciId) { this.kullaniciId = kullaniciId; }

    public String getAdSoyad() { return adSoyad; }
    public void setAdSoyad(String adSoyad) { this.adSoyad = adSoyad; }

    public String getIliskiTuru() { return iliskiTuru; }
    public void setIliskiTuru(String iliskiTuru) { this.iliskiTuru = iliskiTuru; }

    public String getDogumYeri() { return dogumYeri; }
    public void setDogumYeri(String dogumYeri) { this.dogumYeri = dogumYeri; }

    public java.sql.Date getVefatTarihi() { return vefatTarihi; }
    public void setVefatTarihi(java.sql.Date vefatTarihi) { this.vefatTarihi = vefatTarihi; }
}