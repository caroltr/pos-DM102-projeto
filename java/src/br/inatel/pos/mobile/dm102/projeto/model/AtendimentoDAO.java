package br.inatel.pos.mobile.dm102.projeto.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.inatel.pos.mobile.dm102.projeto.controller.JdbcAcesso;

public class AtendimentoDAO implements GenericDAO<Atendimento> {

	@Override
	public void criar(Atendimento atendimento) {
		
		String sqlAtendimento = "INSERT INTO atendimento (cliente_id, data, descricao) VALUES (?, ?, ?)";
		try (Connection connection = JdbcAcesso.connect();
			PreparedStatement psttm = connection.prepareStatement(sqlAtendimento)) {
			
			// Buscar id do cliente
			ClienteDAO clDao = new ClienteDAO();
			Integer clienteId = clDao.buscarClienteId(atendimento.getCliente());
			if(clienteId != null) {
				psttm.setInt(1, clienteId);
				psttm.setDate(2, atendimento.getData());
				psttm.setString(3, atendimento.getDescricao());
				
				if(psttm.executeUpdate() != 0) {
					System.out.println("O atendimento foi cadastrado com sucesso!");
				} else {
					System.err.println("Ocorreu um erro ao cadastraro atendimento.");
				}
			} else {
				System.err.println("Ocorreu um erro ao buscar o cliente selecionado");
			}
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.err.println("Ocorreu um erro no acesso ao banco de dados: " + e.getMessage());
		}
	}
	
	@Override
	public ArrayList<Atendimento> listar() {
		ArrayList<Atendimento> atendimentos = new ArrayList<>();

		String sql = "SELECT * FROM atendimento";
		
		try (
				Connection connection = JdbcAcesso.connect();
				Statement sttm = connection.createStatement();
				ResultSet rs = sttm.executeQuery(sql);
				) {
			
			while(rs.next()) {
				
				int cliente_id = rs.getInt("cliente_id");
				Date data = rs.getDate("data");
				String descricao = rs.getString("descricao");
				
				Cliente cliente = null;
				
				PessoaFisicaDAO pfDao = new PessoaFisicaDAO();
				PessoaFisica pessoaFisica = pfDao.buscarPessoaFisica(cliente_id);
				if(pessoaFisica != null) {
					cliente = pessoaFisica;
				} else {
					PessoaJuridicaDAO pjDao = new PessoaJuridicaDAO();
					PessoaJuridica pessoaJuridica = pjDao.buscarPessoaJuridica(cliente_id);
					if(pessoaJuridica != null) {
						cliente = pessoaJuridica;
					}
				}
				
				if (cliente != null) {
					Atendimento atendimento = new Atendimento(data, descricao, cliente);
					atendimentos.add(atendimento);
				} else {
					System.err.println("Erro ao buscar cliente");
				}
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			System.err.println("Ocorreu um erro no acesso ao banco de dados: " + e.getMessage());
		}
		
		return atendimentos;
	}
	
	public ArrayList<Atendimento> listar(Cliente cliente) {
		ArrayList<Atendimento> atendimentos = new ArrayList<>();

		// Buscar id do cliente
		ClienteDAO clDao = new ClienteDAO();
		Integer clienteId = clDao.buscarClienteId(cliente);
		
		String sql = "SELECT * FROM atendimento WHERE cliente_id = " + clienteId;
		
		try (
				Connection connection = JdbcAcesso.connect();
				Statement sttm = connection.createStatement();
				ResultSet rs = sttm.executeQuery(sql);
				) {
			
			while(rs.next()) {
				
				Date data = rs.getDate("data");
				String descricao = rs.getString("descricao");
				
				Atendimento atendimento = new Atendimento(data, descricao, cliente);
				atendimentos.add(atendimento);
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			System.err.println("Ocorreu um erro no acesso ao banco de dados: " + e.getMessage());
		}
		
		return atendimentos;
	}
}
