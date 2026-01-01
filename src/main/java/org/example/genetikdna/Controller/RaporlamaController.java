package org.example.genetikdna.Controller;

import org.example.genetikdna.Service.RaporlamaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/raporlama")
public class RaporlamaController {

    private final RaporlamaService raporlamaService;

    public RaporlamaController(RaporlamaService raporlamaService) {
        this.raporlamaService = raporlamaService;
    }

    /**
     * Kullanıcı detaylı raporu - Fonksiyon ile
     * GET /raporlama/kullanici/{kullaniciId}
     */
    @GetMapping("/kullanici/{kullaniciId}")
    public ResponseEntity<?> getKullaniciDetayliRaporu(@PathVariable Integer kullaniciId) {
        try {
            Map<String, Object> rapor = raporlamaService.getKullaniciDetayliRaporu(kullaniciId);
            return ResponseEntity.ok(rapor);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("hata", true);
            error.put("mesaj", e.getMessage());
            error.put("detay", "Kullanıcı ID: " + kullaniciId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Test sonuçları analiz raporu - Fonksiyon ile
     * GET /raporlama/test-sonuclari
     * GET /raporlama/test-sonuclari?baslangicTarihi=2024-01-01&bitisTarihi=2024-12-31
     */
    @GetMapping("/test-sonuclari")
    public ResponseEntity<?> getTestSonuclariAnalizRaporu(
            @RequestParam(required = false) String baslangicTarihi,
            @RequestParam(required = false) String bitisTarihi) {
        try {
            Map<String, Object> rapor;
            if (baslangicTarihi != null && bitisTarihi != null) {
                rapor = raporlamaService.getTestSonuclariAnalizRaporu(
                        Date.valueOf(baslangicTarihi),
                        Date.valueOf(bitisTarihi)
                );
            } else {
                rapor = raporlamaService.getTestSonuclariAnalizRaporu();
            }
            
            if (rapor == null || rapor.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("hata", true);
                error.put("mesaj", "Test sonuçları için veri bulunamadı.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            
            return ResponseEntity.ok(rapor);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("hata", true);
            error.put("mesaj", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Hastalık risk analiz raporu - Fonksiyon ile
     * GET /raporlama/hastalik-risk
     * GET /raporlama/hastalik-risk?hastalikId=1
     */
    @GetMapping("/hastalik-risk")
    public ResponseEntity<?> getHastalikRiskAnalizRaporu(
            @RequestParam(required = false) Integer hastalikId) {
        try {
            List<Map<String, Object>> rapor;
            if (hastalikId != null) {
                rapor = raporlamaService.getHastalikRiskAnalizRaporu(hastalikId);
            } else {
                rapor = raporlamaService.getHastalikRiskAnalizRaporu();
            }
            
            if (rapor == null || rapor.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("hata", true);
                error.put("mesaj", "Hastalık risk raporu için veri bulunamadı.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            
            return ResponseEntity.ok(rapor);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("hata", true);
            error.put("mesaj", e.getMessage());
            error.put("detay", e.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Test endpoint - Fonksiyonların varlığını kontrol eder
     * GET /raporlama/test
     */
    @GetMapping("/test")
    public ResponseEntity<?> testFonksiyonlar() {
        Map<String, Object> result = new HashMap<>();
        try {
            // Basit bir test - kullanıcı 1 için rapor dene
            Map<String, Object> testRapor = raporlamaService.getKullaniciDetayliRaporu(1);
            result.put("durum", "başarılı");
            result.put("mesaj", "Fonksiyonlar çalışıyor");
            result.put("test_sonucu", testRapor != null && !testRapor.isEmpty());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("durum", "hata");
            result.put("mesaj", "Fonksiyonlar çalışmıyor: " + e.getMessage());
            result.put("hata_detay", e.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}

