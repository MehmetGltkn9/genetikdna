package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.HastalikTanimi;
import org.example.genetikdna.Service.HastalikTanimiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hastalik-tanimlari")
public class HastalikTanimiController {

    private final HastalikTanimiService hastalikTanimiService;

    public HastalikTanimiController(HastalikTanimiService hastalikTanimiService) {
        this.hastalikTanimiService = hastalikTanimiService;
    }

    @PostMapping
    public void addHastalikTanimi(@RequestBody HastalikTanimi hastalikTanimi) {
        hastalikTanimiService.addHastalikTanimi(hastalikTanimi);
    }

    @GetMapping
    public List<HastalikTanimi> getAllHastalikTanimlari() {
        return hastalikTanimiService.getAllHastalikTanimlari();
    }

    @GetMapping("/icd-kodu/{icdKodu}")
    public HastalikTanimi getHastalikTanimiByIcdKodu(@PathVariable String icdKodu) {
        return hastalikTanimiService.getHastalikTanimiByIcdKodu(icdKodu);
    }

    @GetMapping("/{id}")
    public HastalikTanimi getHastalikTanimiById(@PathVariable("id") Integer id) {
        return hastalikTanimiService.getHastalikTanimiById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteHastalikTanimi(@PathVariable("id") Integer id) {
        hastalikTanimiService.deleteHastalikTanimi(id);
    }
}

