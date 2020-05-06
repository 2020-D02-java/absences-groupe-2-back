/**
 * 
 */
package dev.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.dto.AbsenceDto;
import dev.services.AbsenceService;

/** Controller des absences
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
	 
	
	
}
