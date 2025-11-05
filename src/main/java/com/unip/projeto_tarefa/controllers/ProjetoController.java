package com.unip.projeto_tarefa.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unip.projeto_tarefa.models.Projeto;
import com.unip.projeto_tarefa.models.ProjetoDto;
import com.unip.projeto_tarefa.services.ProjetoRepository;
import com.unip.projeto_tarefa.services.ProjetoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {

	@Autowired
	private ProjetoRepository projetoRepo;
	
	@Autowired
	private ProjetoService projetoService;
	
	
	@GetMapping({"", "/"})
	public String mostrarProjeto (Model model) {
		List<Projeto> projetos = projetoRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("projetos", projetos);
		return "projetos/index";
	}
	
	@GetMapping("/criar")
	public String criarPage (Model model) {
		ProjetoDto projetoDto = new ProjetoDto();
		model.addAttribute("projetoDto", projetoDto);
		return "projetos/CriarProjeto";
	}
	
	@PostMapping("/criar")
	public String criarProjeto(
	        @Valid @ModelAttribute("projetoDto") ProjetoDto projetoDto,
	        BindingResult result,
	        Model model) {

	    if (result.hasErrors()) {
	        return "projetos/CriarProjeto";
	    }

	    Projeto projetoSalvo = projetoService.salvar(projetoDto);
	    return "redirect:/tarefas/criar?projetoId=" + projetoSalvo.getId();
	}
	
	@GetMapping("/editar")
	public String editarProjeto(
	        Model model,
	        @RequestParam Long id
	) {
	    try {
	        Optional<Projeto> projetoOpt = projetoRepo.findById(id);
	        if (projetoOpt.isPresent()) {
	            model.addAttribute("projeto", projetoOpt.get()); // ← Passa a entidade
	        } else {
	            return "redirect:/projetos";
	        }
	    }
	    catch(Exception ex) {
	        System.out.println("Erro: " + ex.getMessage());
	        return "redirect:/projetos";
	    }
	    
	    return "projetos/EditarProjeto";
	}
	
	@PostMapping("/editar")
	public String atualizarProjeto(@ModelAttribute Projeto projeto) {
	    projetoRepo.save(projeto);
	    return "redirect:/projetos";
	}
	
	@GetMapping("/deletar")
	public String deletarProjeto(
			Model model,
			@RequestParam Long id
			) {
		try {
			Projeto projeto = projetoRepo.findById(id).get();
			
			projetoRepo.delete(projeto);
			}
		catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		
		return "redirect:/projetos";
	}
	
}	

