package br.univel;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.univel.anotation.Coluna;
import br.univel.anotation.Tabela;

public class Execute extends SqlGen {
	public Execute() {
	}

	@Override
	protected String getCreateTable(Connection con, Object obj) {
		try {
			String nomeTabela;
			Class<?> cl = obj.getClass();
			StringBuilder sb = new StringBuilder();

			if (cl.isAnnotationPresent(Tabela.class)) {
				Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
				nomeTabela = anotacaoTabela.value();
			} else {
				nomeTabela = cl.getSimpleName().toUpperCase();
			}
			sb.append("CREATE TABLE ").append(nomeTabela).append(" (");

			Field[] atributos = cl.getDeclaredFields();

			for (int i = 0; i < atributos.length; i++) {
				Field field = atributos[i];

				String nomeColuna;
				String tipoColuna;

				if (field.isAnnotationPresent(Coluna.class)) {
					Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

					if (anotacaoColuna.nome().isEmpty()) {
						nomeColuna = field.getName().toUpperCase();
					} else {
						nomeColuna = anotacaoColuna.nome();
					}
				} else {
					nomeColuna = field.getName().toUpperCase();
				}

				Class<?> tipoParametro = field.getType();

				if (tipoParametro.equals(String.class)) {
					tipoColuna = "VARCHAR(100)";

				} else {
					if (tipoParametro.equals(int.class)) {
						tipoColuna = "INT";
					} else {
						tipoColuna = "DESCONHECIDO";
					}

					if (i > 0) {
						sb.append(",");
					}

					sb.append("\n\t").append(nomeColuna).append(' ').append(tipoColuna);
				}
			}
			// Declaraçao da PK

			sb.append(",\n\tPRYMARY KEY( ");

			for (int i = 0, achou = 0; i < atributos.length; i++) {
				Field field = atributos[i];

				if (field.isAnnotationPresent(Coluna.class)) {

					Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

					if (anotacaoColuna.pk()) {

						if (achou > 0) {
							sb.append(", ");
						}

						if (anotacaoColuna.nome().isEmpty()) {
							sb.append(field.getName().toUpperCase());
						} else {
							sb.append(anotacaoColuna.nome());
						}

						achou++;
					}
				}
				sb.append(" )");
			}
			sb.append("\n);");

			return sb.toString();

		} catch (SecurityException e) {
			try {
				throw new Exception(e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	@Override
	protected String getDropTable(Connection con, Object obj) {
		return null;

	}

	@Override
	protected PreparedStatement getSqlInsert(Connection con, Object obj) {

		Class<? extends Object> cl = obj.getClass();
		
		StringBuilder sb = new StringBuilder();
		
		//Declarando a tabela
		
		String nomeTabela;
		if(cl.isAnnotationPresent(Tabela.class)) {
			
			Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
			nomeTabela = anotacaoTabela.value();
			
		} else {
			nomeTabela = cl.getSimpleName().toUpperCase();
		}
		sb.append("INSERT INTO ").append(nomeTabela).append(" (");
		
		Field[] atributos = cl.getDeclaredFields();
		
		for (int i = 0; i < atributos.length; i++) {
			
			Field field = atributos[i];
			
			String nomeColuna;
			
			if (field.isAnnotationPresent(Coluna.class)) {
				Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
				
				if (anotacaoColuna.nome().isEmpty()) {
					nomeColuna = field.getName().toUpperCase();
				} else {
					nomeColuna = anotacaoColuna.nome();
				}
		
			} else {
				nomeColuna = field.getName().toUpperCase();
			}
			
			if (i > 0) {
				sb.append(", ");
			}
			
			sb.append(nomeColuna);
		}
		
		sb.append(") VALUES (");
		
		for (int i = 0; i < atributos.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append('?');
		}
		sb.append(')');
		
		String strSql = sb.toString();
		System.out.println("SQL GERADO: " + strSql);
		
		// comeca a coocar o valor do prepared statement
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(strSql);
			
			for (int i = 0; i < atributos.length; i++) {
				Field field = atributos[i];
				
				// importante nao esquecer
				field.setAccessible(true);
				
				if (field.getType().equals(int.class)) {
					ps.setInt(i + 1, field.getInt(obj));
					
				}else{
					if (field.getType().equals(String.class)) {
						ps.setString(i + 1, String.valueOf(field.get(obj)));
						
					}else{
						throw new RuntimeException("Tipo nao suportado , falta implementar");
						
					}
					
				}
			} catch (SQLException e){
				e.printStackTrace();
			}catch (IllegalArgumentException e) {
				e.printStackTrace();
			}catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		return ps;
		
	}

	@Override
	protected PreparedStatement getSqlSelectAll(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlSelectById(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlUpdateById(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlDeleteById(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
