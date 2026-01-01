package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.RaporlamaDAO;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
public class RaporlamaService {

    private final RaporlamaDAO raporlamaDAO;

    public RaporlamaService(RaporlamaDAO raporlamaDAO) {
        this.raporlamaDAO = raporlamaDAO;
    }

    /**
     * Kullanıcı detaylı raporu
     * @param kullaniciId Kullanıcı ID
     * @return Kullanıcının detaylı istatistikleri
     */
    public Map<String, Object> getKullaniciDetayliRaporu(Integer kullaniciId) {
        return raporlamaDAO.getKullaniciDetayliRaporu(kullaniciId);
    }

    /**
     * Test sonuçları analiz raporu
     * @param baslangicTarihi Başlangıç tarihi (opsiyonel)
     * @param bitisTarihi Bitiş tarihi (opsiyonel)
     * @return Test sonuçları analiz raporu
     */
    public Map<String, Object> getTestSonuclariAnalizRaporu(Date baslangicTarihi, Date bitisTarihi) {
        return raporlamaDAO.getTestSonuclariAnalizRaporu(baslangicTarihi, bitisTarihi);
    }

    /**
     * Tüm test sonuçları analiz raporu
     */
    public Map<String, Object> getTestSonuclariAnalizRaporu() {
        return raporlamaDAO.getTestSonuclariAnalizRaporu();
    }

    /**
     * Hastalık risk analiz raporu
     * @param hastalikId Hastalık ID (opsiyonel)
     * @return Hastalık risk analiz raporu
     */
    public List<Map<String, Object>> getHastalikRiskAnalizRaporu(Integer hastalikId) {
        return raporlamaDAO.getHastalikRiskAnalizRaporu(hastalikId);
    }

    /**
     * Tüm hastalıklar için risk analiz raporu
     */
    public List<Map<String, Object>> getHastalikRiskAnalizRaporu() {
        return raporlamaDAO.getHastalikRiskAnalizRaporu();
    }
}

