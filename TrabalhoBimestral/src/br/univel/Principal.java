package br.univel;

import java.sql.SQLException;

import br.univel.enu.EstadoCivil;

public class Principal {

	public Principal() {

		Cliente c1 = new Cliente(0, null, null, null, null);
		c1.setId(1);
		c1.setNome("Jose");
		c1.setPhone("45999888");
		c1.setEc(EstadoCivil.SOLTEIRO);
		c1.setEnd("Rua Afalfa, 2000");

		Cliente c2 = new Cliente(0, null, null, null, null);
		c1.setId(2);
		c1.setNome("Lorizete");
		c1.setPhone("45888888");
		c1.setEc(EstadoCivil.SOLTEIRO);
		c1.setEnd("Rua Abacaxi, 120");

		Cliente c3 = new Cliente(0, null, null, null, null);
		c1.setId(3);
		c1.setNome("Kalvina");
		c1.setPhone("45777777");
		c1.setEc(EstadoCivil.VIUVO);
		c1.setEnd("Rua Batatinha, 150");
		ConexaoBD conexao = new ConexaoBD();

		DaoImpl d = new DaoImpl();

		try {
			d.setCon(conexao.abrirConexao());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("apagarTabela\n");
		d.apagarTabela(c1);

		System.out.println("criarTabela\n");
		d.criarTabela(c1);

		System.out.println("inserir objeto 1\n");
		d.salvar(c1);

		System.out.println("inserir objeto 2\n");
		d.salvar(c2);

		System.out.println("inserir objeto 3\n");
		d.salvar(c3);

		System.out.println("listarTodos");
		for (Cliente c : d.listarTodos()) {
			System.out.println(c.getId() + " - " + c.getNome() + " - " + c.getEnd() + " - " + c.getPhone() + " - "
					+ c.getEc().toString());
		}

		System.out.println("\nbuscar objeto 1");
		Cliente c4 = new Cliente(0, null, null, null, null);
		c4 = d.buscar(c1.getId());
		System.out.println(c4.getId() + " - " + c4.getNome() + " - " + c4.getEnd() + " - " + c4.getPhone() + " - "
				+ c4.getEc().toString());

		System.out.println("\nalterar objeto 2\n");
		c2.setEc(EstadoCivil.CASADO);
		d.atualizar(c2);

		System.out.println("excluir objeto 3\n");
		d.excluir(c3.getId());

		System.out.println("listarTodos");
		for (Cliente c : d.listarTodos()) {
			System.out.println(c.getId() + " - " + c.getNome() + " - " + c.getEnd() + " - " + c.getPhone() + " - "
					+ c.getEc().toString());
		}

		d.setCon(null);
		try {
			conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Principal();
	}
}
