package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.SoyagaciBaglantisi;
import org.example.genetikdna.Service.SoyagaciBaglantisiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/soyagaci-baglantilari")
public class SoyagaciBaglantisiController {

    private final SoyagaciBaglantisiService soyagaciBaglantisiService;

    public SoyagaciBaglantisiController(SoyagaciBaglantisiService soyagaciBaglantisiService) {
        this.soyagaciBaglantisiService = soyagaciBaglantisiService;
    }

    @PostMapping
    public void addSoyagaciBaglantisi(@RequestBody SoyagaciBaglantisi baglanti) {
        soyagaciBaglantisiService.addSoyagaciBaglantisi(baglanti);
    }

    @GetMapping
    public List<SoyagaciBaglantisi> getAllSoyagaciBaglantilari() {
        return soyagaciBaglantisiService.getAllSoyagaciBaglantilari();
    }

    @GetMapping("/kullanicilar/{kullaniciId}")
    public List<Map<String, Object>> getSoyagaciBaglantilariByKullaniciId(@PathVariable Integer kullaniciId) {
        return soyagaciBaglantisiService.getSoyagaciBaglantilariByKullaniciId(kullaniciId);
    }

    @GetMapping("/{id}")
    public SoyagaciBaglantisi getSoyagaciBaglantisiById(@PathVariable("id") Integer id) {
        return soyagaciBaglantisiService.getSoyagaciBaglantisiById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSoyagaciBaglantisi(@PathVariable("id") Integer id) {
        soyagaciBaglantisiService.deleteSoyagaciBaglantisi(id);
    }
}

