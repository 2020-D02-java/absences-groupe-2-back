/**
 * 
 */
package dev.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.dto.CollegueDto;
import dev.services.CollegueService;

/**
 * Controller de l'entité collegue
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@RestController
@RequestMapping("collegue")
public class CollegueController {

	// Déclarations
	private CollegueService collegueService;

	/**
	 * Constructeur
	 * 
	 * @param collegueService
	 */
	public CollegueController(CollegueService collegueService) {
		this.collegueService = collegueService;
	}

	/**
	 * RECUPERER TOUS LES COLLEGUES
	 * 
	 * @return
	 */
	@GetMapping
	public List<CollegueDto> getAllCollegue() {
		return this.collegueService.getAllCollegues();
	}

}
