package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.KullaniciDAO;
import org.example.genetikdna.Dao.KullaniciHesapDAO;
import org.example.genetikdna.Entity.Kullanici;
import org.example.genetikdna.Entity.KullaniciHesap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KullaniciService {

    private final KullaniciDAO kullaniciDAO;
    private final KullaniciHesapDAO kullaniciHesapDAO;

    public KullaniciService(KullaniciDAO kullaniciDAO, KullaniciHesapDAO kullaniciHesapDAO) {
        this.kullaniciDAO = kullaniciDAO;
        this.kullaniciHesapDAO = kullaniciHesapDAO;
    }

    public void addKullanici(Kullanici kullanici) {
        kullaniciDAO.addKullanici(kullanici);
    }

    public List<Kullanici> getAllKullanicilar() {
        return kullaniciDAO.getAllKullanicilar();
    }

    public List<Map<String, Object>> getKullaniciDetaylari(Integer kullaniciId) {
        return kullaniciDAO.getKullaniciDetaylari(kullaniciId);
    }

    public Kullanici getKullaniciById(Integer id) {
        return kullaniciDAO.getKullaniciById(id);
    }

    public void deleteKullanici(Integer id) {
        kullaniciDAO.deleteKullanici(id);
    }

    // Stored Procedure: Kullanıcı ve Hesap Ekleme
    public Map<String, Object> kullaniciVeHesapEkle(String ad, String soyad, java.sql.Date dogumTarihi, 
                                                      String cinsiyet, String eposta, String parolaHash) {
        return kullaniciDAO.kullaniciVeHesapEkle(ad, soyad, dogumTarihi, cinsiyet, eposta, parolaHash);
    }

    // Stored Procedure: Kullanıcı Güncelleme
    public Map<String, Object> kullaniciGuncelle(Integer id, String ad, String soyad, 
                                                java.sql.Date dogumTarihi, String cinsiyet, Integer kullaniciId) {
        return kullaniciDAO.kullaniciGuncelle(id, ad, soyad, dogumTarihi, cinsiyet, kullaniciId);
    }

    /**
     * Transaction yönetimi ile kullanıcı ve hesap ekleme
     * Hata durumunda tüm işlemler rollback edilir
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> kullaniciVeHesapEkleWithTransaction(Kullanici kullanici, String eposta, 
                                                                   String parolaHash, boolean aktifMi) {
        try {
            // 1. Kullanıcı ekle ve ID'yi al
            if (kullanici.getKayitTarihi() == null) {
                kullanici.setKayitTarihi(new Timestamp(System.currentTimeMillis()));
            }
            Integer kullaniciId = kullaniciDAO.addKullaniciAndGetId(kullanici);
            
            // 2. Hesap ekle
            KullaniciHesap hesap = new KullaniciHesap();
            hesap.setKullaniciId(kullaniciId);
            hesap.setEposta(eposta);
            hesap.setParolaHash(parolaHash);
            hesap.setAktifMi(aktifMi);
            kullaniciHesapDAO.addKullaniciHesap(hesap);
            
            // 3. Sonuç döndür
            Map<String, Object> result = new HashMap<>();
            result.put("kullanici_id", kullaniciId);
            result.put("eposta", eposta);
            result.put("mesaj", "Kullanıcı ve hesap başarıyla oluşturuldu (Transaction ile)");
            
            return result;
        } catch (Exception e) {
            // Hata durumunda transaction otomatik olarak rollback edilir
            throw new RuntimeException("Kullanıcı ve hesap ekleme işlemi başarısız oldu: " + e.getMessage(), e);
        }
    }
}

