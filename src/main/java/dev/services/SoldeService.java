package dev.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.controller.dto.SoldeDto;
import dev.entites.Solde;
import dev.repository.SoldeRepo;

/**
 * Service de l'entité Solde
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@Service
public class SoldeService {

	// Déclarations
	private SoldeRepo soldeRepository;

	/**
	 * Constructeur
	 *
	 * @param soldeRepository
	 */
	public SoldeService(SoldeRepo soldeRepository) {
		this.soldeRepository = soldeRepository;
	}

	/**
	 * @param id
	 * @return la liste des soldes du collègue authentifié
	 */
	public List<SoldeDto> listerSoldesCollegue() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		List<SoldeDto> listeSoldes = new ArrayList<>();

		for (Solde solde : soldeRepository.findAll()) {
			if (solde.getCollegue().getEmail().equals(email)) {
				SoldeDto soldeDto = new SoldeDto(solde.getType(), solde.getNombreDeJours());

				listeSoldes.add(soldeDto);
			}
		}
		return listeSoldes;
	}

}
