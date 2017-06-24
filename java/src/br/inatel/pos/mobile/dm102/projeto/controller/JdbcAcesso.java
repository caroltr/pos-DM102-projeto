package br.inatel.pos.mobile.dm102.projeto.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcAcesso {

	private static final String url = "jdbc:postgresql://localhost:5432/exemplo";
	private static final String username = "postgres";
	private static final String password = "162321ctr";
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
     * Conecta ao banco de dados PostgreSQL
     *
     * @return um objeto Connection
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
 
    /**
     * Fecha um objeto PreparedStatement
     * @param psttm
     */
    public static void  psttmClose(PreparedStatement psttm) {
    	try {
    		if(psttm != null) {
    			psttm.close();
    		}
		} catch (Exception e) {
			/* ignored */
		}
    }
 
    /**
     * Fecha um objeto ResultSet
     * @param rs
     */
    public static void  rsClose(ResultSet rs) {
        try {
        	if(rs != null) {
        		rs.close();
        	}
    	} catch (Exception e) {
        	/* ignored */
    	}
    }
}
