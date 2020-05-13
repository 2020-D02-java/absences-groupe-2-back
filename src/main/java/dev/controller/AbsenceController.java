/**
 * 
 */
package dev.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.dto.AbsenceDemandeDto;
import dev.controller.dto.AbsenceVisualisationDto;
import dev.controller.dto.ErreurDto;
import dev.exceptions.CollegueByEmailNotExistException;
import dev.repository.AbsenceRepo;
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
	public List<AbsenceVisualisationDto> listerAbsencesCollegue(){
		return absenceService.listerAbsencesCollegue();
	}
	 
	@PostMapping(value = "/traitement-de-nuit")
	public void traitementDeNuit() {
		absenceService.traitementDeNuit();
	}
	
	@PostMapping
	public ResponseEntity<?> demandeAbsence(@RequestBody AbsenceDemandeDto absenceDto) {
			AbsenceDemandeDto saveAbsence = absenceService.demandeAbsence(absenceDto);
		return ResponseEntity.status(HttpStatus.ACCEPTED).header("resultat", "l'absence a été créée").body(saveAbsence);
	}
	
	//Gestion des erreurs
    @ExceptionHandler(CollegueByEmailNotExistException.class)
  	public ResponseEntity<ErreurDto> quandCollegueByIdNotExistException(CollegueByEmailNotExistException ex) {
    	ErreurDto erreurDto = new ErreurDto();
    	erreurDto.setMessage(ex.getMessage());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
  	}
    
    
	
}
