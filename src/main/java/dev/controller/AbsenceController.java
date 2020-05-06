/**
 * 
 */
package dev.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.dto.AbsenceDto;
import dev.controller.dto.ErreurDto;
import dev.exceptions.CollegueByIdNotExistException;
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
	@GetMapping(("{id}"))
	public List<AbsenceDto> listerAbsencesCollegue(@PathVariable Integer id){
		return absenceService.listerAbsencesCollegue(id);
	}
	 
	
	//Gestion des erreurs
    @ExceptionHandler(CollegueByIdNotExistException.class)
  	public ResponseEntity<ErreurDto> quandRechercherCollegueParNomException(CollegueByIdNotExistException ex) {
    	ErreurDto erreurDto = new ErreurDto();
    	erreurDto.setMessage(ex.getMessage());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
  	}
	
}
