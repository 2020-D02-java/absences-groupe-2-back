/**
 * 
 */
package dev.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.controller.dto.JourFermeDto;
import dev.entites.JourFerme;
import dev.exceptions.CommentaireManquantJourFerieException;
import dev.exceptions.DateDansLePasseException;
import dev.repository.JourFermeRepo;

/**Service de l'entité Jour Ferme
 *
 * @author BATIGNES Pierre
 *
 */
@Service
public class JourFermeService {

	private JourFermeRepo jourFermeRepository;

	/**
	 * Constructeur
	 *
	 * @param absenceRepository
	 */
	public JourFermeService(JourFermeRepo jourFermeRepository) {
		this.jourFermeRepository = jourFermeRepository;
	}

	public List<JourFerme> getAllJourFermes() {

		return this.jourFermeRepository.findAll();
	}
	
	public List<JourFerme> getJourFermesParDate(Integer annee) {
		List<JourFerme> listJourFerme = this.jourFermeRepository.findAll();
		List<JourFerme> list = new ArrayList<>();
		
		for(JourFerme jour: listJourFerme) {
			if(jour.getDate().getYear() == annee) {
				list.add(jour);
			}
		}
		
		
		return list;
	}
	
//	public List<JourFermeDto> listerJourFerme(Long id) {
//
//		List<JourFermeDto> listeJourFerme = new ArrayList<>();
//
//		for (JourFerme jourFerme : jourFermeRepository.findAll()) {
//			if (jourFerme.getId() == id) {
//				JourFermeDto jourFermeDto = new JourFermeDto();
//				jourFermeDto.setDate(jourFerme.getDate());
//				jourFermeDto.setTypeJourFerme(jourFerme.getTypeJourFerme());
//				listeJourFerme.add(jourFermeDto);
//			}
//		}
//		return listeJourFerme;
//	}

	@Transactional
	public JourFerme postJourFerme(@Valid JourFermeDto jourFermeDto) {
		JourFerme jourFerme = new JourFerme(jourFermeDto.getDate(),jourFermeDto.getTypeJourFerme(),jourFermeDto.getCommentaire());
		
		if(jourFerme.getDate().isBefore(LocalDate.now()))  // Cas jour saisi dans le passé, erreur
		{
			throw new DateDansLePasseException("Il n'est pas possible de saisir une date dans le passé.");
		} 
		else if(jourFerme.getTypeJourFerme().toString().equals("JOURS_FERIES") && jourFerme.getCommentaire().isEmpty()) // Cas jour ferié selectionné, et commentaire manquant
		{
			throw new CommentaireManquantJourFerieException("Un commentaire est necessaire lors de la saisie d'un jour ferié.");
		} 
		else // cas passant
		{
			this.jourFermeRepository.save(jourFerme);
			return jourFerme;
		}
		
		

	}
}