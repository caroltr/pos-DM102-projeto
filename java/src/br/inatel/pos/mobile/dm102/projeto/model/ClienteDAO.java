package br.inatel.pos.mobile.dm102.projeto.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.inatel.pos.mobile.dm102.projeto.controller.JdbcAcesso;

public class ClienteDAO {
	
	/**
	 * Busca o ID cujo determinado cliente está cadastrado no banco de dados
	 * @param cliente
	 * @return
	 */
	public Integer buscarClienteId(Cliente cliente) {
		
        String sql = "SELECT id FROM cliente WHERE nome = '" + cliente.getNome() + "'";
 
        try (Connection conn = JdbcAcesso.connect();
                Statement sttm = conn.createStatement();
        		ResultSet rs = sttm.executeQuery(sql)) {
        	
        	if(rs.next()) {
        		return rs.getInt("id");
        	}
 
        } catch (SQLException e) {
        	//e.printStackTrace();
			System.err.println("Ocorreu um erro no acesso ao banco de dados: " + e.getMessage());
        }
        
        return null;
    }
	
	/**
	 * Busca um cliente cadastrado no banco de dados sob determindo ID
	 * @param id
	 * @return
	 */
	public Cliente buscarCliente(int id) {
		
		String sql = "SELECT * FROM cliente WHERE id = " + id;
		
		try (
				Connection connection = JdbcAcesso.connect();
				Statement sttm = connection.createStatement();
				ResultSet rs = sttm.executeQuery(sql);
				) {
			
			if(rs.next()) {
				
				String cNome = rs.getString("nome");
				String cEndereco = rs.getString("endereco");
				String cTelefone = rs.getString("telefone");
				
				return new Cliente(cNome, cEndereco, cTelefone);
				
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println("Ocorreu um erro no acesso ao banco de dados: " + e.getMessage());
		}
		
		return null;
	}
}
