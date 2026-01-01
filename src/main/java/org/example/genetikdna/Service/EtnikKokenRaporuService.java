package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.EtnikKokenRaporuDAO;
import org.example.genetikdna.Entity.EtnikKokenRaporu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EtnikKokenRaporuService {

    private final EtnikKokenRaporuDAO etnikKokenRaporuDAO;

    public EtnikKokenRaporuService(EtnikKokenRaporuDAO etnikKokenRaporuDAO) {
        this.etnikKokenRaporuDAO = etnikKokenRaporuDAO;
    }

    public void addEtnikKokenRaporu(EtnikKokenRaporu rapor) {
        etnikKokenRaporuDAO.addEtnikKokenRaporu(rapor);
    }

    public List<EtnikKokenRaporu> getAllEtnikKokenRaporlari() {
        return etnikKokenRaporuDAO.getAllEtnikKokenRaporlari();
    }

    public List<Map<String, Object>> getEtnikKokenRaporlariBySonucId(Integer sonucId) {
        return etnikKokenRaporuDAO.getEtnikKokenRaporlariBySonucId(sonucId);
    }

    public EtnikKokenRaporu getEtnikKokenRaporuById(Integer id) {
        return etnikKokenRaporuDAO.getEtnikKokenRaporuById(id);
    }

    public void deleteEtnikKokenRaporu(Integer id) {
        etnikKokenRaporuDAO.deleteEtnikKokenRaporu(id);
    }
}

