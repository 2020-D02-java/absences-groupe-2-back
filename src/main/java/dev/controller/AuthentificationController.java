package dev.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.dto.CollegueDto;
import dev.repository.CollegueRepo;

/**
 * WEB API d'authentification.
 *
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@RestController
public class AuthentificationController {

	// Déclarations
	private CollegueRepo collegueRepo;

	/**
	 * Constructeur
	 * 
	 * @param collegueRepo
	 */
	public AuthentificationController(CollegueRepo collegueRepo) {
		this.collegueRepo = collegueRepo;
	}

	/**
	 * QUI SUIS-JE ?
	 * 
	 * @return
	 */
	@GetMapping("/me")
	public ResponseEntity<?> quiSuisJe() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return this.collegueRepo.findByEmail(email).map(CollegueDto::new).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
	}
}
