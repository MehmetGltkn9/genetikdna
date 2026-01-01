package org.example.genetikdna.Entity;

public class LaboratuvarAnalizi {
    private Integer id;
    private Integer numuneId; // FK: Numune.id
    private java.sql.Timestamp analizBaslangic;
    private java.sql.Timestamp analizBitis;
    private String teknisyenAdi;
    private String cihazBilgisi;
    private String kaliteKontrolSonucu;

    public LaboratuvarAnalizi() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getNumuneId() { return numuneId; }
    public void setNumuneId(Integer numuneId) { this.numuneId = numuneId; }
    public java.sql.Timestamp getAnalizBaslangic() { return analizBaslangic; }
    public void setAnalizBaslangic(java.sql.Timestamp analizBaslangic) { this.analizBaslangic = analizBaslangic; }
    public java.sql.Timestamp getAnalizBitis() { return analizBitis; }
    public void setAnalizBitis(java.sql.Timestamp analizBitis) { this.analizBitis = analizBitis; }
    public String getTeknisyenAdi() { return teknisyenAdi; }
    public void setTeknisyenAdi(String teknisyenAdi) { this.teknisyenAdi = teknisyenAdi; }
    public String getCihazBilgisi() { return cihazBilgisi; }
    public void setCihazBilgisi(String cihazBilgisi) { this.cihazBilgisi = cihazBilgisi; }
    public String getKaliteKontrolSonucu() { return kaliteKontrolSonucu; }
    public void setKaliteKontrolSonucu(String kaliteKontrolSonucu) { this.kaliteKontrolSonucu = kaliteKontrolSonucu; }
}