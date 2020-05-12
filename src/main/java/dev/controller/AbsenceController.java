/**
 * 
 */
package dev.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.dto.AbsenceDto;
import dev.controller.dto.ErreurDto;
import dev.entites.Absence;
import dev.entites.Collegue;
import dev.exceptions.CollegueByEmailNotExistException;
import dev.services.AbsenceService;

/** Controller de l'entité Absence
 *
 * @author KOMINIARZ Anaïs
 *
 */
@RestController
@RequestMapping("absences")
public class AbsenceController {

	
	private AbsenceService absenceService;

	/** Constructeur
	 *
	 * @param absenceService
	 */
	public AbsenceController(AbsenceService absenceService) {
		this.absenceService = absenceService;
	}
	
	/**
	 * @param id
	 * @return une liste d'absence Dto
	 */
	@GetMapping
	public List<AbsenceDto> listerAbsencesCollegue(){
		return absenceService.listerAbsencesCollegue();
	}
	 
	
	@PostMapping
	public ResponseEntity<?> demandeAbsence(@RequestBody AbsenceDto absenceDto) {
			AbsenceDto saveAbsence = absenceService.demandeAbsence(absenceDto);
		return ResponseEntity.status(HttpStatus.ACCEPTED).header("resultat", "la réservation a été crée").body(saveAbsence);
	}
	
	//Gestion des erreurs
    @ExceptionHandler(CollegueByEmailNotExistException.class)
  	public ResponseEntity<ErreurDto> quandCollegueByIdNotExistException(CollegueByEmailNotExistException ex) {
    	ErreurDto erreurDto = new ErreurDto();
    	erreurDto.setMessage(ex.getMessage());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
  	}
	
}
