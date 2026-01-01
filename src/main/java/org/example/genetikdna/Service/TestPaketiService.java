package org.example.genetikdna.Service;

import org.example.genetikdna.Dao.TestPaketiDAO;
import org.example.genetikdna.Entity.TestPaketi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestPaketiService {

    private final TestPaketiDAO testPaketiDAO;

    public TestPaketiService(TestPaketiDAO testPaketiDAO) {
        this.testPaketiDAO = testPaketiDAO;
    }

    public void addTestPaketi(TestPaketi paket) {
        testPaketiDAO.addTestPaketi(paket);
    }

    public List<TestPaketi> getAllTestPaketleri() {
        return testPaketiDAO.getAllTestPaketleri();
    }

    public List<Map<String, Object>> getTestPaketiSiparisleri(Integer paketId) {
        return testPaketiDAO.getTestPaketiSiparisleri(paketId);
    }

    public TestPaketi getTestPaketiById(Integer id) {
        return testPaketiDAO.getTestPaketiById(id);
    }

    public void deleteTestPaketi(Integer id) {
        testPaketiDAO.deleteTestPaketi(id);
    }
}

