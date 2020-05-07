/**
 * 
 */
package dev.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.controller.dto.AbsenceDto;
import dev.controller.dto.ErreurDto;
import dev.entites.Absence;
import dev.entites.Collegue;
import dev.exceptions.CollegueByEmailNotExistException;
import dev.repository.AbsenceRepo;
import dev.repository.CollegueRepo;

/** Service de l'entité Absence
 *
 * @author KOMINIARZ Anaïs
 *
 */
@Service
public class AbsenceService {

	private AbsenceRepo absenceRepository;
	private CollegueRepo collegueRepository;
	
	/** Constructeur
	 *
	 * @param absenceRepository
	 */
	public AbsenceService(AbsenceRepo absenceRepository, CollegueRepo collegueRepository) {
		this.absenceRepository = absenceRepository;
		this.collegueRepository = collegueRepository;
	}
	
	/**
	 * @param collegue : Collegue
	 * @return la liste des absences du collègue dont l'id est passé en paramètres
	 */
	public List<AbsenceDto> listerAbsencesCollegue(String email) {
		
		//Vérification que l'id correspond bien à un collègue
		Optional<Collegue> optionnalCollegue = collegueRepository.findByEmail(email);
		if (!optionnalCollegue.isPresent()) {
			throw new CollegueByEmailNotExistException("L'email selectionne ne correspond a aucun collegue");
		}
		
		List<AbsenceDto> listeAbsences = new ArrayList<>();
		
		for (Absence absence : absenceRepository.findAll()) {
			if (absence.getCollegue().getEmail().equals(email)) {
				AbsenceDto absenceDto = new AbsenceDto();
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
