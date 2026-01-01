package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.EtnikKokenRaporu;
import org.example.genetikdna.Service.EtnikKokenRaporuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/etnik-koken-raporlari")
public class EtnikKokenRaporuController {

    private final EtnikKokenRaporuService etnikKokenRaporuService;

    public EtnikKokenRaporuController(EtnikKokenRaporuService etnikKokenRaporuService) {
        this.etnikKokenRaporuService = etnikKokenRaporuService;
    }

    @PostMapping
    public void addEtnikKokenRaporu(@RequestBody EtnikKokenRaporu rapor) {
        etnikKokenRaporuService.addEtnikKokenRaporu(rapor);
    }

    @GetMapping
    public List<EtnikKokenRaporu> getAllEtnikKokenRaporlari() {
        return etnikKokenRaporuService.getAllEtnikKokenRaporlari();
    }

    @GetMapping("/sonuclar/{sonucId}")
    public List<Map<String, Object>> getEtnikKokenRaporlariBySonucId(@PathVariable Integer sonucId) {
        return etnikKokenRaporuService.getEtnikKokenRaporlariBySonucId(sonucId);
    }

    @GetMapping("/{id}")
    public EtnikKokenRaporu getEtnikKokenRaporuById(@PathVariable("id") Integer id) {
        return etnikKokenRaporuService.getEtnikKokenRaporuById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEtnikKokenRaporu(@PathVariable("id") Integer id) {
        etnikKokenRaporuService.deleteEtnikKokenRaporu(id);
    }
}

