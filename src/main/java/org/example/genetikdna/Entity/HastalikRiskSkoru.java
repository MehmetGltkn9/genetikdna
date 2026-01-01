package org.example.genetikdna.Entity;

public class HastalikRiskSkoru {
    private Integer id;
    private Integer sonucId;
    private Integer hastalikId;
    private double riskYuzdesi;
    private String riskSeviyesi;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getSonucId() { return sonucId; }
    public void setSonucId(Integer sonucId) { this.sonucId = sonucId; }
    public Integer getHastalikId() { return hastalikId; }
    public void setHastalikId(Integer hastalikId) { this.hastalikId = hastalikId; }
    public double getRiskYuzdesi() { return riskYuzdesi; }
    public void setRiskYuzdesi(double riskYuzdesi) { this.riskYuzdesi = riskYuzdesi; }
    public String getRiskSeviyesi() { return riskSeviyesi; }
    public void setRiskSeviyesi(String riskSeviyesi) { this.riskSeviyesi = riskSeviyesi; }
}