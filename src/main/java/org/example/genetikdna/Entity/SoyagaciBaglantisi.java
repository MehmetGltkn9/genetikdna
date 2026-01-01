package org.example.genetikdna.Entity;

public class SoyagaciBaglantisi {
    private Integer id;
    private Integer kullaniciBirId;
    private Integer kullaniciIkiId;
    private String tahminiIliski;
    private double paylasilanDna;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getKullaniciBirId() { return kullaniciBirId; }
    public void setKullaniciBirId(Integer kullaniciBirId) { this.kullaniciBirId = kullaniciBirId; }
    public Integer getKullaniciIkiId() { return kullaniciIkiId; }
    public void setKullaniciIkiId(Integer kullaniciIkiId) { this.kullaniciIkiId = kullaniciIkiId; }
    public String getTahminiIliski() { return tahminiIliski; }
    public void setTahminiIliski(String tahminiIliski) { this.tahminiIliski = tahminiIliski; }
    public double getPaylasilanDna() { return paylasilanDna; }
    public void setPaylasilanDna(double paylasilanDna) { this.paylasilanDna = paylasilanDna; }
}