package br.inatel.pos.mobile.dm102.projeto.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Atendimento {
	
	private Date data;
	private String descricao;
	private Cliente cliente;
	
	public Atendimento(Date data, String descricao, Cliente cliente) {
		super();
		this.data = data;
		this.descricao = descricao;
		this.cliente = cliente;
		
		cliente.inserirAtendimento(this);
	}

	public Date getData() {
		return data;
	}

	public String getDescricao() {
		return descricao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	@Override
	public String toString() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dataStr = sdf.format(data);
		
		return "Data: " + dataStr + ", Descrição: " + descricao + ", Cliente: [" + cliente + "]";
	}
	
	
}
