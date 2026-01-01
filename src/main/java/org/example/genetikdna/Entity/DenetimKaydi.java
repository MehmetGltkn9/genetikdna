package org.example.genetikdna.Entity;

public class DenetimKaydi {
    private Long id; // Veri yoğunluğu için Long kullanıldı
    private java.sql.Timestamp islemTarihi;
    private Integer kullaniciId; // İşlemi yapan kişi
    private String etkilenenTablo;
    private Integer etkilenenId;
    private String islemTipi;    // INSERT, UPDATE, DELETE
    private String aciklama;

    public DenetimKaydi() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public java.sql.Timestamp getIslemTarihi() { return islemTarihi; }
    public void setIslemTarihi(java.sql.Timestamp islemTarihi) { this.islemTarihi = islemTarihi; }

    public Integer getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(Integer kullaniciId) { this.kullaniciId = kullaniciId; }

    public String getEtkilenenTablo() { return etkilenenTablo; }
    public void setEtkilenenTablo(String etkilenenTablo) { this.etkilenenTablo = etkilenenTablo; }

    public Integer getEtkilenenId() { return etkilenenId; }
    public void setEtkilenenId(Integer etkilenenId) { this.etkilenenId = etkilenenId; }

    public String getIslemTipi() { return islemTipi; }
    public void setIslemTipi(String islemTipi) { this.islemTipi = islemTipi; }

    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
}