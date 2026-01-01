package org.example.genetikdna.Entity;

public class GenetikVaryant {
    private Integer id;
    private String rsId; // rs1234567 gibi
    private String kromozom;
    private int konum;
    private String referansAlel;

    public GenetikVaryant() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getRsId() { return rsId; }
    public void setRsId(String rsId) { this.rsId = rsId; }
    public String getKromozom() { return kromozom; }
    public void setKromozom(String kromozom) { this.kromozom = kromozom; }
    public int getKonum() { return konum; }
    public void setKonum(int konum) { this.konum = konum; }
    public String getReferansAlel() { return referansAlel; }
    public void setReferansAlel(String referansAlel) { this.referansAlel = referansAlel; }
}