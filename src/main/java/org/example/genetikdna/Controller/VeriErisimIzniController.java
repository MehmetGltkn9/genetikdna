package org.example.genetikdna.Controller;

import org.example.genetikdna.Entity.VeriErisimIzni;
import org.example.genetikdna.Service.VeriErisimIzniService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/veri-erisim-izinleri")
public class VeriErisimIzniController {

    private final VeriErisimIzniService veriErisimIzniService;

    public VeriErisimIzniController(VeriErisimIzniService veriErisimIzniService) {
        this.veriErisimIzniService = veriErisimIzniService;
    }

    @PostMapping
    public void addVeriErisimIzni(@RequestBody VeriErisimIzni izin) {
        veriErisimIzniService.addVeriErisimIzni(izin);
    }

    @GetMapping
    public List<VeriErisimIzni> getAllVeriErisimIzinleri() {
        return veriErisimIzniService.getAllVeriErisimIzinleri();
    }

    @GetMapping("/kullanicilar/{kullaniciId}")
    public List<Map<String, Object>> getVeriErisimIzinleriByKullaniciId(@PathVariable Integer kullaniciId) {
        return veriErisimIzniService.getVeriErisimIzinleriByKullaniciId(kullaniciId);
    }

    @GetMapping("/{id}")
    public VeriErisimIzni getVeriErisimIzniById(@PathVariable("id") Integer id) {
        return veriErisimIzniService.getVeriErisimIzniById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteVeriErisimIzni(@PathVariable("id") Integer id) {
        veriErisimIzniService.deleteVeriErisimIzni(id);
    }
}

