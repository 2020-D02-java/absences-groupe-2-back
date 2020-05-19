/**
 * 
 */
package dev.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.dto.ErreurDto;
import dev.controller.dto.SoldeDto;
import dev.exceptions.CollegueAuthentifieNonRecupereException;
import dev.services.SoldeService;

/** Controller de l'entité Solde
 *
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
/** Représentation 
 *
 * @author KOMINIARZ Anaïs
 *
 */
@RestController
@RequestMapping("soldes")
public class SoldeController {
	
	private SoldeService soldeService;

	/** Constructeur
	 *
	 * @param soldeService
	 */
	public SoldeController(SoldeService soldeService) {
		this.soldeService = soldeService;
	} 
	
	/**
	 *  LISTER LES SOLDES 
	 *  
	 * @return une liste de soldes Dto
	 */
	@GetMapping
	public List<SoldeDto> listerSoldesCollegue() {
		return soldeService.listerSoldesCollegue();
	}
	
	// ----------------------------- //
	// ---- GESTION DES ERREURS ---- //
	// ----------------------------- //
	
    @ExceptionHandler(CollegueAuthentifieNonRecupereException.class)
  	public ResponseEntity<ErreurDto> quandCollegueByEmailNotExistException(CollegueAuthentifieNonRecupereException ex) {
    	ErreurDto erreurDto = new ErreurDto();
    	erreurDto.setMessage(ex.getMessage());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurDto);
  	}

}
