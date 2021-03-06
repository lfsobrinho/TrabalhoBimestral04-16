package br.univel;

import br.univel.anotation.*;
import br.univel.enu.*;

@Tabela("CAD_CLIENTE")
public class Cliente {

	@Coluna(pk=true)
	private int ID;

	@Coluna(nome="CLNOME",tam=200)
	private String nome;
	
	@Coluna(nome="CLENDERECO",tam=200)
	private String end;
	
	@Coluna(nome="CLTELEFONE",tam=200)
	private String phone;
	
	@Coluna(nome="ESTADOCIVIL")
	private EstadoCivil ec;
	

	public Cliente(int id, String nome , String end , String phone , EstadoCivil ec) {
		super();
		this.ID = id;
		this.nome = nome;
		this.end = end;
		this.phone = phone;
		this.ec = ec;
	}

	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	public EstadoCivil getEc() {
		return ec;
	}

	public void setEc(EstadoCivil ec) {
		this.ec = ec;
	}

	public int getId() {
		return ID;
	}

	public void setId(int id) {
		this.ID = id;
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
