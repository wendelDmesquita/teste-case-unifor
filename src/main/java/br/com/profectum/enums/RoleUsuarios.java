package br.com.profectum.enums;

import lombok.Getter;

@Getter
public enum RoleUsuarios {
	USUARIO_MASTER("USUARIO"),
	ADMIN("ADMIN"),
	COORDENADOR_CURSO("COORDENADOR"),
	PROFESSOR("PROFESSOR"),
	ALUNO("ALUNO");
	
	private String role;
	
	RoleUsuarios(String role){
		this.role = role;
	}
}
