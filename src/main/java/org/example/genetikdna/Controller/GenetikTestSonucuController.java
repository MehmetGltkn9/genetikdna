package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.GenetikTestSonucu;
import org.example.genetikdna.Service.GenetikTestSonucuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/genetik-test-sonuclari")
public class GenetikTestSonucuController {

    private final GenetikTestSonucuService genetikTestSonucuService;

    public GenetikTestSonucuController(GenetikTestSonucuService genetikTestSonucuService) {
        this.genetikTestSonucuService = genetikTestSonucuService;
    }

    @PostMapping
    public void addGenetikTestSonucu(@RequestBody GenetikTestSonucu sonuc) {
        genetikTestSonucuService.addGenetikTestSonucu(sonuc);
    }

    @GetMapping
    public List<GenetikTestSonucu> getAllGenetikTestSonuclari() {
        return genetikTestSonucuService.getAllGenetikTestSonuclari();
    }

    @GetMapping("/kullanicilar/{kullaniciId}")
    public List<Map<String, Object>> getGenetikTestSonuclariByKullaniciId(@PathVariable Integer kullaniciId) {
        return genetikTestSonucuService.getGenetikTestSonuclariByKullaniciId(kullaniciId);
    }

    @GetMapping("/analizler/{analizId}")
    public List<Map<String, Object>> getGenetikTestSonuclariByAnalizId(@PathVariable Integer analizId) {
        return genetikTestSonucuService.getGenetikTestSonuclariByAnalizId(analizId);
    }

    @GetMapping("/{id}")
    public GenetikTestSonucu getGenetikTestSonucuById(@PathVariable("id") Integer id) {
        return genetikTestSonucuService.getGenetikTestSonucuById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteGenetikTestSonucu(@PathVariable("id") Integer id) {
        genetikTestSonucuService.deleteGenetikTestSonucu(id);
    }

    @PostMapping("/test-sonucu-ve-veri")
    public Map<String, Object> genetikTestSonucuVeVeriEkle(@RequestBody Map<String, Object> request) {
        return genetikTestSonucuService.genetikTestSonucuVeVeriEkle(
                ((Number) request.get("kullaniciId")).intValue(),
                ((Number) request.get("analizId")).intValue(),
                (String) request.get("veriSurumu"),
                (String) request.get("hamVeriYolu"),
                ((Number) request.get("dosyaBoyutuMb")).intValue()
        );
    }

    @PostMapping("/test-sonucu-ve-veri-transaction")
    public Map<String, Object> testSonucuVeVeriEkleWithTransaction(@RequestBody Map<String, Object> request) {
        GenetikTestSonucu sonuc = new GenetikTestSonucu();
        sonuc.setKullaniciId(((Number) request.get("kullaniciId")).intValue());
        sonuc.setAnalizId(((Number) request.get("analizId")).intValue());
        sonuc.setVeriSurumu((String) request.get("veriSurumu"));
        
        String hamVeriYolu = (String) request.get("hamVeriYolu");
        Integer dosyaBoyutuMb = ((Number) request.get("dosyaBoyutuMb")).intValue();
        
        return genetikTestSonucuService.testSonucuVeVeriEkleWithTransaction(sonuc, hamVeriYolu, dosyaBoyutuMb);
    }
}

