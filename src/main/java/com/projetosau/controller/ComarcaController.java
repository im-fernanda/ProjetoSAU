package com.projetosau.controller;

import com.projetosau.domain.Comarca;
import com.projetosau.domain.Regional;
import com.projetosau.service.ComarcaService;
import com.projetosau.service.RegionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ComarcaController {

    private final ComarcaService comarcaService;
    private final RegionalService regionalService;

    @Autowired
    public ComarcaController(ComarcaService comarcaService, RegionalService regionalService) {
        this.comarcaService = comarcaService;
        this.regionalService = regionalService;
    }

    @GetMapping("/cadastroComarca")
    public String getCadastroComarcaPage(@RequestParam(value = "success", required = false) String success,
                                         @RequestParam(value = "error", required = false) String error,
                                         Model model) {
        Comarca comarca = new Comarca();
        List<Regional> regioes = regionalService.findAll(); // Obtém todas as regiões para o select
        model.addAttribute("comarca", comarca);
        model.addAttribute("regionais", regioes);

        if ("true".equals(error)) {
            model.addAttribute("msg", "Por favor, corrija os erros.");
        }

        return "cadastroComarca";
    }

    @PostMapping("/processSaveComarca")
    public ModelAndView processSaveComarca(
            @ModelAttribute @Valid Comarca comarca, BindingResult result,
            @RequestParam("action") String action) {

        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("redirect:/cadastroComarca?error=true");
            return modelAndView;
        }

        try {
            comarcaService.create(comarca);
            if ("save".equals(action)) {
                // Redireciona para a mesma página com uma mensagem de sucesso
                return new ModelAndView("redirect:/cadastroComarca?success=true");
            } else if ("goToUnits".equals(action)) {
                // Redireciona para a página de cadastro de unidades
                return new ModelAndView("redirect:/cadastroUnidade?success=true");
            }
        } catch (IllegalArgumentException e) {
            ModelAndView modelAndView = new ModelAndView("redirect:/cadastroComarca?error=true");
            return modelAndView;
        }

        // Se o valor do botão não for esperado, retornar para a página de cadastro
        return new ModelAndView("redirect:/cadastroComarca");
    }
}
