package org.example.genetikdna.Entity;

public class TedaviyeYanit {
    private Integer id;
    private Integer sonucId; // FK: GenetikTestSonucu.id
    private String ilacAdi;
    private String yanitTahmini;
    private String oneriler;

    public TedaviyeYanit() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getSonucId() { return sonucId; }
    public void setSonucId(Integer sonucId) { this.sonucId = sonucId; }
    public String getIlacAdi() { return ilacAdi; }
    public void setIlacAdi(String ilacAdi) { this.ilacAdi = ilacAdi; }
    public String getYanitTahmini() { return yanitTahmini; }
    public void setYanitTahmini(String yanitTahmini) { this.yanitTahmini = yanitTahmini; }
    public String getOneriler() { return oneriler; }
    public void setOneriler(String oneriler) { this.oneriler = oneriler; }
}