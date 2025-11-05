package com.unip.projeto_tarefa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unip.projeto_tarefa.models.Projeto;
import com.unip.projeto_tarefa.models.Tarefa;
import com.unip.projeto_tarefa.models.TarefaDto;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepo;

    @Autowired
    private ProjetoRepository projetoRepo;

    public void salvar(TarefaDto dto) {
        Projeto projeto = projetoRepo.findById(dto.getProjetoId())
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado: " + dto.getProjetoId()));

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setStatus(dto.getStatus());
        tarefa.setProjeto(projeto);

        tarefaRepo.save(tarefa);
    }
}
