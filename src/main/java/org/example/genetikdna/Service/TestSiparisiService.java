package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.NumuneDAO;
import org.example.genetikdna.Dao.TestSiparisiDAO;
import org.example.genetikdna.Entity.Numune;
import org.example.genetikdna.Entity.TestSiparisi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestSiparisiService {

    private final TestSiparisiDAO testSiparisiDAO;
    private final NumuneDAO numuneDAO;

    public TestSiparisiService(TestSiparisiDAO testSiparisiDAO, NumuneDAO numuneDAO) {
        this.testSiparisiDAO = testSiparisiDAO;
        this.numuneDAO = numuneDAO;
    }

    public void addTestSiparisi(TestSiparisi siparis) {
        testSiparisiDAO.addTestSiparisi(siparis);
    }

    public List<TestSiparisi> getAllTestSiparisleri() {
        return testSiparisiDAO.getAllTestSiparisleri();
    }

    public List<Map<String, Object>> getTestSiparisleriByKullaniciId(Integer kullaniciId) {
        return testSiparisiDAO.getTestSiparisleriByKullaniciId(kullaniciId);
    }

    public List<Map<String, Object>> getTestSiparisiDetaylari(Integer siparisId) {
        return testSiparisiDAO.getTestSiparisiDetaylari(siparisId);
    }

    public TestSiparisi getTestSiparisiById(Integer id) {
        return testSiparisiDAO.getTestSiparisiById(id);
    }

    public void deleteTestSiparisi(Integer id) {
        testSiparisiDAO.deleteTestSiparisi(id);
    }

    // Stored Procedure: Test Siparişi ve Numune Ekleme
    public Map<String, Object> testSiparisiVeNumuneEkle(Integer kullaniciId, Integer paketId, 
                                                          Double toplamTutar, String barkodId, String numuneTipi) {
        return testSiparisiDAO.testSiparisiVeNumuneEkle(kullaniciId, paketId, toplamTutar, barkodId, numuneTipi);
    }

    // Stored Procedure: Test Siparişi Ödeme
    public Map<String, Object> testSiparisiOdeme(Integer siparisId, String odemeDurumu) {
        return testSiparisiDAO.testSiparisiOdeme(siparisId, odemeDurumu);
    }

    /**
     * Transaction yönetimi ile sipariş ve numune ekleme
     * Hata durumunda tüm işlemler rollback edilir
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> siparisVeNumuneEkleWithTransaction(TestSiparisi siparis, String barkodId, 
                                                                  String numuneTipi, String durum) {
        try {
            // 1. Sipariş ekle ve ID'yi al
            if (siparis.getSiparisTarihi() == null) {
                siparis.setSiparisTarihi(new Timestamp(System.currentTimeMillis()));
            }
            Integer siparisId = testSiparisiDAO.addTestSiparisiAndGetId(siparis);
            
            // 2. Numune ekle
            Numune numune = new Numune();
            numune.setSiparisId(siparisId);
            numune.setBarkodId(barkodId);
            numune.setNumuneTipi(numuneTipi);
            numune.setDurum(durum != null ? durum : "Beklemede");
            numuneDAO.addNumune(numune);
            
            // 3. Sonuç döndür
            Map<String, Object> result = new HashMap<>();
            result.put("siparis_id", siparisId);
            result.put("barkod_id", barkodId);
            result.put("mesaj", "Sipariş ve numune başarıyla oluşturuldu (Transaction ile)");
            
            return result;
        } catch (Exception e) {
            // Hata durumunda transaction otomatik olarak rollback edilir
            throw new RuntimeException("Sipariş ve numune ekleme işlemi başarısız oldu: " + e.getMessage(), e);
        }
    }
}

