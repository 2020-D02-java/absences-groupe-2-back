/**
 * 
 */
package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.dto.JourFermeDto;
import dev.entites.JourFerme;
import dev.exceptions.CommentaireManquantJourFerieException;
import dev.exceptions.DateDansLePasseException;
import dev.exceptions.JourRttUnWeekEndException;
import dev.exceptions.RttEmployeurDejaValideException;
import dev.exceptions.SaisieJourFeriesUnJourDejaFeriesException;
import dev.services.JourFermeService;

@RestController
@RequestMapping("jourFerme")
public class JourFermeController {

	private JourFermeService jourFermeService;

	public JourFermeController(JourFermeService jourFermeService ) {
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
	
	// ** SUPPRESSION JOUR FERME [via ID] **//
	@DeleteMapping
	@RequestMapping(value = "/delete")
	@CrossOrigin
	public String supprimerJourFerme(@RequestParam("id") Integer id) {

		return this.jourFermeService.deleteJourFerme(id);
        
	}
	
	
	// un jour férié ne peut pas être saisi dans le passé
	@ExceptionHandler(DateDansLePasseException.class)
	public ResponseEntity<String> onDateDansLePasseException(DateDansLePasseException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	// le commentaire est obligatoire pour les jours feriés.
	@ExceptionHandler(CommentaireManquantJourFerieException.class)
	public ResponseEntity<String> onCommentaireManquantJourFerieException(CommentaireManquantJourFerieException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	// il est interdit de saisir une RTT employeur un samedi ou un dimanche
	@ExceptionHandler(JourRttUnWeekEndException.class)
	public ResponseEntity<String> onRttLeWeekEndException(JourRttUnWeekEndException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	// il est interdit de saisir un jour férié à la même date qu'un autre jour férié
	@ExceptionHandler(SaisieJourFeriesUnJourDejaFeriesException.class)
	public ResponseEntity<String> onSaisieJourFerierSurJourDejaFerieException(SaisieJourFeriesUnJourDejaFeriesException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	// il est interdit de saisir un jour férié à la même date qu'un autre jour férié
	@ExceptionHandler(RttEmployeurDejaValideException.class)
	public ResponseEntity<String> onDeleteRttDejaValideException(RttEmployeurDejaValideException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
}
