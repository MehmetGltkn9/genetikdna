package org.example.genetikdna.Entity;

public class HastalikTanimi {
    private Integer id;
    private String hastalikAdi;
    private String icdKodu;
    private String bilimselTanim;

    public HastalikTanimi() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getHastalikAdi() { return hastalikAdi; }
    public void setHastalikAdi(String hastalikAdi) { this.hastalikAdi = hastalikAdi; }
    public String getIcdKodu() { return icdKodu; }
    public void setIcdKodu(String icdKodu) { this.icdKodu = icdKodu; }
    public String getBilimselTanim() { return bilimselTanim; }
    public void setBilimselTanim(String bilimselTanim) { this.bilimselTanim = bilimselTanim; }
}