package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.VeriErisimIzniDAO;
import org.example.genetikdna.Entity.VeriErisimIzni;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VeriErisimIzniService {

    private final VeriErisimIzniDAO veriErisimIzniDAO;

    public VeriErisimIzniService(VeriErisimIzniDAO veriErisimIzniDAO) {
        this.veriErisimIzniDAO = veriErisimIzniDAO;
    }

    public void addVeriErisimIzni(VeriErisimIzni izin) {
        veriErisimIzniDAO.addVeriErisimIzni(izin);
    }

    public List<VeriErisimIzni> getAllVeriErisimIzinleri() {
        return veriErisimIzniDAO.getAllVeriErisimIzinleri();
    }

    public List<Map<String, Object>> getVeriErisimIzinleriByKullaniciId(Integer kullaniciId) {
        return veriErisimIzniDAO.getVeriErisimIzinleriByKullaniciId(kullaniciId);
    }

    public VeriErisimIzni getVeriErisimIzniById(Integer id) {
        return veriErisimIzniDAO.getVeriErisimIzniById(id);
    }

    public void deleteVeriErisimIzni(Integer id) {
        veriErisimIzniDAO.deleteVeriErisimIzni(id);
    }
}

