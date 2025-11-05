package com.unip.projeto_tarefa.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unip.projeto_tarefa.models.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long>{

}
