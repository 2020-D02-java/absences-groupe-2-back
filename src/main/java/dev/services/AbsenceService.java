/**
 * 
 */
package dev.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.controller.dto.AbsenceDto;
import dev.entites.Absence;
import dev.entites.Collegue;
import dev.entites.Solde;
import dev.entites.Statut;
import dev.entites.TypeAbsence;
import dev.entites.TypeSolde;
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
	public List<AbsenceDto> listerAbsencesCollegue(String email) {
		
		//Vérification que l'email correspond bien à un collègue
		Optional<Collegue> optionnalCollegue = collegueRepository.findByEmail(email);
		if (!optionnalCollegue.isPresent()) {
			throw new CollegueByEmailNotExistException("L'email selectionne ne correspond a aucun collegue");
		}
		
		List<AbsenceDto> listeAbsences = new ArrayList<>();
		
		for (Absence absence : absenceRepository.findAll()) {
			if (absence.getCollegue().getEmail().equals(email)) {
				AbsenceDto absenceDto = new AbsenceDto();

				// ID Necessaire pour la modale suppression front
				absenceDto.setId(absence.getId());
				absenceDto.setDateDebut(absence.getDateDebut());
				absenceDto.setDateFin(absence.getDateFin());
				absenceDto.setType(absence.getType());
				absenceDto.setStatut(absence.getStatut());
				// Motif Necessaire pour la modale suppression front
				absenceDto.setMotif(absence.getMotif());
				listeAbsences.add(absenceDto);
			}
		}
		return listeAbsences;
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
	
	// Supprimer jour ferme
		/*
		 * Règles métier: 
		 * 
		 * supprimer une demande d'absence qui n'est pas de type mission
		 */
		@Transactional
		public String deleteAbsence(@Valid Long id) {
			Optional<Absence> absence = this.absenceRepository.findById(id);

			if (absence.isPresent()) {
				
				this.absenceRepository.delete(absence.get());
				return "\"absence supprimee\"";

			} else {
				return "\"erreur dans la suppression\"";
			}

		}
}
