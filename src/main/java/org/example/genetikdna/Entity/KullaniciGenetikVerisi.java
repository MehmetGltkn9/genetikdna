package org.example.genetikdna.Entity;

public class KullaniciGenetikVerisi {
    private Integer id;
    private Integer sonucId; // FK: GenetikTestSonucu.id
    private String hamVeriDepolamaYolu;
    private int dosyaBoyutuMb;

    public KullaniciGenetikVerisi() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getSonucId() { return sonucId; }
    public void setSonucId(Integer sonucId) { this.sonucId = sonucId; }
    public String getHamVeriDepolamaYolu() { return hamVeriDepolamaYolu; }
    public void setHamVeriDepolamaYolu(String hamVeriDepolamaYolu) { this.hamVeriDepolamaYolu = hamVeriDepolamaYolu; }
    public int getDosyaBoyutuMb() { return dosyaBoyutuMb; }
    public void setDosyaBoyutuMb(int dosyaBoyutuMb) { this.dosyaBoyutuMb = dosyaBoyutuMb; }
}