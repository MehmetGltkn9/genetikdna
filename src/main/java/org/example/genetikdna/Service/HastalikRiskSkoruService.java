package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.HastalikRiskSkoruDAO;
import org.example.genetikdna.Entity.HastalikRiskSkoru;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HastalikRiskSkoruService {

    private final HastalikRiskSkoruDAO hastalikRiskSkoruDAO;

    public HastalikRiskSkoruService(HastalikRiskSkoruDAO hastalikRiskSkoruDAO) {
        this.hastalikRiskSkoruDAO = hastalikRiskSkoruDAO;
    }

    public void addHastalikRiskSkoru(HastalikRiskSkoru riskSkoru) {
        hastalikRiskSkoruDAO.addHastalikRiskSkoru(riskSkoru);
    }

    public List<HastalikRiskSkoru> getAllHastalikRiskSkorlari() {
        return hastalikRiskSkoruDAO.getAllHastalikRiskSkorlari();
    }

    public List<Map<String, Object>> getHastalikRiskSkorlariBySonucId(Integer sonucId) {
        return hastalikRiskSkoruDAO.getHastalikRiskSkorlariBySonucId(sonucId);
    }

    public List<Map<String, Object>> getHastalikRiskSkorlariByHastalikId(Integer hastalikId) {
        return hastalikRiskSkoruDAO.getHastalikRiskSkorlariByHastalikId(hastalikId);
    }

    public HastalikRiskSkoru getHastalikRiskSkoruById(Integer id) {
        return hastalikRiskSkoruDAO.getHastalikRiskSkoruById(id);
    }

    public void deleteHastalikRiskSkoru(Integer id) {
        hastalikRiskSkoruDAO.deleteHastalikRiskSkoru(id);
    }

    // Stored Procedure: Hastalık Risk Skoru ve Tedaviye Yanıt Ekleme
    public Map<String, Object> hastalikRiskVeTedaviEkle(Integer sonucId, Integer hastalikId, Double riskYuzdesi, 
                                                          String riskSeviyesi, String ilacAdi, String yanitTahmini, String oneriler) {
        return hastalikRiskSkoruDAO.hastalikRiskVeTedaviEkle(sonucId, hastalikId, riskYuzdesi, riskSeviyesi, ilacAdi, yanitTahmini, oneriler);
    }
}

