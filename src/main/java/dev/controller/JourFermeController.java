/**
 * 
 */
package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.dto.JourFermeDto;
import dev.entites.JourFerme;
import dev.exceptions.DateDansLePasseException;
import dev.services.JourFermeService;

@RestController
@RequestMapping("jourFerme")
public class JourFermeController {

	private JourFermeService jourFermeService;

	public JourFermeController(JourFermeService jourFermeService) {
		this.jourFermeService = jourFermeService;
	}

//	@GetMapping(("{id}"))
//	public List<JourFermeDto> listerJourFerme(@PathVariable Long id){
//		return jourFermeService.listerJourFerme(id);
//	}
	
//	@GetMapping
//	public List<JourFerme> getAllJourFermes() {
//
//		return this.jourFermeService.getAllJourFermes();
//	}
	
	@GetMapping
	public List<JourFerme> getJourFermesParDate(@RequestParam Integer annee) {
		return this.jourFermeService.getJourFermesParDate(annee);
	}
	 
	@PostMapping
	public JourFerme postJourFerme(@RequestBody @Valid JourFermeDto jourFermeDto) {
		return this.jourFermeService.postJourFerme(jourFermeDto);
	}
	
	@ExceptionHandler(DateDansLePasseException.class)
	public ResponseEntity<String> onDateDansLePasseException(DateDansLePasseException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
}