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
 * @author GIRARD Vincent
 *
 */
@Service
public class CollegueService {

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
	 * @return Lister les collegues
	 */
	public List<CollegueDto> getAllCollegues() {
		List<CollegueDto> listeCollegues = new ArrayList<>();
		//return listeCollegues;
		
		
		for (Collegue collegue : collegueRepo.findAll()) {

			CollegueDto collegueDto = new CollegueDto();

			// ID Necessaire pour la modale suppression front
//			collegueDto.setId(collegue.getId());
			collegueDto.setEmail(collegue.getEmail());
			collegueDto.setNom(collegue.getNom());
			collegueDto.setPrenom(collegue.getPrenom());
			listeCollegues.add(collegueDto);

		}
		return listeCollegues;
	}

}
