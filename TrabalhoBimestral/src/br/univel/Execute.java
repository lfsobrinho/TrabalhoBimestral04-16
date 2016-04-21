package br.univel;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;

import br.univel.anotation.Coluna;
import br.univel.anotation.Tabela;

public class Execute extends SqlGen {
	public Execute(){
	}

	@Override
	protected String getCreateTable(Connection con, Object obj) {
		try{
			String nomeTabela;
			Class<?> cl = obj.getClass();
			StringBuilder sb = new StringBuilder();
			
			if(cl.isAnnotationPresent(Tabela.class)){
				Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
				nomeTabela = anotacaoTabela.value();
			}else{
				nomeTabela = cl.getSimpleName().toUpperCase();
			}
			sb.append("CREATE TABLE ").append(nomeTabela).append(" (");
			
			Field[] atributos = cl.getDeclaredFields();
			
			for (int i=0; i < atributos.length; i++){
				Field field = atributos[i];
				
				String nomeColuna;
				String tipoColuna;
				
				if(field.isAnnotationPresent(Coluna.class)){
					Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
					
					if(anotacaoColuna.nome().isEmpty()){
						nomeColuna = field.getName().toUpperCase();
					}else{
						nomeColuna = anotacaoColuna.nome();				
					}
				}else{
					nomeColuna = field.getName().toUpperCase();
				}
				
				Class<?> tipoParametro = field.getType();
				
				if(tipoParametro.equals(String.class)){
					tipoColuna = "VARCHAR(100)";
					
				}else{
					if(tipoParametro.equals(int.class)){
						tipoColuna = "INT";
					}else{
						tipoColuna = "DESCONHECIDO";
					}
					
					if (i > 0){
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
						
						if (achou > 0){
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
			
		}catch (SecurityException e) {
			throw new Exception(e);
		}
	}

	@Override
	protected String getDropTable(Connection con, Object obj) {
		
		return null;
	}

	@Override
	protected PreparedStatement getSqlInsert(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
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
