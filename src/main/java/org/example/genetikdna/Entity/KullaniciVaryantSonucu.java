package org.example.genetikdna.Entity;

public class KullaniciVaryantSonucu {
    private long id;
    private Integer sonucId;
    private Integer varyantId;
    private String tespitEdilenAlel;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Integer getSonucId() { return sonucId; }
    public void setSonucId(Integer sonucId) { this.sonucId = sonucId; }
    public Integer getVaryantId() { return varyantId; }
    public void setVaryantId(Integer varyantId) { this.varyantId = varyantId; }
    public String getTespitEdilenAlel() { return tespitEdilenAlel; }
    public void setTespitEdilenAlel(String tespitEdilenAlel) { this.tespitEdilenAlel = tespitEdilenAlel; }
}