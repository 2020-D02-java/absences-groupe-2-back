/**
 * 
 */
package dev.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.controller.dto.AbsenceDto;
import dev.entites.Absence;
import dev.entites.Collegue;
import dev.entites.JourFerme;
import dev.entites.Solde;
import dev.entites.Statut;
import dev.entites.TypeAbsence;
import dev.entites.TypeSolde;
import dev.exceptions.CollegueByEmailNotExistException;
import dev.repository.AbsenceRepo;
import dev.repository.CollegueRepo;
import dev.repository.JourFermeRepo;

/**
 * Service de l'entité Absence
 *
 * @author KOMINIARZ Anaïs
 *
 */
@Service
public class AbsenceService {

	private AbsenceRepo absenceRepository;
	private CollegueRepo collegueRepository;
	private JourFermeRepo jourFermeRepository;

	/**
	 * Constructeur
	 *
	 * @param absenceRepository
	 */
	public AbsenceService(AbsenceRepo absenceRepository, CollegueRepo collegueRepository,
			JourFermeRepo jourFermeRepository) {
		this.absenceRepository = absenceRepository;
		this.collegueRepository = collegueRepository;
		this.jourFermeRepository = jourFermeRepository;
	}

	/**
	 * @param collegue : Collegue
	 * @return la liste des absences du collègue dont l'email est passé en
	 *         paramètres
	 */
	public List<AbsenceDto> listerAbsencesCollegue(String email) {

		// Vérification que l'email correspond bien à un collègue
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
				absenceDto.setType(absence.getType());
				absenceDto.setStatut(absence.getStatut());
				listeAbsences.add(absenceDto);
			}
		}

		return listeAbsences;

	}

	public int joursOuvresEntreDeuxDates(LocalDate dateDebut, LocalDate dateFin) {

		int numeroJour = dateDebut.getDayOfWeek().getValue();
		int nombreDeJours = dateFin.compareTo(dateDebut) + 1;
		int nombreDeJoursFermes = 0;

		int nombreDeSamediEtDimanche = nombreDeJours / (9 - numeroJour) * 2;

		for (JourFerme jourFerme : jourFermeRepository.findAll()) {
			if (!(jourFerme.getDate().isBefore(dateDebut)) && !(jourFerme.getDate().isAfter(dateFin))) {
				nombreDeJoursFermes += 1;
			}
		}

		return nombreDeJours - nombreDeSamediEtDimanche - nombreDeJoursFermes;
	}

	/**
	 * traitement de nuit des demandes d'absences
	 */
	public void traitementDeNuit() {

		List<Solde> soldes = new ArrayList<>();

		/*
		 * si RTT employeur, changer la demande en validée et baisser le compteur de RTT
		 * de tous les collegues
		 */
		for (JourFerme jourFermes : jourFermeRepository.findAll()) {
			//jourFermes.setStatut(Statut.VALIDEE);
			for (Collegue collegue : collegueRepository.findAll()) {
				for (Solde solde : collegue.getSoldes()) {
					if (solde.getType() == TypeSolde.RTT_EMPLOYE) {
						solde.setNombreDeJours(solde.getNombreDeJours() - 1);
					}
				}
			}
		}
		for (Absence absence : absenceRepository.findAll()) {
				int nombreDeJoursOuvresPendantAbsence = joursOuvresEntreDeuxDates(absence.getDateDebut(),
						absence.getDateFin());

				soldes = absence.getCollegue().getSoldes();
				for (Solde solde : soldes) {
					if (solde.getType().toString().equals(absence.getType().toString())) {

					}
					// if (solde.getNombreDeJours())
					// if date.d
				}
			}
		}
	}
}
