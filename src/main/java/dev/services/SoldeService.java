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
	 * @return la liste des soldes du collègue dont l'id est passé en paramètres
	 */
	public List<SoldeDto> listerSoldesCollegue(Integer id){
		
		//Vérification que l'id correspond bien à un collègue
		Optional<Collegue> optionnalCollegue = collegueRepository.findById(id);
		if (!optionnalCollegue.isPresent()) {
			throw new CollegueByEmailNotExistException("L'id selectionne ne correspond a aucun collegue");
		}
		
		List<SoldeDto> listeSoldes = new ArrayList<>();
		
		for (Solde solde : soldeRepository.findAll()) {
			if (solde.getCollegue().getId() == id) {
				SoldeDto soldeDto = new SoldeDto();
				soldeDto.setTypeSolde(solde.getTypeSolde());
				soldeDto.setNombredeJours(solde.getNombreDeJours());
				
				listeSoldes.add(soldeDto);
			}
		}
		return listeSoldes;
	}
	

}
