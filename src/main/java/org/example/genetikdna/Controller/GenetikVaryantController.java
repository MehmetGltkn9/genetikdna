package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.GenetikVaryant;
import org.example.genetikdna.Service.GenetikVaryantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/genetik-varyantlar")
public class GenetikVaryantController {

    private final GenetikVaryantService genetikVaryantService;

    public GenetikVaryantController(GenetikVaryantService genetikVaryantService) {
        this.genetikVaryantService = genetikVaryantService;
    }

    @PostMapping
    public void addGenetikVaryant(@RequestBody GenetikVaryant varyant) {
        genetikVaryantService.addGenetikVaryant(varyant);
    }

    @GetMapping
    public List<GenetikVaryant> getAllGenetikVaryantlar() {
        return genetikVaryantService.getAllGenetikVaryantlar();
    }

    @GetMapping("/kromozomlar/{kromozom}")
    public List<Map<String, Object>> getGenetikVaryantlarByKromozom(@PathVariable String kromozom) {
        return genetikVaryantService.getGenetikVaryantlarByKromozom(kromozom);
    }

    @GetMapping("/rs-id/{rsId}")
    public GenetikVaryant getGenetikVaryantByRsId(@PathVariable String rsId) {
        return genetikVaryantService.getGenetikVaryantByRsId(rsId);
    }

    @GetMapping("/{id}")
    public GenetikVaryant getGenetikVaryantById(@PathVariable("id") Integer id) {
        return genetikVaryantService.getGenetikVaryantById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteGenetikVaryant(@PathVariable("id") Integer id) {
        genetikVaryantService.deleteGenetikVaryant(id);
    }
}

