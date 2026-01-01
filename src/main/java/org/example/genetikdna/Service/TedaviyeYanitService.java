package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.TedaviyeYanitDAO;
import org.example.genetikdna.Entity.TedaviyeYanit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TedaviyeYanitService {

    private final TedaviyeYanitDAO tedaviyeYanitDAO;

    public TedaviyeYanitService(TedaviyeYanitDAO tedaviyeYanitDAO) {
        this.tedaviyeYanitDAO = tedaviyeYanitDAO;
    }

    public void addTedaviyeYanit(TedaviyeYanit yanit) {
        tedaviyeYanitDAO.addTedaviyeYanit(yanit);
    }

    public List<TedaviyeYanit> getAllTedaviyeYanitlar() {
        return tedaviyeYanitDAO.getAllTedaviyeYanitlar();
    }

    public List<Map<String, Object>> getTedaviyeYanitlarBySonucId(Integer sonucId) {
        return tedaviyeYanitDAO.getTedaviyeYanitlarBySonucId(sonucId);
    }

    public TedaviyeYanit getTedaviyeYanitById(Integer id) {
        return tedaviyeYanitDAO.getTedaviyeYanitById(id);
    }

    public void deleteTedaviyeYanit(Integer id) {
        tedaviyeYanitDAO.deleteTedaviyeYanit(id);
    }
}

