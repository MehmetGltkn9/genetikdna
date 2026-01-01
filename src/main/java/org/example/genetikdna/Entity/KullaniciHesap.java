package org.example.genetikdna.Entity;

public class KullaniciHesap {
    private Integer id;
    private Integer kullaniciId;
    private String eposta;
    private String parolaHash;
    private java.sql.Timestamp sonGiris;
    private boolean aktifMi;

    public KullaniciHesap() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(Integer kullaniciId) { this.kullaniciId = kullaniciId; }
    public String getEposta() { return eposta; }
    public void setEposta(String eposta) { this.eposta = eposta; }
    public String getParolaHash() { return parolaHash; }
    public void setParolaHash(String parolaHash) { this.parolaHash = parolaHash; }
    public java.sql.Timestamp getSonGiris() { return sonGiris; }
    public void setSonGiris(java.sql.Timestamp sonGiris) { this.sonGiris = sonGiris; }
    public boolean isAktifMi() { return aktifMi; }
    public void setAktifMi(boolean aktifMi) { this.aktifMi = aktifMi; }
}
