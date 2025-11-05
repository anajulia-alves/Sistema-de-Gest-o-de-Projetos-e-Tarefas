package com.unip.projeto_tarefa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unip.projeto_tarefa.models.Projeto;
import com.unip.projeto_tarefa.models.ProjetoDto;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepo;

    public Projeto salvar(ProjetoDto dto) {
        Projeto p = new Projeto();
        p.setNome(dto.getNome());
        p.setDescricao(dto.getDescricao());
        p.setStatus(dto.getStatus());
        return projetoRepo.save(p);
    }
}


