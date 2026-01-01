package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.DenetimKaydi;
import org.example.genetikdna.Service.DenetimKaydiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/denetim-kayitlari")
public class DenetimKaydiController {

    private final DenetimKaydiService denetimKaydiService;

    public DenetimKaydiController(DenetimKaydiService denetimKaydiService) {
        this.denetimKaydiService = denetimKaydiService;
    }

    @PostMapping
    public void addDenetimKaydi(@RequestBody DenetimKaydi denetimKaydi) {
        denetimKaydiService.addDenetimKaydi(denetimKaydi);
    }

    @GetMapping
    public List<DenetimKaydi> getAllDenetimKayitlari() {
        return denetimKaydiService.getAllDenetimKayitlari();
    }

    @GetMapping("/kullanicilar/{kullaniciId}")
    public List<Map<String, Object>> getDenetimKayitlariByKullaniciId(@PathVariable Integer kullaniciId) {
        return denetimKaydiService.getDenetimKayitlariByKullaniciId(kullaniciId);
    }

    @GetMapping("/tablolar/{tabloAdi}")
    public List<Map<String, Object>> getDenetimKayitlariByTablo(@PathVariable String tabloAdi) {
        return denetimKaydiService.getDenetimKayitlariByTablo(tabloAdi);
    }

    @GetMapping("/{id}")
    public DenetimKaydi getDenetimKaydiById(@PathVariable("id") Long id) {
        return denetimKaydiService.getDenetimKaydiById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteDenetimKaydi(@PathVariable("id") Long id) {
        denetimKaydiService.deleteDenetimKaydi(id);
    }
}

