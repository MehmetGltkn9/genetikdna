package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.KullaniciGenetikVerisiDAO;
import org.example.genetikdna.Entity.KullaniciGenetikVerisi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KullaniciGenetikVerisiService {

    private final KullaniciGenetikVerisiDAO kullaniciGenetikVerisiDAO;

    public KullaniciGenetikVerisiService(KullaniciGenetikVerisiDAO kullaniciGenetikVerisiDAO) {
        this.kullaniciGenetikVerisiDAO = kullaniciGenetikVerisiDAO;
    }

    public void addKullaniciGenetikVerisi(KullaniciGenetikVerisi veri) {
        kullaniciGenetikVerisiDAO.addKullaniciGenetikVerisi(veri);
    }

    public List<KullaniciGenetikVerisi> getAllKullaniciGenetikVerileri() {
        return kullaniciGenetikVerisiDAO.getAllKullaniciGenetikVerileri();
    }

    public List<Map<String, Object>> getKullaniciGenetikVerileriBySonucId(Integer sonucId) {
        return kullaniciGenetikVerisiDAO.getKullaniciGenetikVerileriBySonucId(sonucId);
    }

    public KullaniciGenetikVerisi getKullaniciGenetikVerisiById(Integer id) {
        return kullaniciGenetikVerisiDAO.getKullaniciGenetikVerisiById(id);
    }

    public void deleteKullaniciGenetikVerisi(Integer id) {
        kullaniciGenetikVerisiDAO.deleteKullaniciGenetikVerisi(id);
    }
}

