package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.TestPaketi;
import org.example.genetikdna.Service.TestPaketiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test-paketleri")
public class TestPaketiController {

    private final TestPaketiService testPaketiService;

    public TestPaketiController(TestPaketiService testPaketiService) {
        this.testPaketiService = testPaketiService;
    }

    @PostMapping
    public void addTestPaketi(@RequestBody TestPaketi paket) {
        testPaketiService.addTestPaketi(paket);
    }

    @GetMapping
    public List<TestPaketi> getAllTestPaketleri() {
        return testPaketiService.getAllTestPaketleri();
    }

    @GetMapping("/{paketId}/siparisler")
    public List<Map<String, Object>> getTestPaketiSiparisleri(@PathVariable Integer paketId) {
        return testPaketiService.getTestPaketiSiparisleri(paketId);
    }

    @GetMapping("/{id}")
    public TestPaketi getTestPaketiById(@PathVariable("id") Integer id) {
        return testPaketiService.getTestPaketiById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTestPaketi(@PathVariable("id") Integer id) {
        testPaketiService.deleteTestPaketi(id);
    }
}

