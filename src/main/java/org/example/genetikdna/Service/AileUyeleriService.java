package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.AileUyeleriDAO;
import org.example.genetikdna.Entity.AileUyeleri;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AileUyeleriService {

    private final AileUyeleriDAO aileUyeleriDAO;

    public AileUyeleriService(AileUyeleriDAO aileUyeleriDAO) {
        this.aileUyeleriDAO = aileUyeleriDAO;
    }

    public void addAileUyesi(AileUyeleri aileUyesi) {
        aileUyeleriDAO.addAileUyesi(aileUyesi);
    }

    public List<AileUyeleri> getAllAileUyeleri() {
        return aileUyeleriDAO.getAllAileUyeleri();
    }

    public List<Map<String, Object>> getAileUyeleriByKullaniciId(Integer kullaniciId) {
        return aileUyeleriDAO.getAileUyeleriByKullaniciId(kullaniciId);
    }

    public AileUyeleri getAileUyesiById(Integer id) {
        return aileUyeleriDAO.getAileUyesiById(id);
    }

    public void deleteAileUyesi(Integer id) {
        aileUyeleriDAO.deleteAileUyesi(id);
    }
}

