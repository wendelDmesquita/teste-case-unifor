package br.com.profectum.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.profectum.dto.DisciplinaDTO;
import br.com.profectum.enums.ErrosEnum;
import br.com.profectum.exceptions.RegraNegocioException;
import br.com.profectum.model.Disciplina;
import br.com.profectum.services.DisciplinaService;
import br.com.profectum.utils.ResponseErrosUtil;

@RestController
@RequestMapping(path = "disciplinas")
public class DisciplinaController {
	private DisciplinaService service;

	public DisciplinaController(DisciplinaService service) {
		this.service = service;
	}
	
	@PostMapping(path = "/salvar")
	public ResponseEntity<Object> salvarDisciplina(@RequestBody DisciplinaDTO dto) {
		try {
			Disciplina disciplina = service.converterDeDTO(dto);
			disciplina = service.criarDisciplina(disciplina);
			return new ResponseEntity<>(disciplina, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<Object> listarTodosOsDisciplinas() {
		return service.listarTodosOsDisciplinas();
	}
	
	@GetMapping(path = "/buscar")
	public ResponseEntity<Object> buscarDisciplina(@RequestParam UUID idDisciplina) {
		try {
			Optional<Disciplina> disciplina = service.buscarDisciplinaPorId(idDisciplina);
			return ResponseEntity.ok(disciplina);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping(path = "/{idDisciplina}/atualizar")
	public ResponseEntity<? extends Object> atualizarDisciplina(@PathVariable UUID idDisciplina, @RequestBody DisciplinaDTO dto) {
		if (service.verificarListaDeDisciplinas().size() == 0)
			return ResponseErrosUtil.respostaErro004();
		
		if(service.verificarExistencia(idDisciplina) == false)
			return ResponseErrosUtil.respostaErro002();
		
		return service.buscarDisciplinaPorId(idDisciplina).map(entidade -> {
			try {
				Disciplina disciplina = service.converterDeDTO(dto);
				disciplina.setIdDisciplina(entidade.getIdDisciplina());
				service.atualizarDisciplina(idDisciplina, disciplina);
				return ResponseEntity.ok(disciplina);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getLocalizedMessage());
			}
		}).orElseGet(() -> new ResponseEntity<Object>(ErrosEnum.ERRO_003.getMensagemErro(), HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping(path = "/deletar/{idDisciplina}")
	public ResponseEntity<? extends Object> deletarDisciplina(@PathVariable UUID idDisciplina) {
		if (service.verificarListaDeDisciplinas().size() == 0)
			return ResponseErrosUtil.respostaErro004();
		
		if(service.verificarExistencia(idDisciplina) == false)
			return ResponseErrosUtil.respostaErro002();
		
		return service.buscarDisciplinaPorId(idDisciplina).map(entidade -> {
			try {
				service.deletarDisciplina(idDisciplina);
				return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<Object>(ErrosEnum.ERRO_003.getMensagemErro(), HttpStatus.BAD_REQUEST));
	}
}