package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.GenetikTestSonucuDAO;
import org.example.genetikdna.Dao.KullaniciGenetikVerisiDAO;
import org.example.genetikdna.Entity.GenetikTestSonucu;
import org.example.genetikdna.Entity.KullaniciGenetikVerisi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenetikTestSonucuService {

    private final GenetikTestSonucuDAO genetikTestSonucuDAO;
    private final KullaniciGenetikVerisiDAO kullaniciGenetikVerisiDAO;

    public GenetikTestSonucuService(GenetikTestSonucuDAO genetikTestSonucuDAO, 
                                    KullaniciGenetikVerisiDAO kullaniciGenetikVerisiDAO) {
        this.genetikTestSonucuDAO = genetikTestSonucuDAO;
        this.kullaniciGenetikVerisiDAO = kullaniciGenetikVerisiDAO;
    }

    public void addGenetikTestSonucu(GenetikTestSonucu sonuc) {
        genetikTestSonucuDAO.addGenetikTestSonucu(sonuc);
    }

    public List<GenetikTestSonucu> getAllGenetikTestSonuclari() {
        return genetikTestSonucuDAO.getAllGenetikTestSonuclari();
    }

    public List<Map<String, Object>> getGenetikTestSonuclariByKullaniciId(Integer kullaniciId) {
        return genetikTestSonucuDAO.getGenetikTestSonuclariByKullaniciId(kullaniciId);
    }

    public List<Map<String, Object>> getGenetikTestSonuclariByAnalizId(Integer analizId) {
        return genetikTestSonucuDAO.getGenetikTestSonuclariByAnalizId(analizId);
    }

    public GenetikTestSonucu getGenetikTestSonucuById(Integer id) {
        return genetikTestSonucuDAO.getGenetikTestSonucuById(id);
    }

    public void deleteGenetikTestSonucu(Integer id) {
        genetikTestSonucuDAO.deleteGenetikTestSonucu(id);
    }

    // Stored Procedure: Genetik Test Sonucu ve Veri Ekleme
    public Map<String, Object> genetikTestSonucuVeVeriEkle(Integer kullaniciId, Integer analizId, 
                                                             String veriSurumu, String hamVeriYolu, Integer dosyaBoyutuMb) {
        return genetikTestSonucuDAO.genetikTestSonucuVeVeriEkle(kullaniciId, analizId, veriSurumu, hamVeriYolu, dosyaBoyutuMb);
    }

    /**
     * Transaction yönetimi ile test sonucu ve veri ekleme
     * Hata durumunda tüm işlemler rollback edilir
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> testSonucuVeVeriEkleWithTransaction(GenetikTestSonucu sonuc, 
                                                                   String hamVeriYolu, Integer dosyaBoyutuMb) {
        try {
            // 1. Test sonucu ekle ve ID'yi al
            if (sonuc.getYayimTarihi() == null) {
                sonuc.setYayimTarihi(new Timestamp(System.currentTimeMillis()));
            }
            Integer sonucId = genetikTestSonucuDAO.addGenetikTestSonucuAndGetId(sonuc);
            
            // 2. Genetik veri ekle
            KullaniciGenetikVerisi veri = new KullaniciGenetikVerisi();
            veri.setSonucId(sonucId);
            veri.setHamVeriDepolamaYolu(hamVeriYolu);
            veri.setDosyaBoyutuMb(dosyaBoyutuMb);
            kullaniciGenetikVerisiDAO.addKullaniciGenetikVerisi(veri);
            
            // 3. Sonuç döndür
            Map<String, Object> result = new HashMap<>();
            result.put("sonuc_id", sonucId);
            result.put("ham_veri_yolu", hamVeriYolu);
            result.put("mesaj", "Test sonucu ve veri başarıyla oluşturuldu (Transaction ile)");
            
            return result;
        } catch (Exception e) {
            // Hata durumunda transaction otomatik olarak rollback edilir
            throw new RuntimeException("Test sonucu ve veri ekleme işlemi başarısız oldu: " + e.getMessage(), e);
        }
    }
}

