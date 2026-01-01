package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.KullaniciGenetikVerisi;
import org.example.genetikdna.Service.KullaniciGenetikVerisiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kullanici-genetik-verileri")
public class KullaniciGenetikVerisiController {

    private final KullaniciGenetikVerisiService kullaniciGenetikVerisiService;

    public KullaniciGenetikVerisiController(KullaniciGenetikVerisiService kullaniciGenetikVerisiService) {
        this.kullaniciGenetikVerisiService = kullaniciGenetikVerisiService;
    }

    @PostMapping
    public void addKullaniciGenetikVerisi(@RequestBody KullaniciGenetikVerisi veri) {
        kullaniciGenetikVerisiService.addKullaniciGenetikVerisi(veri);
    }

    @GetMapping
    public List<KullaniciGenetikVerisi> getAllKullaniciGenetikVerileri() {
        return kullaniciGenetikVerisiService.getAllKullaniciGenetikVerileri();
    }

    @GetMapping("/sonuclar/{sonucId}")
    public List<Map<String, Object>> getKullaniciGenetikVerileriBySonucId(@PathVariable Integer sonucId) {
        return kullaniciGenetikVerisiService.getKullaniciGenetikVerileriBySonucId(sonucId);
    }

    @GetMapping("/{id}")
    public KullaniciGenetikVerisi getKullaniciGenetikVerisiById(@PathVariable("id") Integer id) {
        return kullaniciGenetikVerisiService.getKullaniciGenetikVerisiById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteKullaniciGenetikVerisi(@PathVariable("id") Integer id) {
        kullaniciGenetikVerisiService.deleteKullaniciGenetikVerisi(id);
    }
}

