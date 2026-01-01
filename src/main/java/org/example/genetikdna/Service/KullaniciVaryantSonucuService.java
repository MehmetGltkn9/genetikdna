package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.KullaniciVaryantSonucuDAO;
import org.example.genetikdna.Entity.KullaniciVaryantSonucu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KullaniciVaryantSonucuService {

    private final KullaniciVaryantSonucuDAO kullaniciVaryantSonucuDAO;

    public KullaniciVaryantSonucuService(KullaniciVaryantSonucuDAO kullaniciVaryantSonucuDAO) {
        this.kullaniciVaryantSonucuDAO = kullaniciVaryantSonucuDAO;
    }

    public void addKullaniciVaryantSonucu(KullaniciVaryantSonucu sonuc) {
        kullaniciVaryantSonucuDAO.addKullaniciVaryantSonucu(sonuc);
    }

    public List<KullaniciVaryantSonucu> getAllKullaniciVaryantSonuclari() {
        return kullaniciVaryantSonucuDAO.getAllKullaniciVaryantSonuclari();
    }

    public List<Map<String, Object>> getKullaniciVaryantSonuclariBySonucId(Integer sonucId) {
        return kullaniciVaryantSonucuDAO.getKullaniciVaryantSonuclariBySonucId(sonucId);
    }

    public List<Map<String, Object>> getKullaniciVaryantSonuclariByVaryantId(Integer varyantId) {
        return kullaniciVaryantSonucuDAO.getKullaniciVaryantSonuclariByVaryantId(varyantId);
    }

    public KullaniciVaryantSonucu getKullaniciVaryantSonucuById(Long id) {
        return kullaniciVaryantSonucuDAO.getKullaniciVaryantSonucuById(id);
    }

    public void deleteKullaniciVaryantSonucu(Long id) {
        kullaniciVaryantSonucuDAO.deleteKullaniciVaryantSonucu(id);
    }

    // Stored Procedure: Varyant Sonuçlarını Toplu Ekleme
    public Map<String, Object> varyantSonuclariTopluEkle(Integer sonucId, String varyantVerileriJson) {
        return kullaniciVaryantSonucuDAO.varyantSonuclariTopluEkle(sonucId, varyantVerileriJson);
    }
}

