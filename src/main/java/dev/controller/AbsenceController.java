/**
 * 
 */
package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
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

import dev.controller.dto.AbsenceDemandeDto;
import dev.controller.dto.AbsenceManagerVisualisationDto;
import dev.controller.dto.AbsenceVisualisationDto;
import dev.controller.dto.AbsenceVisualisationEmailCollegueDto;
import dev.controller.dto.ErreurDto;
import dev.entites.Statut;
import dev.exceptions.AbsenceChevauchementException;
import dev.exceptions.DateDansLePasseOuAujourdhuiException;
import dev.exceptions.AbsenceDateFinAvantDateDebutException;
import dev.exceptions.AbsenceMotifManquantCongesSansSoldeException;
import dev.exceptions.CollegueAuthentifieNonRecupereException;
import dev.services.AbsenceService;

/**
 * Controller de l'entité Absence
 *
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@RestController
@RequestMapping("absences")
@EnableScheduling
public class AbsenceController {

	// Déclarations
	private AbsenceService absenceService;

	/**
	 * Constructeur
	 *
	 * @param absenceService
	 */
	public AbsenceController(AbsenceService absenceService) {
		this.absenceService = absenceService;
	}

	/**
	 * LISTER ABSENCES
	 * 
	 * @param id
	 * @return une liste d'absences Dto
	 */
	@GetMapping
	public List<AbsenceVisualisationDto> listerAbsencesCollegue() {
		return absenceService.listerAbsencesCollegue();
	}
	
	/**
	 * 
	 * LISTER TOUTES LES ABSENCES
	 */
	@GetMapping("/all")
	public List<AbsenceVisualisationEmailCollegueDto> listerToutesAbsencesCollegue() {
		return absenceService.listerToutesAbsencesCollegue();
	}


	/**
	 * RECUPERER UNE ABSENCE VIA L'ID
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/id")
	public AbsenceVisualisationDto getAbsenceParId(@RequestParam("id") Integer id) {
		return this.absenceService.getAbsenceParId(id);
	}
	
	/**
	 * RECUPERER UNE ABSENCE VIA LE STATUT
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/statut")
	public List<AbsenceManagerVisualisationDto> getAbsenceParStatut(@RequestParam("statut") Statut statut) {
		return this.absenceService.getAbsenceParStatut(statut);
	}

	/**
	 * MODIFICATION D'UNE ABSENCE
	 * 
	 * @param absenceDto
	 * @param id
	 * @return
	 */
	@PutMapping("/modification")
	public AbsenceVisualisationDto putAbsence(@RequestBody @Valid AbsenceVisualisationDto absenceDto, @RequestParam("id") Integer id) {
		return this.absenceService.putAbsence(absenceDto, id);
	}
	
	/**
	 * VALIDATION D'UNE ABSENCE
	 * 
	 * @param absenceDto
	 * @param id
	 * @return
	 */
	@PutMapping
	public AbsenceVisualisationDto putValidationAbsence(@RequestBody @Valid AbsenceVisualisationDto absenceDto, @RequestParam("id") Integer id) {
		return this.absenceService.putValidationAbsence(absenceDto, id);
	}
	
	/**
	 * REUFUSER UNE ABSENCE
	 * 
	 * @param absenceDto
	 * @param id
	 * @return
	 */
	@PutMapping("/refuser")
	public AbsenceVisualisationDto putRefuserAbsence(@RequestBody @Valid AbsenceVisualisationDto absenceDto, @RequestParam("id") Integer id) {
		return this.absenceService.putRefuserAbsence(absenceDto, id);
	}

	/**
	 * SUPPRESSION D'UNE ABSENCE VIA L'ID
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping
	@RequestMapping(value = "/delete")
	@CrossOrigin
	public String supprimerAbsence(@RequestParam("id") Integer id) {

		return this.absenceService.deleteAbsence(id);

	}

	/**
	 * TRAITEMENT DE NUIT
	 */ 
	//@Scheduled(cron="00 00 21 * * *", zone="Europe/Paris")
	@PostMapping("/traitement-de-nuit")
	public void traitementDeNuit() {
		absenceService.traitementDeNuit();
	}

	/**
	 * DEMANDE D'ABSENCE
	 * 
	 * @param absenceDto
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> demandeAbsence(@RequestBody AbsenceDemandeDto absenceDto) {
		AbsenceDemandeDto saveAbsence = absenceService.demandeAbsence(absenceDto);
		return ResponseEntity.status(HttpStatus.ACCEPTED).header("resultat", "l'absence a été créée").body(saveAbsence);
	}

	// ----------------------------- //
	// ---- GESTION DES ERREURS ---- //
	// ----------------------------- //

	// Cas où on ne récupère pas le collègue authentifié
	@ExceptionHandler(CollegueAuthentifieNonRecupereException.class)
	public ResponseEntity<ErreurDto> quandCollegueByIdNotExistException(CollegueAuthentifieNonRecupereException ex) {
		ErreurDto erreurDto = new ErreurDto();
		erreurDto.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
	}

	// Cas jour saisi dans le passé ou aujourd'hui, erreur
	@ExceptionHandler(DateDansLePasseOuAujourdhuiException.class)
	public ResponseEntity<ErreurDto> quandDateDansLePasseOuAujourdhuiException(DateDansLePasseOuAujourdhuiException ex) {
		ErreurDto erreurDto = new ErreurDto();
		erreurDto.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
	}

	// Cas DateFin < DateDebut
	@ExceptionHandler(AbsenceDateFinAvantDateDebutException.class)
	public ResponseEntity<ErreurDto> quandAbsenceDateFinAvandDateDebutException(AbsenceDateFinAvantDateDebutException ex) {
		ErreurDto erreurDto = new ErreurDto();
		erreurDto.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
	}

	// Cas congès sans solde, et motif manquant
	@ExceptionHandler(AbsenceMotifManquantCongesSansSoldeException.class)
	public ResponseEntity<ErreurDto> quandAbsenceMotifManquantException(AbsenceMotifManquantCongesSansSoldeException ex) {
		ErreurDto erreurDto = new ErreurDto();
		erreurDto.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
	}

	// Impossible de saisir une demande qui chevauche une autre sauf si celle-ci est
	// en statut REJETEE
	@ExceptionHandler(AbsenceChevauchementException.class)
	public ResponseEntity<ErreurDto> quandAbsenceChevauchementException(AbsenceChevauchementException ex) {
		ErreurDto erreurDto = new ErreurDto();
		erreurDto.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
	}

}
