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

import br.com.profectum.dto.CursoDTO;
import br.com.profectum.enums.ErrosEnum;
import br.com.profectum.exceptions.RegraNegocioException;
import br.com.profectum.model.Curso;
import br.com.profectum.services.CursoService;
import br.com.profectum.utils.ResponseErrosUtil;

@RestController
@RequestMapping(path = "/cursos")
public class CursoController {
	private CursoService service;

	public CursoController(CursoService service) {
		this.service = service;
	}
	
	@PostMapping(path = "/salvar")
	public ResponseEntity<Object> salvarCurso(@RequestBody CursoDTO dto) {
		try {
			Curso curso = service.converterDeDTO(dto);
			curso = service.criarCurso(curso);
			return new ResponseEntity<>(curso, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<Object> listarTodosOsCursos() {
		return service.listarTodosOsCursos();
	}
	
	@GetMapping(path = "/buscar")
	public ResponseEntity<Object> buscarCurso(@RequestParam UUID idCurso) {
		try {
			Optional<Curso> curso = service.buscarCursoPorId(idCurso);
			return ResponseEntity.ok(curso);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping(path = "/{idCurso}/atualizar")
	public ResponseEntity<? extends Object> atualizarCurso(@PathVariable UUID idCurso, @RequestBody CursoDTO dto) {
		if (service.verificarListaDeCursos().size() == 0)
			return ResponseErrosUtil.respostaErro004();
		
		if(service.verificarExistencia(idCurso) == false)
			return ResponseErrosUtil.respostaErro002();
		
		return service.buscarCursoPorId(idCurso).map(entidade -> {
			try {
				Curso curso = service.converterDeDTO(dto);
				curso.setIdCurso(entidade.getIdCurso());
				service.atualizarCurso(idCurso, curso);
				return ResponseEntity.ok(curso);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getLocalizedMessage());
			}
		}).orElseGet(() -> new ResponseEntity<Object>(ErrosEnum.ERRO_003.getMensagemErro(), HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping(path = "/deletar/{idCurso}")
	public ResponseEntity<? extends Object> deletarCurso(@PathVariable UUID idCurso) {
		if (service.verificarListaDeCursos().size() == 0)
			return ResponseErrosUtil.respostaErro004();
		
		if(service.verificarExistencia(idCurso) == false)
			return ResponseErrosUtil.respostaErro002();
		
		return service.buscarCursoPorId(idCurso).map(entidade -> {
			try {
				service.deletarCurso(idCurso);
				return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<Object>(ErrosEnum.ERRO_003.getMensagemErro(), HttpStatus.BAD_REQUEST));
	}
}