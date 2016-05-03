package br.univel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
	
		private Connection con = null;		

		public Connection abrirConexao() throws SQLException {

			String url = "";
			String user = "";
			String pass = "";
			
			try {
				Class.forName("org.h2.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			con = DriverManager.getConnection(url, user, pass);
		
			return con;
		}	
		
		public void fecharConexao() throws SQLException {
			con.close();
		}
	}

