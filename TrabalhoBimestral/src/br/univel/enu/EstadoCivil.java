package br.univel.enu;

public enum EstadoCivil {
	SOLTEIRO , CASADO , VIUVO;

public static final EstadoCivil getPorCodigo(int value){
    for (EstadoCivil item : EstadoCivil.values()) {
        if (item.ordinal() == value) {
        	return item;
        }
    }
	throw new RuntimeException("Nao encontramos o valor : " + value);
	}
}