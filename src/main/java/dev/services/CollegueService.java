/**
 * 
 */
package dev.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.controller.dto.CollegueDto;
import dev.entites.Collegue;
import dev.repository.CollegueRepo;

/**
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@Service
public class CollegueService {

	// Déclarations
	private CollegueRepo collegueRepo;

	/**
	 * Constructeur
	 *
	 * @param absenceRepository
	 */
	public CollegueService(CollegueRepo collegueRepo) {
		this.collegueRepo = collegueRepo;
	}

	/**
	 * Lister tous les collegues
	 * 
	 * @return
	 */
	public List<CollegueDto> getAllCollegues() {
		List<CollegueDto> listeCollegues = new ArrayList<>();

		for (Collegue collegue : collegueRepo.findAll()) {

			CollegueDto collegueDto = new CollegueDto();
			collegueDto.setEmail(collegue.getEmail());
			collegueDto.setNom(collegue.getNom());
			collegueDto.setPrenom(collegue.getPrenom());
			listeCollegues.add(collegueDto);

		}
		return listeCollegues;
	}

}
