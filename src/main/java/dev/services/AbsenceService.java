/**
 * 
 */
package dev.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.controller.dto.AbsenceDto;
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
	public List<AbsenceDto> listerAbsencesCollegue() {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); 
		
		//Vérification que l'email correspond bien à un collègue
		/*Optional<Collegue> optionnalCollegue = collegueRepository.findByEmail(email);
		if (!optionnalCollegue.isPresent()) {
			throw new CollegueByEmailNotExistException("L'email selectionne ne correspond a aucun collegue");
		}*/
		
		List<AbsenceDto> listeAbsences = new ArrayList<>();
		
		for (Absence absence : absenceRepository.findAll()) {
			if (absence.getCollegue().getEmail().equals(email)) {
				AbsenceDto absenceDto = new AbsenceDto();
				absenceDto.setDateDebut(absence.getDateDebut());
				absenceDto.setDateFin(absence.getDateFin());
				absenceDto.setType(absence.getType());
				absenceDto.setStatut(absence.getStatut());
				listeAbsences.add(absenceDto);
			}
		} 
		return listeAbsences;
	}
	
	
	// Création d'une demande d'absence 
	@Transactional
	public AbsenceDto demandeAbsence(AbsenceDto absenceDto) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); 
		
		Collegue collegue = collegueRepository.findByEmail(email)
				.orElseThrow(() -> new CollegueByEmailNotExistException("L'email selectionne ne correspond a aucun collegue"));
		
		//Collegue colAbs = new Collegue(collegueDto.getNom(),collegueDto.getPrenom(), collegueDto.getEmail(), collegueDto.getSoldes(), collegueDto.getRoles());
		Absence absence = new Absence (absenceDto.getDateDebut(),absenceDto.getDateFin(), absenceDto.getType(), absenceDto.getMotif(), absenceDto.getStatut(), collegue);
		
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
		List <Absence> absences = new ArrayList<>();
		if (!collegue.getAbsences().isEmpty()) {
			for (Absence absI : collegue.getAbsences())
				absences.add(absI);				
		}
		
		absences.add(absence);
		//this.collegueRepository.ajoutAbsence(collegue.getAbsences(),absences);
		this.absenceRepository.save(absence);
	
		return new AbsenceDto(absence.getDateDebut(), absence.getDateFin(), absence.getType(), absence.getMotif(), absence.getStatut());

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
