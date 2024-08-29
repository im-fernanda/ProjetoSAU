package com.projetosau.controller;

import com.projetosau.domain.Equipamento;
import com.projetosau.domain.Regional;
import com.projetosau.service.EquipamentoService;
import com.projetosau.service.RegionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EquipamentoController {

    private final EquipamentoService equipamentoService;
    private final RegionalService regionalService;

    public EquipamentoController(EquipamentoService equipamentoService, RegionalService regionalService) {
        this.equipamentoService = equipamentoService;
        this.regionalService = regionalService;
    }

    @GetMapping("/cadastroEquipamento")
    public String getCadastroPage(Model model) {
        // Criando um objeto e usando o model para enviar para o HTML
        Equipamento equipamento = new Equipamento;
        model.addAttribute("equipamento", equipamento);
        return "cadastroEquipamento";
    }

    @GetMapping("/regioes")
    public String getAllRegioes() {
        List<Regional> regioes = regionalService.findAll();
        return regionalService.findAll();
    }

    @GetMapping("/regioes")
    public String getAllRegioes(Model model) {
        List<Regional> regioes = regionalService.findAll();

        return "index";
    }
}
