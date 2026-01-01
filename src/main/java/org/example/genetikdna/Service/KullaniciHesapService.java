package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.KullaniciHesapDAO;
import org.example.genetikdna.Entity.KullaniciHesap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KullaniciHesapService {

    private final KullaniciHesapDAO kullaniciHesapDAO;

    public KullaniciHesapService(KullaniciHesapDAO kullaniciHesapDAO) {
        this.kullaniciHesapDAO = kullaniciHesapDAO;
    }

    public void addKullaniciHesap(KullaniciHesap hesap) {
        kullaniciHesapDAO.addKullaniciHesap(hesap);
    }

    public List<KullaniciHesap> getAllKullaniciHesaplari() {
        return kullaniciHesapDAO.getAllKullaniciHesaplari();
    }

    public List<Map<String, Object>> getKullaniciHesapByKullaniciId(Integer kullaniciId) {
        return kullaniciHesapDAO.getKullaniciHesapByKullaniciId(kullaniciId);
    }

    public KullaniciHesap getKullaniciHesapByEposta(String eposta) {
        return kullaniciHesapDAO.getKullaniciHesapByEposta(eposta);
    }

    public KullaniciHesap getKullaniciHesapById(Integer id) {
        return kullaniciHesapDAO.getKullaniciHesapById(id);
    }

    public void deleteKullaniciHesap(Integer id) {
        kullaniciHesapDAO.deleteKullaniciHesap(id);
    }
}

