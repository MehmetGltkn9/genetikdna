package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.Adres;
import org.example.genetikdna.Service.AdresService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adresler")
public class AdresController {

    private final AdresService adresService;

    public AdresController(AdresService adresService) {
        this.adresService = adresService;
    }

    @PostMapping
    public void addAdres(@RequestBody Adres adres) {
        adresService.addAdres(adres);
    }

    @GetMapping
    public List<Adres> getAllAdresler() {
        return adresService.getAllAdresler();
    }

    @GetMapping("/kullanicilar/{kullaniciId}")
    public List<Map<String, Object>> getAdreslerByKullaniciId(@PathVariable Integer kullaniciId) {
        return adresService.getAdreslerByKullaniciId(kullaniciId);
    }

    @GetMapping("/{id}")
    public Adres getAdresById(@PathVariable("id") Integer id) {
        return adresService.getAdresById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAdres(@PathVariable("id") Integer id) {
        adresService.deleteAdres(id);
    }
}

