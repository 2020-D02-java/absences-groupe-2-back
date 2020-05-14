/**
 * 
 */
package dev.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.controller.dto.AbsenceDemandeDto;
import dev.controller.dto.AbsenceVisualisationDto;
import dev.entites.Absence;
import dev.entites.Collegue;
import dev.entites.JourFerme;
import dev.entites.Solde;
import dev.entites.Statut;
import dev.entites.TypeSolde;
import dev.exceptions.AbsenceChevauchementException;
import dev.exceptions.AbsenceDateException;
import dev.exceptions.AbsenceDateFinException;
import dev.exceptions.AbsenceMotifManquantException;
import dev.exceptions.CollegueAuthentifieNonRecupereException;
import dev.repository.AbsenceRepo;
import dev.repository.CollegueRepo;
import dev.repository.JourFermeRepo;

/**
 * Service de l'entité Absence
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@Service
public class AbsenceService {

	// Déclarations
	private AbsenceRepo absenceRepository;
	private CollegueRepo collegueRepository;
	private JourFermeRepo jourFermeRepository;

	/**
	 * Constructeur
	 *
	 * @param absenceRepository
	 */
	public AbsenceService(AbsenceRepo absenceRepository, CollegueRepo collegueRepository, JourFermeRepo jourFermeRepository) {
		this.absenceRepository = absenceRepository;
		this.collegueRepository = collegueRepository;
		this.jourFermeRepository = jourFermeRepository;
	}

	/**
	 * @param collegue : Collegue
	 * @return la liste des absences du collègue dont l'email est passé en
	 *         paramètres
	 */
	public List<AbsenceVisualisationDto> listerAbsencesCollegue() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		List<AbsenceVisualisationDto> listeAbsences = new ArrayList<>();

		for (Absence absence : absenceRepository.findAll()) {
			if (absence.getCollegue().getEmail().equals(email)) {
				AbsenceVisualisationDto absenceDto = new AbsenceVisualisationDto(absence.getId(), absence.getDateDebut(), absence.getDateFin(), absence.getType(),
						absence.getMotif(), absence.getStatut());
				listeAbsences.add(absenceDto);
			}
		}
		return listeAbsences;

	}

	/**
	 * RECUPERER UNE ABSENCE VIA ID
	 * 
	 * @param id
	 * @return
	 */
	public AbsenceVisualisationDto getAbsenceParId(Integer id) {
		AbsenceVisualisationDto abs = null;

		for (Absence absence : absenceRepository.findAll()) {
			if (absence.getId() == id) {
				abs = new AbsenceVisualisationDto(id, absence.getDateDebut(), absence.getDateFin(), absence.getType(), absence.getMotif(), absence.getStatut());
			}
		}

		return abs;
	}

	/**
	 * MODIFICATION D'UNE ABSENCE
	 * 
	 * @param abenceDto
	 * @param id
	 * @return
	 */
	public AbsenceVisualisationDto putAbsence(@Valid AbsenceVisualisationDto abenceDto, Integer id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		Collegue collegue = collegueRepository.findByEmail(email).orElseThrow(() -> new CollegueAuthentifieNonRecupereException("Le collegue authentifie n a pas ete recupere"));

		AbsenceVisualisationDto absence = this.getAbsenceParId(id);

		if (abenceDto.getDateDebut().isBefore(LocalDate.now()) || (abenceDto.getDateDebut().isEqual(LocalDate.now()))) // Cas jour saisi dans le passé ou aujourd'hui, erreur
		{
			throw new AbsenceDateException("Une demande d'absence ne peut être saisie sur une date ultérieur ou le jour présent.");
		} else if (abenceDto.getDateFin().isBefore(abenceDto.getDateDebut())) // Cas DateFin < DateDebut
		{
			throw new AbsenceDateFinException("La date de fin ne peut-être inférieure à la date du début de votre absence.");
		} else if (abenceDto.getType().toString().equals("CONGES_SANS_SOLDE") && abenceDto.getMotif().isEmpty()) // Cas congès sans solde, et motif manquant
		{
			throw new AbsenceMotifManquantException("Un motif est obligatoire dans le cas où vous souhaitez demander un congés sans solde.");
		}
		else if (!abenceDto.getStatut().toString().equals("REJETEE")||!abenceDto.getStatut().toString().equals("INITIALE")) // Impossible de saisir une demande qui chevauche une autre sauf si celle-ci est en statut REJETEE
		{
			
			List<Absence> listAbsences = new ArrayList<>();
			listAbsences = this.absenceRepository.findAll();
 
			System.out.println(listAbsences);

			for (Absence abs : listAbsences) {

				if ((abs.getDateDebut().toString().equals(abenceDto.getDateDebut().toString()))) {
					throw new AbsenceChevauchementException("Une demande est déjà en cours à cette date");
				}
			}

		}

		absence.setDateDebut(abenceDto.getDateDebut());
		absence.setDateFin(abenceDto.getDateFin());
		absence.setType(abenceDto.getType());
		absence.setMotif(abenceDto.getMotif());
		absence.setStatut(abenceDto.getStatut());

		Absence abs = new Absence(absence.getDateDebut(), absence.getDateFin(), absence.getType(), absence.getMotif(), absence.getStatut(), collegue);
		abs.setId(absence.getId());

		this.absenceRepository.save(abs);
		return absence;
	}

	/**
	 * AJOUT D'UNE ABSENCE
	 * 
	 * @param absenceDto
	 * @return une AbsenceVisualisationDto
	 */
	@Transactional
	public AbsenceDemandeDto demandeAbsence(AbsenceDemandeDto absenceDemandeDto) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		Collegue collegue = collegueRepository.findByEmail(email).orElseThrow(() -> new CollegueAuthentifieNonRecupereException("Le collegue authentifie n a pas ete recupere"));

		Absence absence = new Absence(absenceDemandeDto.getDateDebut(), absenceDemandeDto.getDateFin(), absenceDemandeDto.getType(), absenceDemandeDto.getMotif(),
				absenceDemandeDto.getStatut(), collegue);

		if (absence.getDateDebut().isBefore(LocalDate.now()) || (absence.getDateDebut().isEqual(LocalDate.now()))) // Cas jour saisi dans le passé ou aujourd'hui, erreur
		{
			throw new AbsenceDateException("Une demande d'absence ne peut être saisie sur une date ultérieur ou le jour présent.");
		} else if (absence.getDateFin().isBefore(absence.getDateDebut())) // Cas DateFin < DateDebut
		{
			throw new AbsenceDateFinException("La date de fin ne peut-être inférieure à la date du début de votre absence.");
		} else if (absence.getType().toString().equals("CONGES_SANS_SOLDE") && absence.getMotif().isEmpty()) // Cas congès sans solde, et motif manquant
		{
			throw new AbsenceMotifManquantException("Un motif est obligatoire dans le cas où vous souhaitez demander un congés sans solde.");
		} else if((!absence.getStatut().toString().equals("REJETEE"))||(!absence.getStatut().toString().equals("INITIALE"))) // Impossible de saisir une demande qui chevauche une autre sauf si celle-ci est
																															// en statut REJETEE
		{
			List<Absence> listAbsences = new ArrayList<>();
			listAbsences = this.absenceRepository.findAll();
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
	 * jours Ouvres Entre DeuxDates
	 * 
	 * @param dateDebut 1ere date
	 * @param dateFin   2eme date
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

	/**
	 * traitement de nuit des demandes d'absences
	 */
	public void traitementDeNuit() {

		List<Solde> soldes = new ArrayList<>();

		/*
		 * si RTT employeur, changer la demande en validée et baisser le compteur de RTT
		 * de tous les collegues
		 */
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
			int nombreDeJoursOuvresPendantAbsence = joursOuvresEntreDeuxDates(absence.getDateDebut(), absence.getDateFin());

			soldes = absence.getCollegue().getSoldes();
			for (Solde solde : soldes) {
				if (solde.getType().toString().equals(absence.getType().toString())) {
					if (solde.getNombreDeJours() - nombreDeJoursOuvresPendantAbsence < 0) {
						absence.setStatut(Statut.REJETEE);
					} else {
						absence.setStatut(Statut.EN_ATTENTE_VALIDATION);
						// envoyer un mail au manager
					}
				}
			}
		}
	}

	/**
	 * Supprimer une absence Règles métier: supprimer une demande d'absence qui
	 * n'est pas de type mission
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public String deleteAbsence(@Valid Integer id) {
		Optional<Absence> absence = this.absenceRepository.findById(id);

		if (absence.isPresent()) {

			this.absenceRepository.delete(absence.get());
			return "\"absence supprimee\"";

		} else {
			return "\"erreur dans la suppression\"";
		}

	}
}
