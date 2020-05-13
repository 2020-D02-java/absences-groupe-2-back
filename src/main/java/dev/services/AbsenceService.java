/**
 * 
 */
package dev.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.controller.dto.AbsenceDemandeDto;
import dev.controller.dto.AbsenceVisualisationDto;
import dev.entites.Absence;
import dev.entites.Collegue;
import dev.entites.Solde;
import dev.entites.Statut;
import dev.entites.TypeAbsence;
import dev.entites.TypeSolde;
import dev.exceptions.AbsenceChevauchementException;
import dev.exceptions.AbsenceDateException;
import dev.exceptions.AbsenceDateFinException;
import dev.exceptions.AbsenceMotifManquantException;
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
	 * @return la liste des absences du collègue dont l'email est passé en paramètres
	 */
	public List<AbsenceVisualisationDto> listerAbsencesCollegue() {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); 
		
		List<AbsenceVisualisationDto> listeAbsences = new ArrayList<>();
		
		for (Absence absence : absenceRepository.findAll()) {
			if (absence.getCollegue().getEmail().equals(email)) {
				AbsenceVisualisationDto absenceDto = new AbsenceVisualisationDto(absence.getDateDebut(), absence.getDateFin(), absence.getType(), absence.getStatut());
				listeAbsences.add(absenceDto);
			}
		} 
		return listeAbsences;
	}
	
	
	
	/**
	 * @param absenceDto
	 * @return une AbsenceVisualisationDto
	 */
	@Transactional
	public AbsenceDemandeDto demandeAbsence(AbsenceDemandeDto absenceDemandeDto) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); 
		
		Collegue collegue = collegueRepository.findByEmail(email)
				.orElseThrow(() -> new CollegueByEmailNotExistException("L'email selectionne ne correspond a aucun collegue"));
		
		Absence absence = new Absence (absenceDemandeDto.getDateDebut(),absenceDemandeDto.getDateFin(), absenceDemandeDto.getType(), absenceDemandeDto.getMotif(), absenceDemandeDto.getStatut(), collegue);
		
		if (absence.getDateDebut().isBefore(LocalDate.now()) || (absence.getDateDebut().isEqual(LocalDate.now()))) // Cas jour saisi dans le passé ou aujourd'hui, erreur
		{
			throw new AbsenceDateException("Une demande d'absence ne peut être saisie sur une date ultérieur ou le jour présent.");
		} else if (absence.getDateFin().isBefore(absence.getDateDebut())) // Cas DateFin < DateDebut
		{
			throw new AbsenceDateFinException("La date de fin ne peut-être inférieure à la date du début de votre absence.");
		} 
		else if (absence.getType().toString().equals("CONGES_SANS_SOLDE") && absence.getMotif().isEmpty()) // Cas congès sans solde, et motif manquant
		{
			throw new AbsenceMotifManquantException("Un motif est obligatoire dans le cas où vous souhaitez demander un congés sans solde.");
		} else if (!absence.getStatut().toString().equals("REJETEE")) // Impossible de saisir une demande qui chevauche une autre sauf si celle-ci est en statut REJETEE
		{
			
			List<Absence> listAbsences = new ArrayList<>();
			listAbsences = this.absenceRepository.findAll();
 
			System.out.println(listAbsences);

			for (Absence abs : listAbsences) {

				if ((abs.getDateDebut().toString().equals(absence.getDateDebut().toString()))) {
					throw new AbsenceChevauchementException("Une demande est déjà en cours à cette date");
				}
			}

		}
		
		this.absenceRepository.save(absence);
		return new AbsenceDemandeDto(absence.getDateDebut(), absence.getDateFin(), absence.getType(), absence.getMotif(), absence.getStatut());

	}

	/**
	 * traitement de nuit des demandes d'absences
	 */
	public void traitementDeNuit() {
		
		List<Solde> soldes = new ArrayList<>();
		
		for(Absence absence : absenceRepository.findAll()) {
			/* si RTT employeur, changer la demande en validée et baisser le compteur de RTT de tous les collegues */
			if (absence.getType().equals(TypeAbsence.RTT_EMPLOYEUR)) {
				absence.setStatut(Statut.VALIDEE);
				for (Collegue collegue : collegueRepository.findAll()) {
					for (Solde solde : collegue.getSoldes()) {
						if (solde.getType() == TypeSolde.RTT_EMPLOYE) {
							solde.setNombreDeJours(solde.getNombreDeJours()-1);
						}
					}
				}
			} else {
				soldes = absence.getCollegue().getSoldes();
				for (Solde solde : soldes) {
					//if (solde.getNombreDeJours() )
				}
			}
		}
	}
}
