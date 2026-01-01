package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.Kullanici;
import org.example.genetikdna.Service.KullaniciService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kullanicilar")
public class KullaniciController {

    private final KullaniciService kullaniciService;

    public KullaniciController(KullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    @PostMapping
    public void addKullanici(@RequestBody Kullanici kullanici) {
        kullaniciService.addKullanici(kullanici);
    }

    @GetMapping
    public List<Kullanici> getAllKullanicilar() {
        return kullaniciService.getAllKullanicilar();
    }

    @GetMapping("/{kullaniciId}/detaylar")
    public List<Map<String, Object>> getKullaniciDetaylari(@PathVariable Integer kullaniciId) {
        return kullaniciService.getKullaniciDetaylari(kullaniciId);
    }

    @GetMapping("/{id}")
    public Kullanici getKullaniciById(@PathVariable("id") Integer id) {
        return kullaniciService.getKullaniciById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteKullanici(@PathVariable("id") Integer id) {
        kullaniciService.deleteKullanici(id);
    }

    @PostMapping("/kullanici-ve-hesap")
    public Map<String, Object> kullaniciVeHesapEkle(@RequestBody Map<String, Object> request) {
        return kullaniciService.kullaniciVeHesapEkle(
                (String) request.get("ad"),
                (String) request.get("soyad"),
                java.sql.Date.valueOf((String) request.get("dogumTarihi")),
                (String) request.get("cinsiyet"),
                (String) request.get("eposta"),
                (String) request.get("parolaHash")
        );
    }

    @PutMapping("/{id}/guncelle")
    public Map<String, Object> kullaniciGuncelle(@PathVariable Integer id, 
                                                    @RequestBody Map<String, Object> request,
                                                    @RequestParam Integer kullaniciId) {
        return kullaniciService.kullaniciGuncelle(
                id,
                (String) request.get("ad"),
                (String) request.get("soyad"),
                java.sql.Date.valueOf((String) request.get("dogumTarihi")),
                (String) request.get("cinsiyet"),
                kullaniciId
        );
    }

    @PostMapping("/kullanici-ve-hesap-transaction")
    public Map<String, Object> kullaniciVeHesapEkleWithTransaction(@RequestBody Map<String, Object> request) {
        Kullanici kullanici = new Kullanici();
        kullanici.setAd((String) request.get("ad"));
        kullanici.setSoyad((String) request.get("soyad"));
        kullanici.setDogumTarihi(java.sql.Date.valueOf((String) request.get("dogumTarihi")));
        kullanici.setCinsiyet((String) request.get("cinsiyet"));
        
        String eposta = (String) request.get("eposta");
        String parolaHash = (String) request.get("parolaHash");
        Boolean aktifMi = request.get("aktifMi") != null ? (Boolean) request.get("aktifMi") : true;
        
        return kullaniciService.kullaniciVeHesapEkleWithTransaction(kullanici, eposta, parolaHash, aktifMi);
    }
}

