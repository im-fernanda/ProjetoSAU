package com.projetosau.controller;

import com.projetosau.domain.Comarca;
import com.projetosau.domain.Regional;
import com.projetosau.domain.Unidade;
import com.projetosau.service.ComarcaService;
import com.projetosau.service.RegionalService;
import com.projetosau.service.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class UnidadeController {

    private final UnidadeService unidadeService;
    private final ComarcaService comarcaService;
    private final RegionalService regionalService;

    @Autowired
    public UnidadeController(UnidadeService unidadeService, ComarcaService comarcaService, RegionalService regionalService) {
        this.unidadeService = unidadeService;
        this.comarcaService = comarcaService;
        this.regionalService = regionalService;
    }

    @GetMapping("/cadastroUnidade")
    public String getCadastroUnidadePage(@RequestParam(value = "success", required = false) String success,
                                         @RequestParam(value = "error", required = false) String error,
                                         Model model) {
        Unidade unidade = new Unidade();
        List<Regional> regionais = regionalService.findAll();
        List<Comarca> comarcas = comarcaService.findAll();

        model.addAttribute("unidade", unidade);
        model.addAttribute("regionais", regionais);
        model.addAttribute("comarcas", comarcas);

        if ("true".equals(success)) {
            model.addAttribute("msg", "Cadastro realizado com sucesso!");
        }

        if ("true".equals(error)) {
            model.addAttribute("msg", "Por favor, corrija os erros.");
        }

        return "cadastroUnidade";
    }

    @PostMapping("/processSaveUnidade")
    public ModelAndView processSaveUnidade(
            @ModelAttribute @Valid Unidade unidade, BindingResult result,
            @RequestParam("action") String action) {

        if (result.hasErrors()) {
            return new ModelAndView("redirect:/cadastroUnidade?error=true");
        }

        try {
            unidadeService.create(unidade);

            if ("save".equals(action)) {
                return new ModelAndView("redirect:/cadastroUnidade?success=true");
            } else if ("viewAll".equals(action)) {
                return new ModelAndView("redirect:/listarLocais?success=true");
            }
        } catch (IllegalArgumentException e) {
            ModelAndView modelAndView = new ModelAndView("redirect:/cadastroUnidade?error=true");
            return modelAndView;
        }

        return new ModelAndView("redirect:/cadastroUnidade");
    }


    @GetMapping("/listarLocais")
    public String listarUnidades(Model model, @RequestParam(required = false) Boolean success) {
        List<Unidade> unidades = unidadeService.findAll();
        model.addAttribute("unidades", unidades);

        if (Boolean.TRUE.equals(success)) {
            model.addAttribute("msg", "Cadastro realizado com sucesso!");
        }

        // Verificando o conteúdo da lista de unidades
        unidades.forEach(unidade -> {
            System.out.println("Unidade: " + unidade.getNome() + ", Comarca: " + unidade.getComarca().getNome() +
                    ", Regional: " + unidade.getComarca().getRegional().getNome());
        });

        return "listarLocais";
    }

    @GetMapping("/deleteUnidade/{id}")
    public String deleteUnidade(@PathVariable("id") Long id, Model model) {
        try {
            unidadeService.delete(id);
            model.addAttribute("msg", "Unidade deletada com sucesso!");
            model.addAttribute("alertClass", "alert-success");
        } catch (IllegalArgumentException e) {
            model.addAttribute("msg", "Erro ao deletar unidade: " + e.getMessage());
            model.addAttribute("alertClass", "alert-danger");
        }
        return "redirect:/listarLocais?msg=Remoção realizada com sucesso";
    }


    @GetMapping("/editarUnidade/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        Optional<Unidade> optionalUnidade = unidadeService.findById(id);

        if (optionalUnidade.isPresent()) {
            Unidade unidade = optionalUnidade.get();
            List<Regional> regionais = regionalService.findAll();
            List<Comarca> comarcas = comarcaService.findAll();

            model.addAttribute("unidade", unidade);
            model.addAttribute("regionais", regionais);
            model.addAttribute("comarcas", comarcas);
        } else {
            // Lidar com o caso onde a unidade não é encontrada, por exemplo:
            model.addAttribute("errorMessage", "Unidade não encontrada.");
        }

        return "editarUnidade";
    }


    @PostMapping("/processUpdateUnidade")
    public ModelAndView processUpdateUnidade(
            @ModelAttribute @Valid Unidade unidade, BindingResult result,
            @RequestParam("action") String action) {

        // Pega o id que foi passado por parâmetro na URL
        Optional<Unidade> unidadeToUpdate = unidadeService.findById(unidade.getId());
        if (unidadeToUpdate.isPresent()) {
            unidadeService.update(unidade);
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/listarLocais?success=true");
        modelAndView.addObject("unidade", unidadeService.findAll());
        return modelAndView;
    }


}
