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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.dto.ErreurDto;
import dev.controller.dto.JourFermeAjoutDto;
import dev.controller.dto.JourFermeVisualisationDto;
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
	
	@GetMapping("/date")
	public List<JourFermeVisualisationDto> getJourFermesParDate(@RequestParam Integer annee) {
		return this.jourFermeService.getJourFermesParDate(annee);
	}
	
	@GetMapping("/id")
	public JourFermeVisualisationDto getJourFermesParId(@RequestParam("id") Integer id) {
		return this.jourFermeService.getJourFermesParId(id);
	}
	
	@PutMapping("/modification")
	public JourFermeVisualisationDto putJourFerme(@RequestBody @Valid JourFermeVisualisationDto jourFermeDto, @RequestParam("id") Integer id) {
		return this.jourFermeService.putJourFerme(jourFermeDto, id);
	}
	 
	@PostMapping
	public JourFermeAjoutDto postJourFerme(@RequestBody @Valid JourFermeAjoutDto jourFermeDto) {
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
	public ResponseEntity<ErreurDto> onDateDansLePasseException(DateDansLePasseException ex) {
    	ErreurDto erreurDto = new ErreurDto();
    	erreurDto.setMessage(ex.getMessage());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
	}

	// le commentaire est obligatoire pour les jours feriés.
	@ExceptionHandler(CommentaireManquantJourFerieException.class)
	public ResponseEntity<ErreurDto> onCommentaireManquantJourFerieException(CommentaireManquantJourFerieException ex) {
    	ErreurDto erreurDto = new ErreurDto();
    	erreurDto.setMessage(ex.getMessage());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
	}

	// il est interdit de saisir une RTT employeur un samedi ou un dimanche
	@ExceptionHandler(JourRttUnWeekEndException.class)
	public ResponseEntity<ErreurDto> onRttLeWeekEndException(JourRttUnWeekEndException ex) {
    	ErreurDto erreurDto = new ErreurDto();
    	erreurDto.setMessage(ex.getMessage());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
	}

	// il est interdit de saisir un jour férié à la même date qu'un autre jour férié
	@ExceptionHandler(SaisieJourFeriesUnJourDejaFeriesException.class)
	public ResponseEntity<ErreurDto> onSaisieJourFerierSurJourDejaFerieException(SaisieJourFeriesUnJourDejaFeriesException ex) {
    	ErreurDto erreurDto = new ErreurDto();
    	erreurDto.setMessage(ex.getMessage());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
	}
	
	// il est interdit de saisir un jour férié à la même date qu'un autre jour férié
	@ExceptionHandler(RttEmployeurDejaValideException.class)
	public ResponseEntity<ErreurDto> onDeleteRttDejaValideException(RttEmployeurDejaValideException ex) {
    	ErreurDto erreurDto = new ErreurDto();
    	erreurDto.setMessage(ex.getMessage());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
	}
}
