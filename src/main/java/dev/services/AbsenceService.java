/**
 * 
 */
package dev.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.controller.dto.AbsenceDto;
import dev.entites.Absence;
import dev.repository.AbsenceRepo;

/** Service des absences
 *
 * @author KOMINIARZ Anaïs
 *
 */
@Service
public class AbsenceService {

	private AbsenceRepo absenceRepository;
	
	/** Constructeur
	 *
	 * @param absenceRepository
	 */
	public AbsenceService(AbsenceRepo absenceRepository) {
		this.absenceRepository = absenceRepository;
	}
	
	/**
	 * @param collegue : Collegue
	 * @return la liste des absences du collègue passé en paramètres
	 */
	public List<AbsenceDto> listerAbsencesCollegue(Long idCollegue) {
		AbsenceDto absenceDto = new AbsenceDto();
		List<AbsenceDto> listeAbsences = new ArrayList<>();
		
		for (Absence absence : absenceRepository.findAll()) {
			if (absence.getCollegue().getId() == idCollegue) {
				absenceDto.setDateDebut(absence.getDateDebut());
				absenceDto.setDateFin(absence.getDateFin());
				absenceDto.setTypeAbsence(absence.getTypeAbsence());
				absenceDto.setStatut(absence.getStatut());
				listeAbsences.add(absenceDto);
			}
		}
		return listeAbsences;
	}
}
