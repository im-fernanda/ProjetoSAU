package com.projetosau.controller;

import com.projetosau.domain.Equipamento;
import com.projetosau.domain.Regional;
import com.projetosau.service.EquipamentoService;
import com.projetosau.service.RegionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Controller
public class EquipamentoController {
    private final EquipamentoService service;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.service = equipamentoService;
    }

    @GetMapping("/cadastroEquipamento")
    public String getCadastroEquipamentoPage(Model model) {
        // Criando um objeto e usando o model para enviar para o HTML
        Equipamento equipamento = new Equipamento();
        model.addAttribute("equipamento", equipamento);
        return "cadastroEquipamento";
    }

    @PostMapping("/processSave/{editar_ou_cadastrar}")
    public ModelAndView processSave(
            @ModelAttribute @Valid Equipamento equipamento, BindingResult result, Errors errors,
            @PathVariable String editar_ou_cadastrar) {

        if (Objects.equals(editar_ou_cadastrar, "edit")) {
            // Pega o id do eletro que foi passado por parâmetro na URL
            Optional<Equipamento> equip = service.findById(equipamento.getId());

            // Modifica com as novas informações pegas do HTML caso o equipmaneto exista
            if (equip.isPresent()) {
                service.update(equipamento);
                System.out.println("Editado com sucesso.");
            }
        }

        if (Objects.equals(editar_ou_cadastrar, "cad")) {
            // Salva o objeto no banco de dados com as informações pegas do HTML
            service.create(equipamento);
            System.out.println("Cadastrado com sucesso");
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/home");
        modelAndView.addObject("msg", "Cadastro realizado com sucesso");
        modelAndView.addObject("equipamentos", service.findAll());
        return modelAndView;
    }

}
