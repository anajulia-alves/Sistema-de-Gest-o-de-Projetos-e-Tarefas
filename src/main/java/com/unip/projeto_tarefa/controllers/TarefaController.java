package com.unip.projeto_tarefa.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unip.projeto_tarefa.models.Projeto;
import com.unip.projeto_tarefa.models.Tarefa;
import com.unip.projeto_tarefa.models.TarefaDto;
import com.unip.projeto_tarefa.services.ProjetoRepository;
import com.unip.projeto_tarefa.services.TarefaRepository;
import com.unip.projeto_tarefa.services.TarefaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

	@Autowired
	private TarefaRepository tarefaRepo;
	
	@Autowired
	private ProjetoRepository projetoRepo;
	
	@Autowired
	private TarefaService tarefaService;
	
	@GetMapping({"", "/"})
	public String listarTarefas(@RequestParam("projetoId") Long projetoId, Model model) {
	    Projeto projeto = projetoRepo.findById(projetoId)
	        .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado: " + projetoId));

	    List<Tarefa> tarefas = tarefaRepo.findByProjeto(projeto);

	    model.addAttribute("tarefas", tarefas);
	    model.addAttribute("projeto", projeto);
	    return "tarefas/index";
	}

	
    @GetMapping("/projeto/{id}")
    public String listarTarefasPorProjeto(@PathVariable Long id, Model model) {
        Projeto projeto = projetoRepo.findById(id).orElse(null);
        if (projeto == null) {
            return "redirect:/projetos";
        }
        List<Tarefa> tarefas = tarefaRepo.findByProjeto(projeto);
        model.addAttribute("projeto", projeto);
        model.addAttribute("tarefas", tarefas);
        return "tarefas/porProjeto";
    }
    
    @GetMapping("/criar")
    public String mostrarFormularioCriarTarefa(@RequestParam(required = false) Long projetoId, Model model) {
        if (projetoId == null) {
            
            return "redirect:/projetos";
        }
        
        Projeto projeto = projetoRepo.findById(projetoId)
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado: " + projetoId));
        
        Tarefa tarefa = new Tarefa();
        tarefa.setProjeto(projeto);
        
        model.addAttribute("tarefa", tarefa);
        model.addAttribute("projeto", projeto);
        
        return "tarefas/CriarTarefa";
    }

    @PostMapping("/criar")
    public String criarTarefa(
            @Valid @ModelAttribute("tarefa") Tarefa tarefa,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            
            model.addAttribute("projeto", tarefa.getProjeto());
            return "tarefas/CriarTarefa";
        }

        tarefaRepo.save(tarefa);
        
        // Redireciona para a lista de tarefas do mesmo projeto
        return "redirect:/tarefas?projetoId=" + tarefa.getProjeto().getId();
    }

    @GetMapping("/editar")
    public String editarTarefa(
            Model model,
            @RequestParam Long id
    ) {
        try {
            Optional<Tarefa> tarefaOpt = tarefaRepo.findById(id);
            if (tarefaOpt.isPresent()) {
                Tarefa tarefa = tarefaOpt.get();
                model.addAttribute("tarefa", tarefa);
                model.addAttribute("projeto", tarefa.getProjeto()); // Para mostrar info do projeto
            } else {
                return "redirect:/tarefas?projetoId=" + tarefaOpt.get().getProjeto().getId();
            }
        }
        catch(Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return "redirect:/projetos";
        }
        
        return "tarefas/EditarTarefa";
    }

    @PostMapping("/editar")
    public String atualizarTarefa(@ModelAttribute Tarefa tarefa) {
        // Mantém a relação com o projeto ao salvar
        Tarefa tarefaExistente = tarefaRepo.findById(tarefa.getId()).orElse(null);
        if (tarefaExistente != null) {
            tarefa.setProjeto(tarefaExistente.getProjeto());
        }
        
        tarefaRepo.save(tarefa);
        return "redirect:/tarefas?projetoId=" + tarefa.getProjeto().getId();
    }
    
	@GetMapping("/deletar")
	public String deletarTarefa(
			Model model,
			@RequestParam Long id
			) {
		try {
			Tarefa tarefa = tarefaRepo.findById(id).get();
			
			tarefaRepo.delete(tarefa);
			}
		catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		
		return "redirect:/projetos";
	}

}

