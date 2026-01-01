package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.KullaniciVaryantSonucu;
import org.example.genetikdna.Service.KullaniciVaryantSonucuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kullanici-varyant-sonuclari")
public class KullaniciVaryantSonucuController {

    private final KullaniciVaryantSonucuService kullaniciVaryantSonucuService;

    public KullaniciVaryantSonucuController(KullaniciVaryantSonucuService kullaniciVaryantSonucuService) {
        this.kullaniciVaryantSonucuService = kullaniciVaryantSonucuService;
    }

    @PostMapping
    public void addKullaniciVaryantSonucu(@RequestBody KullaniciVaryantSonucu sonuc) {
        kullaniciVaryantSonucuService.addKullaniciVaryantSonucu(sonuc);
    }

    @GetMapping
    public List<KullaniciVaryantSonucu> getAllKullaniciVaryantSonuclari() {
        return kullaniciVaryantSonucuService.getAllKullaniciVaryantSonuclari();
    }

    @GetMapping("/sonuclar/{sonucId}")
    public List<Map<String, Object>> getKullaniciVaryantSonuclariBySonucId(@PathVariable Integer sonucId) {
        return kullaniciVaryantSonucuService.getKullaniciVaryantSonuclariBySonucId(sonucId);
    }

    @GetMapping("/varyantlar/{varyantId}")
    public List<Map<String, Object>> getKullaniciVaryantSonuclariByVaryantId(@PathVariable Integer varyantId) {
        return kullaniciVaryantSonucuService.getKullaniciVaryantSonuclariByVaryantId(varyantId);
    }

    @GetMapping("/{id}")
    public KullaniciVaryantSonucu getKullaniciVaryantSonucuById(@PathVariable("id") Long id) {
        return kullaniciVaryantSonucuService.getKullaniciVaryantSonucuById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteKullaniciVaryantSonucu(@PathVariable("id") Long id) {
        kullaniciVaryantSonucuService.deleteKullaniciVaryantSonucu(id);
    }

    @PostMapping("/toplu-ekle")
    public Map<String, Object> varyantSonuclariTopluEkle(@RequestBody Map<String, Object> request) {
        return kullaniciVaryantSonucuService.varyantSonuclariTopluEkle(
                ((Number) request.get("sonucId")).intValue(),
                (String) request.get("varyantVerileri")
        );
    }
}

