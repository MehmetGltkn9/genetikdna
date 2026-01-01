package org.example.genetikdna.Entity;

public class Numune {
    private Integer id;
    private Integer siparisId;
    private String barkodId;
    private String numuneTipi;
    private java.sql.Date laboratuvaraVaris;
    private String durum;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getSiparisId() { return siparisId; }
    public void setSiparisId(Integer siparisId) { this.siparisId = siparisId; }
    public String getBarkodId() { return barkodId; }
    public void setBarkodId(String barkodId) { this.barkodId = barkodId; }
    public String getNumuneTipi() { return numuneTipi; }
    public void setNumuneTipi(String numuneTipi) { this.numuneTipi = numuneTipi; }
    public java.sql.Date getLaboratuvaraVaris() { return laboratuvaraVaris; }
    public void setLaboratuvaraVaris(java.sql.Date laboratuvaraVaris) { this.laboratuvaraVaris = laboratuvaraVaris; }
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
}
