package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.LaboratuvarAnalizi;
import org.example.genetikdna.Service.LaboratuvarAnaliziService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/laboratuvar-analizleri")
public class LaboratuvarAnaliziController {

    private final LaboratuvarAnaliziService laboratuvarAnaliziService;

    public LaboratuvarAnaliziController(LaboratuvarAnaliziService laboratuvarAnaliziService) {
        this.laboratuvarAnaliziService = laboratuvarAnaliziService;
    }

    @PostMapping
    public void addLaboratuvarAnalizi(@RequestBody LaboratuvarAnalizi analiz) {
        laboratuvarAnaliziService.addLaboratuvarAnalizi(analiz);
    }

    @GetMapping
    public List<LaboratuvarAnalizi> getAllLaboratuvarAnalizleri() {
        return laboratuvarAnaliziService.getAllLaboratuvarAnalizleri();
    }

    @GetMapping("/numuneler/{numuneId}")
    public List<Map<String, Object>> getLaboratuvarAnalizleriByNumuneId(@PathVariable Integer numuneId) {
        return laboratuvarAnaliziService.getLaboratuvarAnalizleriByNumuneId(numuneId);
    }

    @GetMapping("/teknisyenler/{teknisyenAdi}")
    public List<Map<String, Object>> getLaboratuvarAnalizleriByTeknisyen(@PathVariable String teknisyenAdi) {
        return laboratuvarAnaliziService.getLaboratuvarAnalizleriByTeknisyen(teknisyenAdi);
    }

    @GetMapping("/{id}")
    public LaboratuvarAnalizi getLaboratuvarAnaliziById(@PathVariable("id") Integer id) {
        return laboratuvarAnaliziService.getLaboratuvarAnaliziById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteLaboratuvarAnalizi(@PathVariable("id") Integer id) {
        laboratuvarAnaliziService.deleteLaboratuvarAnalizi(id);
    }

    @PostMapping("/tamamla")
    public Map<String, Object> laboratuvarAnaliziTamamla(@RequestBody Map<String, Object> request) {
        return laboratuvarAnaliziService.laboratuvarAnaliziTamamla(
                ((Number) request.get("numuneId")).intValue(),
                java.sql.Timestamp.valueOf((String) request.get("analizBitis")),
                (String) request.get("kaliteKontrolSonucu"),
                ((Number) request.get("kullaniciId")).intValue(),
                (String) request.get("veriSurumu")
        );
    }
}

