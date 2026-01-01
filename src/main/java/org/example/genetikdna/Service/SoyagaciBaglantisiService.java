package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.SoyagaciBaglantisiDAO;
import org.example.genetikdna.Entity.SoyagaciBaglantisi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SoyagaciBaglantisiService {

    private final SoyagaciBaglantisiDAO soyagaciBaglantisiDAO;

    public SoyagaciBaglantisiService(SoyagaciBaglantisiDAO soyagaciBaglantisiDAO) {
        this.soyagaciBaglantisiDAO = soyagaciBaglantisiDAO;
    }

    public void addSoyagaciBaglantisi(SoyagaciBaglantisi baglanti) {
        soyagaciBaglantisiDAO.addSoyagaciBaglantisi(baglanti);
    }

    public List<SoyagaciBaglantisi> getAllSoyagaciBaglantilari() {
        return soyagaciBaglantisiDAO.getAllSoyagaciBaglantilari();
    }

    public List<Map<String, Object>> getSoyagaciBaglantilariByKullaniciId(Integer kullaniciId) {
        return soyagaciBaglantisiDAO.getSoyagaciBaglantilariByKullaniciId(kullaniciId);
    }

    public SoyagaciBaglantisi getSoyagaciBaglantisiById(Integer id) {
        return soyagaciBaglantisiDAO.getSoyagaciBaglantisiById(id);
    }

    public void deleteSoyagaciBaglantisi(Integer id) {
        soyagaciBaglantisiDAO.deleteSoyagaciBaglantisi(id);
    }
}

