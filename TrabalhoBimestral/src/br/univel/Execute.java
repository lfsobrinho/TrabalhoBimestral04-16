package br.univel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
					if (tipoParametro.equals(int.class) || tipoParametro.isEnum()) {
						tipoColuna = "INT";
					} else {
						tipoColuna = "DESCONHECIDO";
					}

				}
				if (i > 0) {
					sb.append(",");
				}
				sb.append("\n\t").append(nomeColuna).append(' ').append(tipoColuna);
			}
			// Declaraçao da PK

			sb.append(",\n\tPRIMARY KEY( ");

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
				if (i == atributos.length - 1) 
					sb.append(" )");
			}
			sb.append("\n);");

			return sb.toString();

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected String getDropTable(Connection con, Object obj) {
			String nameTable;
			StringBuilder sb = new StringBuilder();

			Class<?> cl = obj.getClass();

			if (cl.isAnnotationPresent(Tabela.class)) {
				Tabela tab = cl.getAnnotation(Tabela.class);
				nameTable = tab.value();
			} else {
				nameTable = cl.getSimpleName().toUpperCase();
			}

			sb.append("DROP TABLE ").append(nameTable).append(";");
			String drop = sb.toString();

			System.out.println(drop);
			
			return drop;
	}

	@Override
	protected PreparedStatement getSqlInsert(Connection con, Object obj) {

		Class<? extends Object> cl = obj.getClass();

		StringBuilder sb = new StringBuilder();

		String nomeTabela;

		if (cl.isAnnotationPresent(Tabela.class)) {

			Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
			nomeTabela = anotacaoTabela.value();

		} else {
			nomeTabela = cl.getSimpleName().toUpperCase();
		}
		sb.append("INSET INTO ").append(nomeTabela).append(" (");

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
		System.out.println("SQL GERADO : " + strSql);

		PreparedStatement ps = null;
		try {
//
			ps = con.prepareStatement(strSql);
//
//			for (int i = 0; i < atributos.length; i++) {
//				Field field = atributos[i];
//
//				field.setAccessible(true);
//
//				if (field.getType().equals(int.class)) {
//					ps.setInt(i + 1, field.getInt(obj));
//				} else {
//					if (field.getType().equals(String.class)) {
//						ps.setString(i + 1, String.valueOf(field.get(obj)));
//					} else {
//						throw new RuntimeException("Tipo nao suportado , falta implementar");
//					}
//				}
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return ps;

	}

	@Override
	protected PreparedStatement getSqlSelectAll(Connection con, Object obj) {

		Class<?> cl = obj.getClass();
		StringBuilder sb = new StringBuilder();
		String nomeTab;

		if (cl.isAnnotationPresent(Tabela.class)) {
			nomeTab = cl.getAnnotation(Tabela.class).value();
		} else {
			nomeTab = cl.getSimpleName().toUpperCase();
		}
		sb.append("SELECT * FROM ").append(nomeTab).append(";");

		String select = sb.toString();
		System.out.println(select);
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(select);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}

	@Override
	protected PreparedStatement getSqlSelectById(Connection con, Object obj, int id) {
		Class<?> cl = obj.getClass();
		StringBuilder sb = new StringBuilder();
		String nomeTabela;

		Field[] atributos = cl.getDeclaredFields();

		for (int i = 0; i < atributos.length; i++) {
			Field field = atributos[i];
		}

		if (cl.isAnnotationPresent(Tabela.class)) {
			nomeTabela = cl.getAnnotation(Tabela.class).value();
		} else {
			nomeTabela = cl.getSimpleName().toUpperCase();
		}

		sb.append("SELECT * FROM ").append(nomeTabela).append(" WHERE ID=").append(id).append(";");
		String select = sb.toString();
		System.out.println(select);
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(select);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;

	}

	@Override
	protected PreparedStatement getSqlUpdateById(Connection con, Object obj, int id) {
		Class<?> cl = obj.getClass();
		StringBuilder sb = new StringBuilder();
		String nomeTabela;

		if (cl.isAnnotationPresent(Tabela.class)) {
			nomeTabela = cl.getAnnotation(Tabela.class).value();
		} else {
			nomeTabela = cl.getSimpleName().toUpperCase();
		}

		sb.append("UPDATE ").append(nomeTabela).append(" SET ");

		Field[] atributos = cl.getDeclaredFields();
		

		for (int i = 0; i < atributos.length; i++) {
			Field field = atributos[i];
			String nomeColuna;

			if (field.isAnnotationPresent(Coluna.class)) {
				Coluna coluna = field.getAnnotation(Coluna.class);
				if (coluna.nome().isEmpty()) {
					nomeColuna = field.getName().toUpperCase();
				} else {
					nomeColuna = coluna.nome();
				}
			} else {
				nomeColuna = field.getName().toUpperCase();
			}

			if (i > 0) {
				sb.append(", ");
			}

			sb.append(nomeColuna).append(" = ?");
		}
		sb.append(" WHERE ID = ").append(id);
		String update = sb.toString();
		System.out.println(update);

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(update);

			for (int i = 0; i < atributos.length; i++) {
				Field field = atributos[i];
				Object type = field.getType();

				field.setAccessible(true);

				if (type.equals(int.class)) {
					ps.setInt(i + 1, field.getInt(obj));
				} else{ 
					if (type.equals(String.class)) {
						ps.setString(i + 1, String.valueOf(field.get(obj)));
					} else {
						//tipo a implementar
					}	
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return ps;
	}

	@Override
	protected PreparedStatement getSqlDeleteById(Connection con, Object obj, int id) {
		PreparedStatement ps = null;
		try {
			Class<?> cl = obj.getClass();
			StringBuilder sb = new StringBuilder();
			String nameTable;

			if (cl.isAnnotationPresent(Tabela.class)) {
				nameTable = cl.getAnnotation(Tabela.class).value();
			} else {
				nameTable = cl.getSimpleName().toUpperCase();
			}

			sb.append("DELETE FROM ").append(nameTable).append(" WHERE ID = ").append(id).append(";");
			String delete = sb.toString();
			System.out.println(delete);

			ps = con.prepareStatement(delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
}
