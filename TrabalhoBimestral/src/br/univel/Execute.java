package br.univel;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import br.univel.anotation.*;

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
				
			}
		}
			
			
			
			
		return null;
	}

	@Override
	protected String getDropTable(Connection con, Object obj) {
		// TODO Auto-generated method stub
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
