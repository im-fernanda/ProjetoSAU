package com.projetosau.controller;

import com.projetosau.domain.Comarca;
import com.projetosau.domain.Equipamento;
import com.projetosau.domain.Regional;
import com.projetosau.domain.Unidade;
import com.projetosau.service.ComarcaService;
import com.projetosau.service.EquipamentoService;
import com.projetosau.service.RegionalService;
import com.projetosau.service.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EquipamentoController {

    private final EquipamentoService equipamentoService;
    private final RegionalService regionalService;
    private final ComarcaService comarcaService;
    private final UnidadeService unidadeService;

    @Autowired
    public EquipamentoController(EquipamentoService equipamentoService, RegionalService regionalService,
                                 ComarcaService comarcaService, UnidadeService unidadeService) {
        this.equipamentoService = equipamentoService;
        this.regionalService = regionalService;
        this.comarcaService = comarcaService;
        this.unidadeService = unidadeService;
    }

    @GetMapping("/listarEquipamentos")
    public String listarEquipamentos(Model model, @RequestParam(required = false) Boolean success) {
        List<Unidade> unidades = unidadeService.findAll();
        List<Equipamento> equipamentos = equipamentoService.findAll();

        model.addAttribute("unidades", unidades);
        model.addAttribute("equipamentos", equipamentos);

        return "listarEquipamentos";
    }

    @GetMapping("/cadastroEquipamento")
    public String getCadastroEquipamentoPage(Model model,
                                             @RequestParam(value = "success", required = false) String success,
                                             @RequestParam(value = "error", required = false) String error) {

        Equipamento equipamento = new Equipamento();
        //List<Regional> regioes = regionalService.findAll();

//        Regional regionalSelecionado = regionalService.findById(idRegionalSelecionado);
//        List<Comarca> comarcas = comarcaService.findByRegional(regionalSelecionado);
//
//        Long regionalId = equipamento.getUnidade().getRegional().getId();
//        // Filtra as comarcasd com base na regional selecionada
//        List<Comarca> comarcas = comarcaService.findByRegional(regionalId);
//        model.addAttribute("comarcas", comarcas);
//
//        Long comarcaId = equipamento.getUnidade().getId();
//        // Filtra as unidades com base na comarca selecionada
//        List<Unidade> unidades = unidadeService.findByComarca(comarcaId);
//        model.addAttribute("unidades", unidades);

        model.addAttribute("equipamento", equipamento);
        model.addAttribute("regionais", regionalService.findAll());
        model.addAttribute("comarcas", comarcaService.findAll());
        model.addAttribute("unidades", unidadeService.findAll());


        if ("true".equals(success)) {
            model.addAttribute("msg", "Cadastro realizado com sucesso!");
        }

        if ("true".equals(error)) {
            model.addAttribute("msg", "Por favor, corrija os erros.");
        }

        return "cadastroEquipamento";
    }

    @PostMapping("/processSaveEquipamento")
    public ModelAndView processSaveEquipamento(@ModelAttribute @Valid Equipamento equipamento,
                                               BindingResult result, @RequestParam("action") String action) {

        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("redirect:/cadastroEquipamento?error=true");
            return modelAndView;
        }

        try {
            equipamentoService.create(equipamento);
            if ("save".equals(action)) {
                // Redireciona para a mesma página com uma mensagem de sucesso
                return new ModelAndView("redirect:/cadastroEquipamento?success=true");
            } else if ("goToEquipamentos".equals(action)) {
                // Redireciona para a página de cadastro de unidades
                return new ModelAndView("redirect:/cadastroEquipamento?success=true");
            }
        } catch (IllegalArgumentException e) {
            ModelAndView modelAndView = new ModelAndView("redirect:/cadastroEquipamento?error=true");
            return modelAndView;
        }

        // Se o valor do botão não for esperado, retornar para a página de cadastro
        return new ModelAndView("redirect:/cadastroEquipamento");
    }


    @GetMapping("/editarEquipamento/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        Optional<Equipamento> optionalEquipamento = equipamentoService.findById(id);

        if (optionalEquipamento.isPresent()) {
            Equipamento equipamento = optionalEquipamento.get();
            List<Regional> regionais = regionalService.findAll();
            List<Comarca> comarcas = comarcaService.findAll();
            List<Unidade> unidades = unidadeService.findAll();

            model.addAttribute("equipamento", equipamento);
            model.addAttribute("regionais", regionais);
            model.addAttribute("comarcas", comarcas);
            model.addAttribute("unidades", unidades);
        } else {
            model.addAttribute("errorMessage", "Equipamento não encontrado.");
        }

        return "editarEquipamento";
    }



}
