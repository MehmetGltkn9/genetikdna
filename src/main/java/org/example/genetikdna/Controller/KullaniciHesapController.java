package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.KullaniciHesap;
import org.example.genetikdna.Service.KullaniciHesapService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kullanici-hesaplari")
public class KullaniciHesapController {

    private final KullaniciHesapService kullaniciHesapService;

    public KullaniciHesapController(KullaniciHesapService kullaniciHesapService) {
        this.kullaniciHesapService = kullaniciHesapService;
    }

    @PostMapping
    public void addKullaniciHesap(@RequestBody KullaniciHesap hesap) {
        kullaniciHesapService.addKullaniciHesap(hesap);
    }

    @GetMapping
    public List<KullaniciHesap> getAllKullaniciHesaplari() {
        return kullaniciHesapService.getAllKullaniciHesaplari();
    }

    @GetMapping("/kullanicilar/{kullaniciId}")
    public List<Map<String, Object>> getKullaniciHesapByKullaniciId(@PathVariable Integer kullaniciId) {
        return kullaniciHesapService.getKullaniciHesapByKullaniciId(kullaniciId);
    }

    @GetMapping("/eposta/{eposta}")
    public KullaniciHesap getKullaniciHesapByEposta(@PathVariable String eposta) {
        return kullaniciHesapService.getKullaniciHesapByEposta(eposta);
    }

    @GetMapping("/{id}")
    public KullaniciHesap getKullaniciHesapById(@PathVariable("id") Integer id) {
        return kullaniciHesapService.getKullaniciHesapById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteKullaniciHesap(@PathVariable("id") Integer id) {
        kullaniciHesapService.deleteKullaniciHesap(id);
    }
}

