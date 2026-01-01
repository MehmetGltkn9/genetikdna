package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.GenetikVaryantDAO;
import org.example.genetikdna.Entity.GenetikVaryant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GenetikVaryantService {

    private final GenetikVaryantDAO genetikVaryantDAO;

    public GenetikVaryantService(GenetikVaryantDAO genetikVaryantDAO) {
        this.genetikVaryantDAO = genetikVaryantDAO;
    }

    public void addGenetikVaryant(GenetikVaryant varyant) {
        genetikVaryantDAO.addGenetikVaryant(varyant);
    }

    public List<GenetikVaryant> getAllGenetikVaryantlar() {
        return genetikVaryantDAO.getAllGenetikVaryantlar();
    }

    public List<Map<String, Object>> getGenetikVaryantlarByKromozom(String kromozom) {
        return genetikVaryantDAO.getGenetikVaryantlarByKromozom(kromozom);
    }

    public GenetikVaryant getGenetikVaryantById(Integer id) {
        return genetikVaryantDAO.getGenetikVaryantById(id);
    }

    public GenetikVaryant getGenetikVaryantByRsId(String rsId) {
        return genetikVaryantDAO.getGenetikVaryantByRsId(rsId);
    }

    public void deleteGenetikVaryant(Integer id) {
        genetikVaryantDAO.deleteGenetikVaryant(id);
    }
}

