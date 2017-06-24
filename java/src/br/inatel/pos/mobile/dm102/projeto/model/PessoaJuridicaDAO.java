package br.inatel.pos.mobile.dm102.projeto.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.inatel.pos.mobile.dm102.projeto.controller.JdbcAcesso;

public class PessoaJuridicaDAO implements GenericDAO<PessoaJuridica> {

	@Override
	public void criar(PessoaJuridica pessoaJuridica) {
		
		String mensagem = null;
		boolean ocorreuErro = false;
		
		PreparedStatement psttmCliente = null;
		PreparedStatement psttmClientePessoaJuridica = null;
		ResultSet rs = null;
		
		try (Connection connection = JdbcAcesso.connect()) {
			Integer id = null;
			
			// Inserindo primeiro na tabela cliente
			String sqlCliente = "INSERT INTO cliente (nome, endereco, telefone) VALUES (?, ?, ?)";
			psttmCliente = connection.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
					
			psttmCliente.setString(1, pessoaJuridica.getNome());
			psttmCliente.setString(2, pessoaJuridica.getEndereco());
			psttmCliente.setString(3, pessoaJuridica.getTelefone());
			
			if(psttmCliente.executeUpdate() != 0) {
				
				rs = psttmCliente.getGeneratedKeys();
	            if (rs.next()) {
	                id = rs.getInt(1);
	            }
			}
			
			// Se foi inserido corretamente na tabela cliente, inseri os dados restantes na tabela cliente_pessoa_fisica
			if(id != null) {
				String sqlPessoaJuridica = "INSERT INTO cliente_pessoa_juridica (id, cnpj, razao_social, inscricao_estadual) VALUES (?, ?, ?, ?)";
				
				psttmClientePessoaJuridica = connection.prepareStatement(sqlPessoaJuridica);
					
				psttmClientePessoaJuridica.setInt(1, id);
				psttmClientePessoaJuridica.setString(2, pessoaJuridica.getCnpj());
				psttmClientePessoaJuridica.setString(3, pessoaJuridica.getRazaoSocial());
				psttmClientePessoaJuridica.setString(4, pessoaJuridica.getInscricaoEstadual());
				
				if(psttmClientePessoaJuridica.executeUpdate() != 0) {
					mensagem = "O cliente pessoa jurídica foi cadastrado com sucesso!";
				} else {
					mensagem = "Ocorreu um erro ao cadastrar o cliente.";
				}
				
			} else {
				mensagem = "Ocorreu um erro ao cadastrar o cliente.";
			}
		} catch(SQLException e) {
			//e.printStackTrace();
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
			JdbcAcesso.psttmClose(psttmClientePessoaJuridica);
			JdbcAcesso.rsClose(rs);
		}
	}
	
	@Override
	public ArrayList<PessoaJuridica> listar() {
		ArrayList<PessoaJuridica> clientesPessoaJuridica = new ArrayList<>();

		String sql = "SELECT id FROM cliente";
		
		try (
				Connection connection = JdbcAcesso.connect();
				Statement sttm = connection.createStatement();
				ResultSet rs = sttm.executeQuery(sql);
				) {
			
			while(rs.next()) {
				
				int id = rs.getInt("id");

				PessoaJuridica clientePessoaJuridica = buscarPessoaJuridica(id);
				if(clientePessoaJuridica != null) {
					clientesPessoaJuridica.add(clientePessoaJuridica);
				}
				
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.err.println("Ocorreu um erro no acesso ao banco de dados: " + e.getMessage());
		}
		
		return clientesPessoaJuridica;
	}
	
	public PessoaJuridica buscarPessoaJuridica(int id) {
		
		PessoaJuridica pessoaJuridica = null;
		String sql = "SELECT * FROM cliente_pessoa_juridica WHERE id = " + id;
		try (
				Connection connection = JdbcAcesso.connect();
				Statement sttm = connection.createStatement();
				ResultSet rs = sttm.executeQuery(sql)
				) {
			
			if(rs.next()) {
				
				String cnpj = rs.getString("cnpj");
				String razaoSocial = rs.getString("razao_social");
				String inscricaoEstadual = rs.getString("inscricao_estadual");
				
				ClienteDAO clDao = new ClienteDAO();
				Cliente cliente = clDao.buscarCliente(id);
				
				if(cliente != null) {
					pessoaJuridica = new PessoaJuridica(cliente, cnpj, razaoSocial, inscricaoEstadual);
				}
				
			}
		} catch(SQLException e) {
			// e.printStackTrace();
			System.err.println("Ocorreu um erro no acesso ao banco de dados: " + e.getMessage());
		}
		
		return pessoaJuridica;
	}
	
	public PessoaJuridica buscarPessoaJuridica(String cnpj) {
		
		PessoaJuridica pessoaJuridica = null;
		String sql = "SELECT * FROM cliente_pessoa_juridica WHERE cnpj = '" + cnpj + "'";
		try (
				Connection connection = JdbcAcesso.connect();
				Statement sttm = connection.createStatement();
				ResultSet rs = sttm.executeQuery(sql)
				) {
			
			if(rs.next()) {
				
				int id = rs.getInt("id");
				String razaoSocial = rs.getString("razao_social");
				String inscricaoEstadual = rs.getString("inscricao_estadual");
				
				ClienteDAO clDao = new ClienteDAO();
				Cliente cliente = clDao.buscarCliente(id);
				
				if(cliente != null) {
					pessoaJuridica = new PessoaJuridica(cliente, cnpj, razaoSocial, inscricaoEstadual);
				}
				
			}
		} catch(SQLException e) {
			// e.printStackTrace();
			System.err.println("Ocorreu um erro no acesso ao banco de dados: " + e.getMessage());
		}
		
		return pessoaJuridica;
	}
}
