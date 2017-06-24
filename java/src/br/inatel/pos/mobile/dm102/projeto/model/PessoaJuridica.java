package br.inatel.pos.mobile.dm102.projeto.model;

public class PessoaJuridica extends Cliente {
	
	private String cnpj;
	private String razaoSocial;
	private String inscricaoEstadual;
	
	public PessoaJuridica(Cliente cliente, String cnpj,
			String razaoSocial, String inscricaoSocial) {
		super(cliente.getNome(), cliente.getEndereco(), cliente.getTelefone());
		this.cnpj = cnpj;
		this.razaoSocial = razaoSocial;
		this.inscricaoEstadual = inscricaoSocial;
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	@Override
	public String toString() {
		return "[Pessoa Jurídica] " + super.toString() + " CNPJ: " + cnpj + " Razão Social: " + razaoSocial + " Inscrição estadual: " + inscricaoEstadual;
	}
	
	
}
