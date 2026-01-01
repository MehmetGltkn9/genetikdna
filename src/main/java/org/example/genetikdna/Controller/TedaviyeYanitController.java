package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.TedaviyeYanit;
import org.example.genetikdna.Service.TedaviyeYanitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tedaviye-yanitlar")
public class TedaviyeYanitController {

    private final TedaviyeYanitService tedaviyeYanitService;

    public TedaviyeYanitController(TedaviyeYanitService tedaviyeYanitService) {
        this.tedaviyeYanitService = tedaviyeYanitService;
    }

    @PostMapping
    public void addTedaviyeYanit(@RequestBody TedaviyeYanit yanit) {
        tedaviyeYanitService.addTedaviyeYanit(yanit);
    }

    @GetMapping
    public List<TedaviyeYanit> getAllTedaviyeYanitlar() {
        return tedaviyeYanitService.getAllTedaviyeYanitlar();
    }

    @GetMapping("/sonuclar/{sonucId}")
    public List<Map<String, Object>> getTedaviyeYanitlarBySonucId(@PathVariable Integer sonucId) {
        return tedaviyeYanitService.getTedaviyeYanitlarBySonucId(sonucId);
    }

    @GetMapping("/{id}")
    public TedaviyeYanit getTedaviyeYanitById(@PathVariable("id") Integer id) {
        return tedaviyeYanitService.getTedaviyeYanitById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTedaviyeYanit(@PathVariable("id") Integer id) {
        tedaviyeYanitService.deleteTedaviyeYanit(id);
    }
}

