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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class EquipamentoController {

    private final EquipamentoService equipamentoService;
    private final RegionalService regionalService;
    private final ComarcaService comarcaService;
    private final UnidadeService unidadeService;

    public EquipamentoController(EquipamentoService equipamentoService, RegionalService regionalService,
                                 ComarcaService comarcaService, UnidadeService unidadeService) {
        this.equipamentoService = equipamentoService;
        this.regionalService = regionalService;
        this.comarcaService = comarcaService;
        this.unidadeService = unidadeService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/listarEquipamentos";
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
                                             @RequestParam(value = "equipamento-regional", required = false) Long regionalId,
                                             @RequestParam(value = "equipamento-comarca", required = false) Long comarcaId,
                                             @RequestParam(value = "success", required = false) String success,
                                             @RequestParam(value = "error", required = false) String error) {

        Equipamento equipamento = new Equipamento();


        // Carregar todos os regionais
        List<Regional> regionais = regionalService.findAll();
        model.addAttribute("regionais", regionais);

        // Se regionalId for fornecido, carregar comarcas associadas e filtra os regionais
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

        model.addAttribute("equipamento", equipamento);

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
                // Redireciona para a página de listagem dos equipamentos
                return new ModelAndView("redirect:/listarEquipamentos");
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

            // Adiciona uma lista de comarcas associadas para a regional selecionada
            model.addAttribute("comarcasPorRegional", comarcas.stream()
                    .filter(c -> c.getRegional().getId().equals(equipamento.getUnidade().getComarca().getRegional().getId()))
                    .collect(Collectors.toList()));

            // Adiciona uma lista de unidades associadas para a comarca selecionada
            model.addAttribute("unidadesPorComarca", unidades.stream()
                    .filter(u -> u.getComarca().getId().equals(equipamento.getUnidade().getComarca().getId()))
                    .collect(Collectors.toList()));
        } else {
            model.addAttribute("errorMessage", "Equipamento não encontrado.");
        }

        return "editarEquipamento";
    }

    @PostMapping("/processUpdateEquipamento")
    public ModelAndView processUpdateEquipamento(
            @ModelAttribute @Valid Equipamento equipamento, BindingResult result,
            @RequestParam("action") String action) {

        // Verifica se há erros de validação
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("editarEquipamento");
            modelAndView.addObject("equipamento", equipamento);
            modelAndView.addObject("regionais", regionalService.findAll());
            modelAndView.addObject("comarcas", comarcaService.findAll());
            modelAndView.addObject("comarcasPorRegional", comarcaService.findByRegional(equipamento.getUnidade().getRegional().getId()));
            return modelAndView;
        }

        // Atualiza o equipamento se não houver erros
        equipamentoService.update(equipamento);

        // Redireciona para a lista de equipamentos com uma mensagem de sucesso
        ModelAndView modelAndView = new ModelAndView("redirect:/listarEquipamentos?success=true");
        return modelAndView;
    }


    @GetMapping("/deletarEquipamento/{id}")
    public String deleteUnidade(@PathVariable("id") Long id, Model model) {
        try {
            equipamentoService.delete(id);
            model.addAttribute("msg", "Equipamento deletado com sucesso!");
            model.addAttribute("alertClass", "alert-success");
        } catch (IllegalArgumentException e) {
            model.addAttribute("msg", "Erro ao deletar equipamento: " + e.getMessage());
            model.addAttribute("alertClass", "alert-danger");
        }
        return "redirect:/listarEquipamentos?msg=Remoção realizada com sucesso";
    }

    @GetMapping("/listarPorLocal/{unidadeId}")
    public String listarPorLocal(@PathVariable("unidadeId") Long unidadeId, Model model) {
        Optional<Unidade> unidadeOpt = unidadeService.findById(unidadeId);
        
        if (unidadeOpt.isEmpty()) {
            model.addAttribute("errorMessage", "Unidade não encontrada.");
            return "redirect:/listarLocais";
        }

        Unidade unidade = unidadeOpt.get();
        List<Equipamento> equipamentos = equipamentoService.findByUnidadeId(unidadeId);

        model.addAttribute("unidade", unidade);
        model.addAttribute("equipamentos", equipamentos);

        return "listarEquipamentosPorLocal";
    }

    @GetMapping("/listarPorTombo")
    public String buscarPorTomboForm(Model model) {
        return "buscarPorTombo";
    }

    @GetMapping("/listarPorTombo/{tombo}")
    public String listarPorTombo(@PathVariable("tombo") Integer tombo, Model model) {
        Optional<Equipamento> equipamentoOpt = equipamentoService.findByTombo(tombo);

        if (equipamentoOpt.isEmpty()) {
            model.addAttribute("errorMessage", "Equipamento com tombo " + tombo + " não encontrado.");
            model.addAttribute("tombo", tombo);
            return "buscarPorTombo";
        }

        Equipamento equipamento = equipamentoOpt.get();
        model.addAttribute("equipamento", equipamento);

        return "detalhesEquipamento";
    }

    @GetMapping("/listarPorNome")
    public String buscarPorNomeForm(Model model) {
        return "buscarPorNome";
    }

    @GetMapping("/listarPorNome/resultado")
    public String listarPorNome(@RequestParam("nome") String nome, Model model) {
        if (nome == null || nome.trim().isEmpty()) {
            model.addAttribute("errorMessage", "Por favor, informe um nome para buscar.");
            return "buscarPorNome";
        }

        List<Equipamento> equipamentos = equipamentoService.findByNome(nome.trim());
        model.addAttribute("equipamentos", equipamentos);
        model.addAttribute("nomeBusca", nome.trim());

        return "listarEquipamentosPorNome";
    }

    @GetMapping("/cadastrarTransferencia/{id}")
    public String getTransferenciaForm(@PathVariable("id") Long id, Model model) {
        Optional<Equipamento> equipamentoOpt = equipamentoService.findById(id);

        if (equipamentoOpt.isEmpty()) {
            model.addAttribute("errorMessage", "Equipamento não encontrado.");
            return "redirect:/listarEquipamentos";
        }

        Equipamento equipamento = equipamentoOpt.get();
        List<Unidade> unidades = unidadeService.findAll();
        List<Regional> regionais = regionalService.findAll();

        model.addAttribute("equipamento", equipamento);
        model.addAttribute("unidades", unidades);
        model.addAttribute("regionais", regionais);

        return "transferirEquipamento";
    }

    @PostMapping("/processTransferencia")
    public ModelAndView processTransferencia(
            @RequestParam("equipamentoId") Long equipamentoId,
            @RequestParam("novaUnidadeId") Long novaUnidadeId) {

        try {
            equipamentoService.transferirEquipamento(equipamentoId, novaUnidadeId);
            return new ModelAndView("redirect:/listarEquipamentos?msg=Transferência realizada com sucesso");
        } catch (IllegalArgumentException e) {
            ModelAndView modelAndView = new ModelAndView("redirect:/cadastrarTransferencia/" + equipamentoId);
            modelAndView.addObject("errorMessage", e.getMessage());
            return modelAndView;
        }
    }

}
