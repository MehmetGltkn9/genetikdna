package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.NumuneDAO;
import org.example.genetikdna.Entity.Numune;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NumuneService {

    private final NumuneDAO numuneDAO;

    public NumuneService(NumuneDAO numuneDAO) {
        this.numuneDAO = numuneDAO;
    }

    public void addNumune(Numune numune) {
        numuneDAO.addNumune(numune);
    }

    public List<Numune> getAllNumuneler() {
        return numuneDAO.getAllNumuneler();
    }

    public List<Map<String, Object>> getNumunelerBySiparisId(Integer siparisId) {
        return numuneDAO.getNumunelerBySiparisId(siparisId);
    }

    public List<Map<String, Object>> getNumuneAnalizleri(Integer numuneId) {
        return numuneDAO.getNumuneAnalizleri(numuneId);
    }

    public Numune getNumuneById(Integer id) {
        return numuneDAO.getNumuneById(id);
    }

    public Numune getNumuneByBarkodId(String barkodId) {
        return numuneDAO.getNumuneByBarkodId(barkodId);
    }

    public void deleteNumune(Integer id) {
        numuneDAO.deleteNumune(id);
    }
}

