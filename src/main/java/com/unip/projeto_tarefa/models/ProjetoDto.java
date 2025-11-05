package com.unip.projeto_tarefa.models;

import jakarta.validation.constraints.*;

public class ProjetoDto {

	@NotBlank(message="O campo não pode estar vazio")
	private String nome;
	
	@NotBlank(message="O campo não pode estar vazio")
	private String status;
	
	@Size(min=10, message ="A descrição precisa ter no mínimo 10 caracteres")
	@Size(max=2000, message = "A descrição deve ter no máximo 2000 caracteres")
	@NotBlank
	private String descricao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return descricao;
	}

	public void setDescription(String descricao) {
		this.descricao = descricao;
	}

	
}
