package br.univel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
	
		private Connection con = null;		

		public Connection abrirConexao() throws SQLException {

			String url = "jdbc:mysql://localhost/trabalho";
			String user = "root";
			String pass = "123";
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			con = DriverManager.getConnection(url, user, pass);
		
			return con;
		}	
		
		public void fecharConexao() throws SQLException {
			con.close();
		}
	}

