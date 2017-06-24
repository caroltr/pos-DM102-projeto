package br.inatel.pos.mobile.dm102.projeto.model;

import java.util.ArrayList;

public class Cliente {
	
	private String nome;
	private String endereco;
	private String telefone;
	
	private ArrayList<Atendimento> atendimentos = new ArrayList<>();
	
	public Cliente(String nome, String endereco, String telefone) {
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
	}
	
	public void inserirAtendimento(Atendimento atendimento) {
		atendimentos.add(atendimento);
	}
	
	// Não está sendo utilizado, já que foi integrado ao Banco de dados
	public void listarAtendimentos() {
		System.out.println("Cliente: " + nome);
		
		for(Atendimento atendimento : atendimentos) {
			System.out.println(atendimento);
		}
	}

	protected String getNome() {
		return nome;
	}

	protected String getEndereco() {
		return endereco;
	}

	protected String getTelefone() {
		return telefone;
	}

	@Override
	public String toString() {
		return "Nome: " + nome + ", Endereco: " + endereco + ", Telefone: " + telefone;
	}
}
