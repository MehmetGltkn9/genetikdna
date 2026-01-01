package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.HastalikRiskSkoru;
import org.example.genetikdna.Service.HastalikRiskSkoruService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hastalik-risk-skorlari")
public class HastalikRiskSkoruController {

    private final HastalikRiskSkoruService hastalikRiskSkoruService;

    public HastalikRiskSkoruController(HastalikRiskSkoruService hastalikRiskSkoruService) {
        this.hastalikRiskSkoruService = hastalikRiskSkoruService;
    }

    @PostMapping
    public void addHastalikRiskSkoru(@RequestBody HastalikRiskSkoru riskSkoru) {
        hastalikRiskSkoruService.addHastalikRiskSkoru(riskSkoru);
    }

    @GetMapping
    public List<HastalikRiskSkoru> getAllHastalikRiskSkorlari() {
        return hastalikRiskSkoruService.getAllHastalikRiskSkorlari();
    }

    @GetMapping("/sonuclar/{sonucId}")
    public List<Map<String, Object>> getHastalikRiskSkorlariBySonucId(@PathVariable Integer sonucId) {
        return hastalikRiskSkoruService.getHastalikRiskSkorlariBySonucId(sonucId);
    }

    @GetMapping("/hastaliklar/{hastalikId}")
    public List<Map<String, Object>> getHastalikRiskSkorlariByHastalikId(@PathVariable Integer hastalikId) {
        return hastalikRiskSkoruService.getHastalikRiskSkorlariByHastalikId(hastalikId);
    }

    @GetMapping("/{id}")
    public HastalikRiskSkoru getHastalikRiskSkoruById(@PathVariable("id") Integer id) {
        return hastalikRiskSkoruService.getHastalikRiskSkoruById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteHastalikRiskSkoru(@PathVariable("id") Integer id) {
        hastalikRiskSkoruService.deleteHastalikRiskSkoru(id);
    }

    @PostMapping("/risk-ve-tedavi")
    public Map<String, Object> hastalikRiskVeTedaviEkle(@RequestBody Map<String, Object> request) {
        return hastalikRiskSkoruService.hastalikRiskVeTedaviEkle(
                ((Number) request.get("sonucId")).intValue(),
                ((Number) request.get("hastalikId")).intValue(),
                ((Number) request.get("riskYuzdesi")).doubleValue(),
                (String) request.get("riskSeviyesi"),
                (String) request.get("ilacAdi"),
                (String) request.get("yanitTahmini"),
                (String) request.get("oneriler")
        );
    }
}

