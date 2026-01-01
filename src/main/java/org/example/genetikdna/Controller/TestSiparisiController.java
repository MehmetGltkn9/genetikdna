package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.TestSiparisi;
import org.example.genetikdna.Service.TestSiparisiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test-siparisleri")
public class TestSiparisiController {

    private final TestSiparisiService testSiparisiService;

    public TestSiparisiController(TestSiparisiService testSiparisiService) {
        this.testSiparisiService = testSiparisiService;
    }

    @PostMapping
    public void addTestSiparisi(@RequestBody TestSiparisi siparis) {
        testSiparisiService.addTestSiparisi(siparis);
    }

    @GetMapping
    public List<TestSiparisi> getAllTestSiparisleri() {
        return testSiparisiService.getAllTestSiparisleri();
    }

    @GetMapping("/kullanicilar/{kullaniciId}")
    public List<Map<String, Object>> getTestSiparisleriByKullaniciId(@PathVariable Integer kullaniciId) {
        return testSiparisiService.getTestSiparisleriByKullaniciId(kullaniciId);
    }

    @GetMapping("/{siparisId}/detaylar")
    public List<Map<String, Object>> getTestSiparisiDetaylari(@PathVariable Integer siparisId) {
        return testSiparisiService.getTestSiparisiDetaylari(siparisId);
    }

    @GetMapping("/{id}")
    public TestSiparisi getTestSiparisiById(@PathVariable("id") Integer id) {
        return testSiparisiService.getTestSiparisiById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTestSiparisi(@PathVariable("id") Integer id) {
        testSiparisiService.deleteTestSiparisi(id);
    }

    @PostMapping("/siparis-ve-numune")
    public Map<String, Object> testSiparisiVeNumuneEkle(@RequestBody Map<String, Object> request) {
        return testSiparisiService.testSiparisiVeNumuneEkle(
                ((Number) request.get("kullaniciId")).intValue(),
                ((Number) request.get("paketId")).intValue(),
                ((Number) request.get("toplamTutar")).doubleValue(),
                (String) request.get("barkodId"),
                (String) request.get("numuneTipi")
        );
    }

    @PutMapping("/{siparisId}/odeme")
    public Map<String, Object> testSiparisiOdeme(@PathVariable Integer siparisId, 
                                                  @RequestBody Map<String, Object> request) {
        return testSiparisiService.testSiparisiOdeme(siparisId, (String) request.get("odemeDurumu"));
    }

    @PostMapping("/siparis-ve-numune-transaction")
    public Map<String, Object> siparisVeNumuneEkleWithTransaction(@RequestBody Map<String, Object> request) {
        TestSiparisi siparis = new TestSiparisi();
        siparis.setKullaniciId(((Number) request.get("kullaniciId")).intValue());
        siparis.setPaketId(((Number) request.get("paketId")).intValue());
        siparis.setToplamTutar(((Number) request.get("toplamTutar")).doubleValue());
        siparis.setOdemeDurumu((String) request.get("odemeDurumu"));
        
        String barkodId = (String) request.get("barkodId");
        String numuneTipi = (String) request.get("numuneTipi");
        String durum = (String) request.get("durum");
        
        return testSiparisiService.siparisVeNumuneEkleWithTransaction(siparis, barkodId, numuneTipi, durum);
    }
}

