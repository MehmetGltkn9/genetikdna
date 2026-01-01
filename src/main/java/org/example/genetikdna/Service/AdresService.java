package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.AdresDAO;
import org.example.genetikdna.Entity.Adres;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdresService {

    private final AdresDAO adresDAO;

    public AdresService(AdresDAO adresDAO) {
        this.adresDAO = adresDAO;
    }

    public void addAdres(Adres adres) {
        adresDAO.addAdres(adres);
    }

    public List<Adres> getAllAdresler() {
        return adresDAO.getAllAdresler();
    }

    public List<Map<String, Object>> getAdreslerByKullaniciId(Integer kullaniciId) {
        return adresDAO.getAdreslerByKullaniciId(kullaniciId);
    }

    public Adres getAdresById(Integer id) {
        return adresDAO.getAdresById(id);
    }

    public void deleteAdres(Integer id) {
        adresDAO.deleteAdres(id);
    }
}

