/**
 * 
 */
package dev.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.controller.dto.JourFermeDto;
import dev.entites.JourFerme;
import dev.repository.JourFermeRepo;

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

		this.jourFermeRepository.save(jourFerme);
		return jourFerme;

	}
}
