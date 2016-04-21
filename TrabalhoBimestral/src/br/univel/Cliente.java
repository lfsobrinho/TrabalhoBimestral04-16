package br.univel;

import br.univel.anotation.*;

@Tabela("CAD_CLIENTE")
public class Cliente {

	@Coluna(pk=true)
	private int id;

	@Coluna(nome="CLNOME")
	private String nome;
	
	@Coluna(nome="CLENDERECO")
	private String end;
	
	@Coluna(nome="CLTELEFONE")
	private String phone;
	
	
	public Cliente() {
		this(0, null);
	}

	public Cliente(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}




}
