package br.univel;

import br.univel.anotation.*;
import br.univel.enu.*;

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
	
	@Coluna(nome="ESTADOCIVIL")
	private EstadoCivil ec;

	public Cliente(int id, String nome , String end , String phone) {
		super();
		this.id = id;
		this.nome = nome;
		this.end = end;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}




}
