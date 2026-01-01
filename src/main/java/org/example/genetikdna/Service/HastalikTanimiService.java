package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.HastalikTanimiDAO;
import org.example.genetikdna.Entity.HastalikTanimi;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HastalikTanimiService {

    private final HastalikTanimiDAO hastalikTanimiDAO;

    public HastalikTanimiService(HastalikTanimiDAO hastalikTanimiDAO) {
        this.hastalikTanimiDAO = hastalikTanimiDAO;
    }

    public void addHastalikTanimi(HastalikTanimi hastalikTanimi) {
        hastalikTanimiDAO.addHastalikTanimi(hastalikTanimi);
    }

    public List<HastalikTanimi> getAllHastalikTanimlari() {
        return hastalikTanimiDAO.getAllHastalikTanimlari();
    }

    public HastalikTanimi getHastalikTanimiById(Integer id) {
        return hastalikTanimiDAO.getHastalikTanimiById(id);
    }

    public HastalikTanimi getHastalikTanimiByIcdKodu(String icdKodu) {
        return hastalikTanimiDAO.getHastalikTanimiByIcdKodu(icdKodu);
    }

    public void deleteHastalikTanimi(Integer id) {
        hastalikTanimiDAO.deleteHastalikTanimi(id);
    }
}

