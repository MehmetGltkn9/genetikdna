package org.example.genetikdna.Entity;

public class Adres {
    private Integer id;
    private Integer kullaniciId; // Foreign Key: Kullanici.id
    private String adresTipi;    // Fatura, Numune Gönderim vb.
    private String ulke;
    private String sehir;
    private String postaKodu;
    private String detayliAdres;

    public Adres() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(Integer kullaniciId) { this.kullaniciId = kullaniciId; }

    public String getAdresTipi() { return adresTipi; }
    public void setAdresTipi(String adresTipi) { this.adresTipi = adresTipi; }

    public String getUlke() { return ulke; }
    public void setUlke(String ulke) { this.ulke = ulke; }

    public String getSehir() { return sehir; }
    public void setSehir(String sehir) { this.sehir = sehir; }

    public String getPostaKodu() { return postaKodu; }
    public void setPostaKodu(String postaKodu) { this.postaKodu = postaKodu; }

    public String getDetayliAdres() { return detayliAdres; }
    public void setDetayliAdres(String detayliAdres) { this.detayliAdres = detayliAdres; }
}