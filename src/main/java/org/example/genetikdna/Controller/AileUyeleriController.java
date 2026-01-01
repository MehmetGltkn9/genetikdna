package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.AileUyeleri;
import org.example.genetikdna.Service.AileUyeleriService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aile-uyeleri")
public class AileUyeleriController {

    private final AileUyeleriService aileUyeleriService;

    public AileUyeleriController(AileUyeleriService aileUyeleriService) {
        this.aileUyeleriService = aileUyeleriService;
    }

    @PostMapping
    public void addAileUyesi(@RequestBody AileUyeleri aileUyesi) {
        aileUyeleriService.addAileUyesi(aileUyesi);
    }

    @GetMapping
    public List<AileUyeleri> getAllAileUyeleri() {
        return aileUyeleriService.getAllAileUyeleri();
    }

    @GetMapping("/kullanicilar/{kullaniciId}")
    public List<Map<String, Object>> getAileUyeleriByKullaniciId(@PathVariable Integer kullaniciId) {
        return aileUyeleriService.getAileUyeleriByKullaniciId(kullaniciId);
    }

    @GetMapping("/{id}")
    public AileUyeleri getAileUyesiById(@PathVariable("id") Integer id) {
        return aileUyeleriService.getAileUyesiById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAileUyesi(@PathVariable("id") Integer id) {
        aileUyeleriService.deleteAileUyesi(id);
    }
}

