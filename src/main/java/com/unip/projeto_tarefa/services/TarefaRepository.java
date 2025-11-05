package com.unip.projeto_tarefa.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unip.projeto_tarefa.models.Projeto;
import com.unip.projeto_tarefa.models.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
	List<Tarefa> findByProjeto(Projeto projeto);
}
