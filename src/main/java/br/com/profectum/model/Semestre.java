package br.com.profectum.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Semestre {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "id_semestre")
	private Long idSemestre;
	
	@Column(name = "nome_semestre")
	private String nomeSemestre;
	
	@Column (name = "parcial_horas")
	private Integer parcialHoras;
	
	@Column (name = "curso")
	private String curso;
	
	@ManyToMany
	private List<Disciplina> disciplinas;
}
