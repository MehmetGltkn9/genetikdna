package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.Numune;
import org.example.genetikdna.Service.NumuneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/numuneler")
public class NumuneController {

    private final NumuneService numuneService;

    public NumuneController(NumuneService numuneService) {
        this.numuneService = numuneService;
    }

    @PostMapping
    public void addNumune(@RequestBody Numune numune) {
        numuneService.addNumune(numune);
    }

    @GetMapping
    public List<Numune> getAllNumuneler() {
        return numuneService.getAllNumuneler();
    }

    @GetMapping("/siparisler/{siparisId}")
    public List<Map<String, Object>> getNumunelerBySiparisId(@PathVariable Integer siparisId) {
        return numuneService.getNumunelerBySiparisId(siparisId);
    }

    @GetMapping("/{numuneId}/analizler")
    public List<Map<String, Object>> getNumuneAnalizleri(@PathVariable Integer numuneId) {
        return numuneService.getNumuneAnalizleri(numuneId);
    }

    @GetMapping("/barkod/{barkodId}")
    public Numune getNumuneByBarkodId(@PathVariable String barkodId) {
        return numuneService.getNumuneByBarkodId(barkodId);
    }

    @GetMapping("/{id}")
    public Numune getNumuneById(@PathVariable("id") Integer id) {
        return numuneService.getNumuneById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteNumune(@PathVariable("id") Integer id) {
        numuneService.deleteNumune(id);
    }
}

