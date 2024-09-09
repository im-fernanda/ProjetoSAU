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

import java.util.*;
import java.util.stream.Collectors;

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
                                         @RequestParam(value = "regional", required = false) Long regionalId,
                                         Model model) {

        Unidade unidade = new Unidade();

        // Carregar todos os regionais
        List<Regional> regionais = regionalService.findAll();
        model.addAttribute("regionais", regionais);

        // Se regionalId for fornecido, carregar comarcas associadas e filtrar regionais
        if (regionalId != null) {
            // Carrega as comarcas associadas ao regionalId
            List<Comarca> comarcas = comarcaService.findByRegional(regionalId);
            model.addAttribute("comarcas", comarcas);

            // Filtra os regionais relacionados às comarcas
            Set<Long> regionalIdsRelacionados = comarcas.stream()
                    .map(Comarca::getRegional)
                    .map(Regional::getId)
                    .collect(Collectors.toSet());

            List<Regional> regionaisRelacionados = regionalService.findAllById(regionalIdsRelacionados);
            model.addAttribute("regionais", regionaisRelacionados);
        } else {
            // Sem regionalId, comarcas são uma lista vazia
            model.addAttribute("comarcas", Collections.emptyList());
        }

        model.addAttribute("unidade", unidade);

        // Mensagens de sucesso ou erro
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
            @ModelAttribute @Valid Unidade unidade, BindingResult result, @RequestParam("regional") Long regionalId,
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

            // Adiciona uma lista de comarcas associadas para a regional selecionada
            model.addAttribute("comarcasPorRegional", comarcas.stream()
                    .filter(c -> c.getRegional().getId().equals(unidade.getRegional().getId()))
                    .collect(Collectors.toList()));
        } else {
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


    @GetMapping("/unidadesPorComarca")
    @ResponseBody
    public List<Unidade> getUnidadesPorComarca(@RequestParam("comarcaId") Long comarcaId) {
        return unidadeService.findByComarca(comarcaId);
    }


}
