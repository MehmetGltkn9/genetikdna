package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.LaboratuvarAnaliziDAO;
import org.example.genetikdna.Entity.LaboratuvarAnalizi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LaboratuvarAnaliziService {

    private final LaboratuvarAnaliziDAO laboratuvarAnaliziDAO;

    public LaboratuvarAnaliziService(LaboratuvarAnaliziDAO laboratuvarAnaliziDAO) {
        this.laboratuvarAnaliziDAO = laboratuvarAnaliziDAO;
    }

    public void addLaboratuvarAnalizi(LaboratuvarAnalizi analiz) {
        laboratuvarAnaliziDAO.addLaboratuvarAnalizi(analiz);
    }

    public List<LaboratuvarAnalizi> getAllLaboratuvarAnalizleri() {
        return laboratuvarAnaliziDAO.getAllLaboratuvarAnalizleri();
    }

    public List<Map<String, Object>> getLaboratuvarAnalizleriByNumuneId(Integer numuneId) {
        return laboratuvarAnaliziDAO.getLaboratuvarAnalizleriByNumuneId(numuneId);
    }

    public List<Map<String, Object>> getLaboratuvarAnalizleriByTeknisyen(String teknisyenAdi) {
        return laboratuvarAnaliziDAO.getLaboratuvarAnalizleriByTeknisyen(teknisyenAdi);
    }

    public LaboratuvarAnalizi getLaboratuvarAnaliziById(Integer id) {
        return laboratuvarAnaliziDAO.getLaboratuvarAnaliziById(id);
    }

    public void deleteLaboratuvarAnalizi(Integer id) {
        laboratuvarAnaliziDAO.deleteLaboratuvarAnalizi(id);
    }

    // Stored Procedure: Laboratuvar Analizi Tamamlama
    public Map<String, Object> laboratuvarAnaliziTamamla(Integer numuneId, java.sql.Timestamp analizBitis, 
                                                           String kaliteKontrolSonucu, Integer kullaniciId, String veriSurumu) {
        return laboratuvarAnaliziDAO.laboratuvarAnaliziTamamla(numuneId, analizBitis, kaliteKontrolSonucu, kullaniciId, veriSurumu);
    }
}

