package org.example.genetikdna.Entity;

public class EtnikKokenRaporu {
    private Integer id;
    private Integer sonucId; // FK: GenetikTestSonucu.id
    private String bolgeAdi;
    private double yuzdeOrani;
    private String raporDetayi;

    public EtnikKokenRaporu() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getSonucId() { return sonucId; }
    public void setSonucId(Integer sonucId) { this.sonucId = sonucId; }
    public String getBolgeAdi() { return bolgeAdi; }
    public void setBolgeAdi(String bolgeAdi) { this.bolgeAdi = bolgeAdi; }
    public double getYuzdeOrani() { return yuzdeOrani; }
    public void setYuzdeOrani(double yuzdeOrani) { this.yuzdeOrani = yuzdeOrani; }
    public String getRaporDetayi() { return raporDetayi; }
    public void setRaporDetayi(String raporDetayi) { this.raporDetayi = raporDetayi; }
}
