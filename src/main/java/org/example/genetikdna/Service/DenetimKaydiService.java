package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.DenetimKaydiDAO;
import org.example.genetikdna.Entity.DenetimKaydi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DenetimKaydiService {

    private final DenetimKaydiDAO denetimKaydiDAO;

    public DenetimKaydiService(DenetimKaydiDAO denetimKaydiDAO) {
        this.denetimKaydiDAO = denetimKaydiDAO;
    }

    public void addDenetimKaydi(DenetimKaydi denetimKaydi) {
        denetimKaydiDAO.addDenetimKaydi(denetimKaydi);
    }

    public List<DenetimKaydi> getAllDenetimKayitlari() {
        return denetimKaydiDAO.getAllDenetimKayitlari();
    }

    public List<Map<String, Object>> getDenetimKayitlariByKullaniciId(Integer kullaniciId) {
        return denetimKaydiDAO.getDenetimKayitlariByKullaniciId(kullaniciId);
    }

    public List<Map<String, Object>> getDenetimKayitlariByTablo(String tabloAdi) {
        return denetimKaydiDAO.getDenetimKayitlariByTablo(tabloAdi);
    }

    public DenetimKaydi getDenetimKaydiById(Long id) {
        return denetimKaydiDAO.getDenetimKaydiById(id);
    }

    public void deleteDenetimKaydi(Long id) {
        denetimKaydiDAO.deleteDenetimKaydi(id);
    }
}

