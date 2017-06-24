package br.inatel.pos.mobile.dm102.projeto.model;

public class PessoaFisica extends Cliente {
	
	private String cpf;
	private String identidade;
	private String tipoIdentidade;
	
	public PessoaFisica(Cliente cliente, String cpf, String identidade, String tipoIdentidade) {
		super(cliente.getNome(), cliente.getEndereco(), cliente.getTelefone());
		this.cpf = cpf;
		this.identidade = identidade;
		this.tipoIdentidade = tipoIdentidade;
	}

	public String getCpf() {
		return cpf;
	}

	public String getIdentidade() {
		return identidade;
	}

	public String getTipoIdentidade() {
		return tipoIdentidade;
	}

	@Override
	public String toString() {
		return "[Pessoa Física] " + super.toString() + " CPF: " + cpf + ", Identidade: " + identidade + ", Tipo de identidade: " + tipoIdentidade;
	}
	
	
}
