package br.univel;

import br.univel.anotation.*;

@Tabela("CAD_CLIENTE")
public class Cliente {

	@Coluna(pk=true)
	private int id;

	@Coluna(nome="CLNOME",tam=200)
	private String nome;
	
	@Coluna(nome="CLENDERECO",tam=200)
	private String end;
	
	@Coluna(nome="CLTELEFONE",tam=200)
	private String phone;
	
	
	public Cliente() {
		this(0, null);
	}

	public Cliente(int id, String nome , String end , String phone) {
		super();
		this.id = id;
		this.nome = nome;
	}




}
