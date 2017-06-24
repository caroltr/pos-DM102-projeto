package br.inatel.pos.mobile.dm102.projeto.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.inatel.pos.mobile.dm102.projeto.controller.JdbcAcesso;

public class PessoaFisicaDAO implements GenericDAO<PessoaFisica> {

	@Override
	public void criar(PessoaFisica pessoaFisica) {
		
		String mensagem = null;
		boolean ocorreuErro = false;
		
		PreparedStatement psttmCliente = null;
		PreparedStatement psttmClientePessoaFisica = null;
		ResultSet rs = null;
		
		try (Connection connection = JdbcAcesso.connect()) {
			Integer id = null;
			
			// Inserindo primeiro na tabela cliente
			String sqlCliente = "INSERT INTO cliente (nome, endereco, telefone) VALUES (?, ?, ?)";
			psttmCliente = connection.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
					
			psttmCliente.setString(1, pessoaFisica.getNome());
			psttmCliente.setString(2, pessoaFisica.getEndereco());
			psttmCliente.setString(3, pessoaFisica.getTelefone());
			
			if(psttmCliente.executeUpdate() != 0) {
				
				rs = psttmCliente.getGeneratedKeys();
	            if (rs.next()) {
	                id = rs.getInt(1);
	            }
			}
			
			// Se foi inserido corretamente na tabela cliente, inseri os dados restantes na tabela cliente_pessoa_fisica
			if(id != null) {
				String sqlPessoaFisica = "INSERT INTO cliente_pessoa_fisica (id, cpf, identidade, tipo_identidade) VALUES (?, ?, ?, ?)";
				
				psttmClientePessoaFisica = connection.prepareStatement(sqlPessoaFisica);
					
				psttmClientePessoaFisica.setInt(1, id);
				psttmClientePessoaFisica.setString(2, pessoaFisica.getCpf());
				psttmClientePessoaFisica.setString(3, pessoaFisica.getIdentidade());
				psttmClientePessoaFisica.setString(4, pessoaFisica.getTipoIdentidade());
				
				if(psttmClientePessoaFisica.executeUpdate() != 0) {
					mensagem = "O cliente pessoa física foi cadastrado com sucesso!";
				} else {
					mensagem = "Ocorreu um erro ao cadastrar o cliente.";
				}
				
			} else {
				mensagem = "Ocorreu um erro ao cadastrar o cliente.";
			}
		} catch(SQLException e) {
			mensagem = "Ocorreu um erro no acesso ao banco de dados: " + e.getMessage();
			ocorreuErro = true;
		} finally {
			if(ocorreuErro) {
				System.err.println(mensagem);
				// TODO limpar todos os clientes da tabela que não possuem correspondente nas tabelas cliente_pessoa_fisica ou cliente_pessoa_juridica
			} else {
				System.out.println(mensagem);
			}
			
			// Fechando objetos
			JdbcAcesso.psttmClose(psttmCliente);
			JdbcAcesso.psttmClose(psttmClientePessoaFisica);
			JdbcAcesso.rsClose(rs);
		}
	}

	@Override
	public ArrayList<PessoaFisica> listar() {
		ArrayList<PessoaFisica> clientesPessoaFisica = new ArrayList<>();

		String sql = "SELECT id FROM cliente";
		
		try (
				Connection connection = JdbcAcesso.connect();
				Statement sttm = connection.createStatement();
				ResultSet rs = sttm.executeQuery(sql);
				) {
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				
				PessoaFisica clientePessoaFisica = buscarPessoaFisica(id);
				if(clientePessoaFisica != null) {
					clientesPessoaFisica.add(clientePessoaFisica);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.err.println("Ocorreu um erro no acesso ao banco de dados: " + e.getMessage());
		}
		
		return clientesPessoaFisica;
	}
	
	public PessoaFisica buscarPessoaFisica(int id) {
		
		PessoaFisica pessoaFisica = null;
		String sqlPessoaFisica = "SELECT * FROM cliente_pessoa_fisica WHERE id = " + id;
		try (
				Connection connection = JdbcAcesso.connect();
				Statement sttm = connection.createStatement();
				ResultSet rs = sttm.executeQuery(sqlPessoaFisica)
				) {
			
			if(rs.next()) {
				
				String cpf = rs.getString("cpf");
				String identidade = rs.getString("identidade");
				String tipoIdentidade = rs.getString("tipo_identidade");
				
				ClienteDAO clDao = new ClienteDAO();
				Cliente cliente = clDao.buscarCliente(id);
				
				if(cliente != null) {
					pessoaFisica = new PessoaFisica(cliente, cpf, identidade, tipoIdentidade);
				}
				
			}
		} catch(SQLException e) {
			// e.printStackTrace();
			System.err.println("Ocorreu um erro no acesso ao banco de dados: " + e.getMessage());
		}
		
		return pessoaFisica;
	}
	
	public PessoaFisica buscarPessoaFisica(String cpf) {
		
		PessoaFisica pessoaFisica = null;
		String sqlPessoaFisica = "SELECT * FROM cliente_pessoa_fisica WHERE cpf = '" + cpf + "'";
		try (
				Connection connection = JdbcAcesso.connect();
				Statement sttm = connection.createStatement();
				ResultSet rs = sttm.executeQuery(sqlPessoaFisica)
				) {
			
			if(rs.next()) {
				
				int id = rs.getInt("id");
				String identidade = rs.getString("identidade");
				String tipoIdentidade = rs.getString("tipo_identidade");
				
				ClienteDAO clDao = new ClienteDAO();
				Cliente cliente = clDao.buscarCliente(id);
				
				if(cliente != null) {
					pessoaFisica = new PessoaFisica(cliente, cpf, identidade, tipoIdentidade);
				}
				
			}
		} catch(SQLException e) {
			// e.printStackTrace();
			System.err.println("Ocorreu um erro no acesso ao banco de dados: " + e.getMessage());
		}
		
		return pessoaFisica;
	}
}
