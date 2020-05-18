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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.dto.ErreurDto;
import dev.controller.dto.JourFermeAjoutDto;
import dev.controller.dto.JourFermeVisualisationDto;
import dev.controller.dto.JourFermeVisualisationPlanningDto;
import dev.exceptions.CommentaireManquantJourFerieException;
import dev.exceptions.DateDansLePasseException;
import dev.exceptions.DeleteRttEmployeurDejaValideException;
import dev.exceptions.RttEmployeurUnWeekEndException;
import dev.exceptions.SaisieJourFerieUnJourDejaFerieException;
import dev.services.JourFermeService;

/**
 * Controller de l'entité jour ferme
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@RestController
@RequestMapping("jourFerme")
public class JourFermeController {

	// Déclarations
	private JourFermeService jourFermeService;

	/**
	 * Constructeur
	 * 
	 * @param jourFermeService
	 */
	public JourFermeController(JourFermeService jourFermeService) {
		this.jourFermeService = jourFermeService;
	}
	
	/**
	 * LISTER TOUS LES JOURS FERMES
	 * 
	 */
	@GetMapping
	public List<JourFermeVisualisationPlanningDto> listerJourFermes() {
		return jourFermeService.getAllJourFermes();
	}
	
	
	/**
	 * RECUPERER TOUS LES JOURS FERMES AVEC FILTRE ANNEE
	 * 
	 * @param annee
	 * @return
	 */
	@GetMapping("/date")
	public List<JourFermeVisualisationDto> getJourFermesParDate(@RequestParam Integer annee) {
		return this.jourFermeService.getJourFermesParDate(annee);
	}

	/**
	 * RECUPERER JOURS FERMES VIA ID
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/id")
	public JourFermeVisualisationDto getJourFermesParId(@RequestParam("id") Integer id) {
		return this.jourFermeService.getJourFermesParId(id);
	}

	/**
	 * MODIFICATION JOURS FERMES
	 * 
	 * @param jourFermeDto
	 * @param id
	 * @return
	 */
	@PutMapping("/modification")
	public JourFermeVisualisationDto putJourFerme(@RequestBody @Valid JourFermeVisualisationDto jourFermeDto, @RequestParam("id") Integer id) {
		return this.jourFermeService.putJourFerme(jourFermeDto, id);
	}

	/**
	 * AJOUTER JOURS FERMES
	 * 
	 * @param jourFermeDto
	 * @return
	 */
	@PostMapping
	public JourFermeAjoutDto postJourFerme(@RequestBody @Valid JourFermeAjoutDto jourFermeDto) {
		return this.jourFermeService.postJourFerme(jourFermeDto);
	}

	/**
	 * SUPPRESSION JOURS FERMES VIA ID
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping
	@RequestMapping(value = "/delete")
	@CrossOrigin
	public String supprimerJourFerme(@RequestParam("id") Integer id) {
		return this.jourFermeService.deleteJourFerme(id);
	}

	// ----------------------------- //
	// ---- GESTION DES ERREURS ---- //
	// ----------------------------- //
	
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
	@ExceptionHandler(RttEmployeurUnWeekEndException.class)
	public ResponseEntity<ErreurDto> onRttLeWeekEndException(RttEmployeurUnWeekEndException ex) {
		ErreurDto erreurDto = new ErreurDto();
		erreurDto.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
	}

	// il est interdit de saisir un jour férié à la même date qu'un autre jour férié
	@ExceptionHandler(SaisieJourFerieUnJourDejaFerieException.class) 
	public ResponseEntity<ErreurDto> onSaisieJourFerierSurJourDejaFerieException(SaisieJourFerieUnJourDejaFerieException ex) {
		ErreurDto erreurDto = new ErreurDto();
		erreurDto.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
	}

	// Il n'est pas possible de faire la suppression d'un RTT employeur déjà validé 
	@ExceptionHandler(DeleteRttEmployeurDejaValideException.class) 
	public ResponseEntity<ErreurDto> onDeleteRttEmployeurDejaValideException(DeleteRttEmployeurDejaValideException ex) {
		ErreurDto erreurDto = new ErreurDto();
		erreurDto.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
	}
}
