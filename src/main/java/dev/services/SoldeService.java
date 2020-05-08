/**
 * 
 */
package dev.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.controller.dto.SoldeDto;
import dev.entites.Collegue;
import dev.entites.Solde;
import dev.exceptions.CollegueByEmailNotExistException;
import dev.repository.CollegueRepo;
import dev.repository.SoldeRepo;

/** Service de l'entité Solde
 *
 * @author KOMINIARZ Anaïs
 *
 */
@Service
public class SoldeService {
	
	private SoldeRepo soldeRepository;
	private CollegueRepo collegueRepository;
	
	/** Constructeur
	 *
	 * @param soldeRepository
	 */
	public SoldeService(SoldeRepo soldeRepository, CollegueRepo collegueRepository) {
		this.soldeRepository = soldeRepository;
		this.collegueRepository = collegueRepository;
	}
	
	
	/**
	 * @param id
	 * @return la liste des soldes du collègue dont l'email est passé en paramètres
	 */
	public List<SoldeDto> listerSoldesCollegue(String email){
		
		//Vérification que l'email correspond bien à un collègue
		Optional<Collegue> optionnalCollegue = collegueRepository.findByEmail(email);
		if (!optionnalCollegue.isPresent()) {
			throw new CollegueByEmailNotExistException("L'email selectionne ne correspond a aucun collegue");
		}
		
		List<SoldeDto> listeSoldes = new ArrayList<>();
		
		for (Solde solde : soldeRepository.findAll()) {
			if (solde.getCollegue().getEmail().equals(email)) {
				SoldeDto soldeDto = new SoldeDto();
				soldeDto.setType(solde.getType());
				soldeDto.setNombreDeJours(solde.getNombreDeJours());
				
				listeSoldes.add(soldeDto);
			}
		}
		return listeSoldes;
	}
	

}
