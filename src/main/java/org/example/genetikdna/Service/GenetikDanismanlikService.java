package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.GenetikDanismanlikDAO;
import org.example.genetikdna.Entity.GenetikDanismanlik;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GenetikDanismanlikService {

    private final GenetikDanismanlikDAO genetikDanismanlikDAO;

    public GenetikDanismanlikService(GenetikDanismanlikDAO genetikDanismanlikDAO) {
        this.genetikDanismanlikDAO = genetikDanismanlikDAO;
    }

    public void addGenetikDanismanlik(GenetikDanismanlik danismanlik) {
        genetikDanismanlikDAO.addGenetikDanismanlik(danismanlik);
    }

    public List<GenetikDanismanlik> getAllGenetikDanismanliklar() {
        return genetikDanismanlikDAO.getAllGenetikDanismanliklar();
    }

    public List<Map<String, Object>> getGenetikDanismanliklarByKullaniciId(Integer kullaniciId) {
        return genetikDanismanlikDAO.getGenetikDanismanliklarByKullaniciId(kullaniciId);
    }

    public GenetikDanismanlik getGenetikDanismanlikById(Integer id) {
        return genetikDanismanlikDAO.getGenetikDanismanlikById(id);
    }

    public void deleteGenetikDanismanlik(Integer id) {
        genetikDanismanlikDAO.deleteGenetikDanismanlik(id);
    }
}

