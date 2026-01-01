package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.GenetikDanismanlik;
import org.example.genetikdna.Service.GenetikDanismanlikService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/genetik-danismanlik")
public class GenetikDanismanlikController {

    private final GenetikDanismanlikService genetikDanismanlikService;

    public GenetikDanismanlikController(GenetikDanismanlikService genetikDanismanlikService) {
        this.genetikDanismanlikService = genetikDanismanlikService;
    }

    @PostMapping
    public void addGenetikDanismanlik(@RequestBody GenetikDanismanlik danismanlik) {
        genetikDanismanlikService.addGenetikDanismanlik(danismanlik);
    }

    @GetMapping
    public List<GenetikDanismanlik> getAllGenetikDanismanliklar() {
        return genetikDanismanlikService.getAllGenetikDanismanliklar();
    }

    @GetMapping("/kullanicilar/{kullaniciId}")
    public List<Map<String, Object>> getGenetikDanismanliklarByKullaniciId(@PathVariable Integer kullaniciId) {
        return genetikDanismanlikService.getGenetikDanismanliklarByKullaniciId(kullaniciId);
    }

    @GetMapping("/{id}")
    public GenetikDanismanlik getGenetikDanismanlikById(@PathVariable("id") Integer id) {
        return genetikDanismanlikService.getGenetikDanismanlikById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteGenetikDanismanlik(@PathVariable("id") Integer id) {
        genetikDanismanlikService.deleteGenetikDanismanlik(id);
    }
}

