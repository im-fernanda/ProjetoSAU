package com.projetosau.controller;

import com.projetosau.domain.Regional;
import com.projetosau.service.RegionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegionalController {

    private final RegionalService regionalService;

    @Autowired
    public RegionalController(RegionalService regionalService) {
        this.regionalService = regionalService;
    }

    @GetMapping("/cadastroRegional")
    public String getCadastroRegionalPage(Model model) {
        model.addAttribute("regional", new Regional());
        return "cadastroRegional";
    }

    @PostMapping("/processSaveRegional")
    public ModelAndView processSaveRegional(
            @ModelAttribute @Valid Regional regional, BindingResult result, Errors errors,
            @RequestParam("action") String action) {

        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("cadastroRegional");
            modelAndView.addObject("msg", "Por favor, corrija os erros.");
            return modelAndView;
        }

        try {
            regionalService.create(regional);
            String successMessage = "Regional cadastrada com sucesso!";

            if ("save".equals(action)) {
                // Redireciona para a mesma página com uma mensagem de sucesso
                return new ModelAndView("redirect:/cadastroRegional?success=true");
            } else if ("goToComarca".equals(action)) {
                // Redireciona para a página de cadastro de comarca
                return new ModelAndView("redirect:/cadastroComarca?success=true");
            }
        } catch (IllegalArgumentException e) {
            ModelAndView modelAndView = new ModelAndView("cadastroRegional");
            modelAndView.addObject("msg", e.getMessage());
            return modelAndView;
        }

        // Se o valor do botão não for esperado, retornar para a página de cadastro
        return new ModelAndView("cadastroRegional");
    }
}
