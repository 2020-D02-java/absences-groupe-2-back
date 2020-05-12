/**
 * 
 */
package dev.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.controller.dto.AbsenceDto;
import dev.controller.dto.CollegueDto;
import dev.entites.Absence;
import dev.entites.AbsenceCollegue;
import dev.entites.Collegue;
import dev.entites.JourFerme;
import dev.exceptions.AbsenceChevauchementException;
import dev.exceptions.AbsenceDateException;
import dev.exceptions.AbsenceDateFinException;
import dev.exceptions.AbsenceMotifManquantException;
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
/*	public List<AbsenceDto> listerAbsencesCollegue(String email) {

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

	}*/

	/**
	 * @param dateDebut 1ere date
	 * @param dateFin 	2eme date
	 * @return le nombre de jours ouvrés entre deux dates
	 */
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
	
	
	// Création d'une demande d'absence 
		@Transactional
		public AbsenceCollegue demandeAbsence (AbsenceDto absenceDto, CollegueDto collegueDto) {
			
			Collegue colAbs = new Collegue(collegueDto.getNom(),collegueDto.getPrenom(), collegueDto.getEmail(), collegueDto.getSoldes(), collegueDto.getRoles());
			AbsenceCollegue absence = new AbsenceCollegue (colAbs,new Absence(absenceDto.getDateDebut(),absenceDto.getDateFin(), absenceDto.getType(), absenceDto.getMotif(), absenceDto.getStatut()));
			
			
			if (absence.getAbsence().getDateDebut().isBefore(LocalDate.now()) || (absence.getAbsence().getDateDebut().isEqual(LocalDate.now()))) // Cas jour saisi dans le passé ou aujourd'hui, erreur
			{
				throw new AbsenceDateException("Une demande d'absence ne peut être saisie sur une date ultérieur ou le jour présent.");
			} else if (absence.getAbsence().getDateFin().isBefore(absence.getAbsence().getDateDebut())) // Cas DateFin < DateDebut
			{
				throw new AbsenceDateFinException("La date de fin ne peut-être inférieure à la date du début de votre absence.");
			} 
			else if (absence.getAbsence().getType().toString().equals("CONGES_SANS_SOLDE") && absence.getAbsence().getMotif().isEmpty()) // Cas congès sans solde, et motif manquant
			{
				throw new AbsenceMotifManquantException("Un motif est obligatoire dans le cas où vous souhaitez demander un congés sans solde.");
			} else if (!absence.getAbsence().getStatut().toString().equals("REJETEE")) // Impossible de saisir une demande qui chevauche une autre sauf si celle-ci est en statut REJETEE
			{
				
				List<AbsenceCollegue> listAbsences = new ArrayList<>();
				listAbsences = this.absenceRepository.findAll();
	 
				System.out.println(listAbsences);

				for (AbsenceCollegue abs : listAbsences) {

					if ((abs.getAbsence().getDateDebut().toString().equals(absence.getAbsence().getDateDebut().toString()))) {
						throw new AbsenceChevauchementException("Une demande est déjà en cours à cette date");
					}
				}

			}
			List <AbsenceCollegue> absences = new ArrayList<>();
			if (!colAbs.getAbsences().isEmpty()) {
				for (AbsenceCollegue absI : colAbs.getAbsences())
					absences.add(absI);				
			}
			
			absences.add(absence);
			//this.collegueRepository.ajoutAbsence(colAbs.getAbsences(),absences);
			this.absenceRepository.save(absence);
			return absence;

		}

	/**
	 * traitement de nuit des demandes d'absences
	 */
	/*public void traitementDeNuit() {

		List<Solde> soldes = new ArrayList<>();

		
		  si RTT employeur, changer la demande en validée et baisser le compteur de RTT
		  de tous les collegues
		 
		for (JourFerme jourFerme : jourFermeRepository.findAll()) {
			if (jourFerme.getStatut().equals(Statut.INITIALE)) {
				jourFerme.setStatut(Statut.VALIDEE);
			}
			for (Collegue collegue : collegueRepository.findAll()) {
				for (Solde solde : collegue.getSoldes()) {
					if (solde.getType() == TypeSolde.RTT_EMPLOYE) {
						solde.setNombreDeJours(solde.getNombreDeJours() - 1);
					}
				}
			}
		}
		for (Absence absence : absenceRepository.findAll()) {
				int nombreDeJoursOuvresPendantAbsence = joursOuvresEntreDeuxDates(absence.getDateDebut(),absence.getDateFin());

				soldes = absence.getCollegue().getSoldes();
				for (Solde solde : soldes) {
					if (solde.getType().toString().equals(absence.getType().toString())) {
						if (solde.getNombreDeJours() - nombreDeJoursOuvresPendantAbsence < 0) {
							absence.setStatut(Statut.REJETEE);
						} else {
							absence.setStatut(Statut.EN_ATTENTE_VALIDATION);
							//envoyer un mail au manager
						}
					}
				}
			}
		}*/
}
