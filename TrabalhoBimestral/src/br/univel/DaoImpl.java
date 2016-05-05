package br.univel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.univel.enu.EstadoCivil;

public class DaoImpl implements Dao<Cliente, Integer> {


	private Connection con = null;
	
	
	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	@Override
	public void salvar(Cliente c) {
		Execute ger = new Execute();
		
		try {

			PreparedStatement ps = ger.getSqlInsert(con, c);
			ps.setInt(1, c.getId());
			ps.setString(2, c.getNome());
			ps.setString(3, c.getEnd());
			ps.setString(4, c.getPhone());
			//ps.setInt(5, c.getEc().ordinal());
			ps.setInt(5, 0);
			
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}			
	}

	@Override
	public Cliente buscar(Integer k) {
		Execute ger = new Execute();
		Cliente c = new Cliente();
				
		try {

			PreparedStatement ps = ger.getSqlSelectById(con, new Cliente(), k);
			ps.setInt(1, k);
			ResultSet resultados = ps.executeQuery();
			
			while (resultados.next()) {
				c.setId(resultados.getInt("ID"));
				c.setNome(resultados.getString("CLNOME"));
				c.setEnd(resultados.getString("CLENDERECO"));
				c.setPhone(resultados.getString("CLTELEFONE"));
				c.setEc(EstadoCivil.getPorCodigo(resultados.getInt("ESTADOCIVIL")));
			}			
			
			ps.close();
			resultados.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		return c;
	}

	@Override
	public void atualizar(Cliente t) {
		Execute ger = new Execute();
			
		try {

			PreparedStatement ps = ger.getSqlUpdateById(con, t, 0);
			ps.setString(2, t.getNome());
			ps.setString(3, t.getEnd());
			ps.setString(4, t.getPhone());
			ps.setInt(5, t.getEc().ordinal());
			ps.setInt(1, t.getId());
			
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}		
	}

	@Override
	public void excluir(Integer k) {
		Execute ger = new Execute();
				
		try {

			PreparedStatement ps = ger.getSqlDeleteById(con, new Cliente(k, null, null, null, null), k);
			ps.setInt(1, k);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}		
	}

	@Override
	public List<Cliente> listarTodos() {
		Execute ger = new Execute();
		List<Cliente> listaCliente = new ArrayList<Cliente>();
		
		try {

			PreparedStatement ps = ger.getSqlSelectAll(con, new Cliente(0, null, null, null, null));
			ResultSet resultados = ps.executeQuery();
			
			while (resultados.next()) {
				
				Cliente c = new Cliente();
				c.setId(resultados.getInt("ID"));
				c.setNome(resultados.getString("CLNOME"));
				c.setEnd(resultados.getString("CLENDERECO"));
				c.setPhone(resultados.getString("CLTELEFONE"));
				c.setEc(EstadoCivil.getPorCodigo(resultados.getInt("ESTADOCIVIL")));
				
				listaCliente.add(c);
			}			
			
			ps.close();
			resultados.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		return listaCliente;		
	}

	public void criarTabela(Cliente t){
		Execute ger = new Execute();
		
		try {
			String sql = ger.getCreateTable(con, t);	
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}			
		
	}
	
	public void apagarTabela(Cliente t){
		Execute ger = new Execute();
				
		try {
			String sql = ger.getDropTable(con, t);	
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}			
		
	}	
	
	
}
