/**
 * 
 */
package dev.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.controller.dto.AbsenceDemandeDto;
import dev.controller.dto.AbsenceVisualisationDto;
import dev.controller.dto.AbsenceVisualisationEmailCollegueDto;
import dev.entites.Absence;
import dev.entites.Collegue;
import dev.entites.JourFerme;
import dev.entites.Solde;
import dev.entites.Statut;
import dev.entites.TypeAbsence;
import dev.entites.TypeJourFerme;
import dev.entites.TypeSolde;
import dev.exceptions.AbsenceChevauchementException;
import dev.exceptions.AbsenceDateFinAvantDateDebutException;
import dev.exceptions.AbsenceMotifManquantCongesSansSoldeException;
import dev.exceptions.CollegueAuthentifieNonRecupereException;
import dev.exceptions.CollegueAuthentifieNotAbsencesException;
import dev.exceptions.DateDansLePasseOuAujourdhuiException;
import dev.exceptions.JoursFermesNotFoundByType;
import dev.repository.AbsenceRepo;
import dev.repository.CollegueRepo;
import dev.repository.JourFermeRepo;
import dev.repository.SoldeRepo;

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
	private SoldeRepo soldeRepository;

	/**
	 * Constructeur
	 *
	 * @param absenceRepository
	 */
	public AbsenceService(AbsenceRepo absenceRepository, CollegueRepo collegueRepository, JourFermeRepo jourFermeRepository, SoldeRepo soldeRepository) {
		this.absenceRepository = absenceRepository;
		this.collegueRepository = collegueRepository;
		this.jourFermeRepository = jourFermeRepository;
		this.soldeRepository = soldeRepository;
	}

	/**
	 * LISTER ABSENCES DU COLLEGUE CONNECTE
	 * 
	 * @return la liste d'absence Dto du collegue connecté
	 */
	public List<AbsenceVisualisationDto> listerAbsencesCollegue() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		List<AbsenceVisualisationDto> listeAbsences = new ArrayList<>();

		List<Absence> liste = absenceRepository.findByCollegueEmail(email).orElseThrow(() -> new CollegueAuthentifieNotAbsencesException
				("Le collègue authentifié n'a pas encore d'absences")); 		
		for (Absence absence : liste) {
			AbsenceVisualisationDto absenceDto = new AbsenceVisualisationDto(absence.getId(), absence.getDateDebut(), absence.getDateFin(), absence.getType(),
					absence.getMotif(), absence.getStatut());
			listeAbsences.add(absenceDto);
		}
		return listeAbsences;

	}
	
	/**
	 * LISTER TOUTES LES ABSENCES DES COLLEGUES (front ==> vue-par-departement-par-jour-par-collaborateur)
	 */
	public List<AbsenceVisualisationEmailCollegueDto> listerToutesAbsencesCollegue() {

		List<AbsenceVisualisationEmailCollegueDto> listeAbsences = new ArrayList<>();
		List<Absence> liste = absenceRepository.findAll();
		for (Absence absence : liste) {
			AbsenceVisualisationEmailCollegueDto absenceDto = new AbsenceVisualisationEmailCollegueDto(absence.getDateDebut(), absence.getDateFin(), absence.getType(),
					absence.getMotif(), absence.getStatut(), absence.getCollegue().getEmail());
			listeAbsences.add(absenceDto);
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

		Collegue collegue = collegueRepository.findByEmail(email).orElseThrow(() -> new CollegueAuthentifieNonRecupereException("Le collègue authentifié n'a pas été récupéré"));

		AbsenceVisualisationDto absence = this.getAbsenceParId(id);
		
		if (abenceDto.getDateDebut().isBefore(LocalDate.now()) || (abenceDto.getDateDebut().isEqual(LocalDate.now()))) // Cas jour saisi dans le passé ou aujourd'hui, erreur
		{
			throw new DateDansLePasseOuAujourdhuiException("Une demande d'absence ne peut être saisie sur une date ultérieur ou le jour présent.");
		} else if (abenceDto.getDateFin().isBefore(abenceDto.getDateDebut())) // Cas DateFin < DateDebut
		{
			throw new AbsenceDateFinAvantDateDebutException("La date de fin ne peut-être inférieure à la date du début de votre absence.");
		} else if (abenceDto.getType().equals(TypeAbsence.CONGES_SANS_SOLDE) && abenceDto.getMotif().isEmpty()) // Cas congès sans solde, et motif manquant
		{
			throw new AbsenceMotifManquantCongesSansSoldeException("Un motif est obligatoire dans le cas où vous souhaitez demander un congés sans solde.");
		}
		else if ((abenceDto.getStatut().equals(Statut.EN_ATTENTE_VALIDATION))||(abenceDto.getStatut().equals(Statut.VALIDEE))) // Impossible de saisir une demande qui chevauche une autre sauf si celle-ci est en statut REJETEE
		{
			
			List<Absence> listAbsences = new ArrayList<>();
			listAbsences = this.absenceRepository.findAll();
 
			System.out.println(listAbsences);

			for (Absence abs : listAbsences) {

				if ((abs.getDateDebut().toString().equals(abenceDto.getDateDebut().toString()))) {
					throw new AbsenceChevauchementException("Une demande est déjà en cours à cette date test");
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

		Collegue collegue = collegueRepository.findByEmail(email).orElseThrow(() -> new CollegueAuthentifieNonRecupereException("Le collègue authentifié n'a pas été récupéré"));

		Absence absence = new Absence(absenceDemandeDto.getDateDebut(), absenceDemandeDto.getDateFin(), absenceDemandeDto.getType(), absenceDemandeDto.getMotif(),
				absenceDemandeDto.getStatut(), collegue);

		if (absence.getDateDebut().isBefore(LocalDate.now()) || (absence.getDateDebut().isEqual(LocalDate.now()))) // Cas jour saisi dans le passé ou aujourd'hui, erreur
		{
			throw new DateDansLePasseOuAujourdhuiException("Une demande d'absence ne peut être saisie sur une date ultérieur ou le jour présent.");
		} else if (absence.getDateFin().isBefore(absence.getDateDebut())) // Cas DateFin < DateDebut
		{
			throw new AbsenceDateFinAvantDateDebutException("La date de fin ne peut-être inférieure à la date du début de votre absence.");
		} else if (absence.getType().equals(TypeAbsence.CONGES_SANS_SOLDE) && absence.getMotif().isEmpty()) // Cas congès sans solde, et motif manquant
		{
			throw new AbsenceMotifManquantCongesSansSoldeException("Un motif est obligatoire dans le cas où vous souhaitez demander un congés sans solde.");
		} else if((absence.getStatut().equals(Statut.EN_ATTENTE_VALIDATION))||(absence.getStatut().equals(Statut.VALIDEE))) // Impossible de saisir une demande qui chevauche une autre sauf si celle-ci est
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
		
		int nombreDeSamediEtDimanche;
		int numeroJour = dateDebut.getDayOfWeek().getValue();
		int nombreDeJours = (int) ChronoUnit.DAYS.between(dateDebut, dateFin) + 1;
		
		if ((numeroJour - 1 + nombreDeJours) <= 5) {
			nombreDeSamediEtDimanche = 0;
		} else {
			nombreDeSamediEtDimanche = 2 + (((nombreDeJours - (9- numeroJour)) / 7) *2);
		}
		
		int nombreDeJoursFermes = 0;
 
		for (JourFerme jourFerme : jourFermeRepository.findAll()) {
			if (!(jourFerme.getDate().isBefore(dateDebut)) && !(jourFerme.getDate().isAfter(dateFin)) 
					&&!(jourFerme.getDate().getDayOfWeek().getValue() == 6) &&!(jourFerme.getDate().getDayOfWeek().getValue()==7)) {
				nombreDeJoursFermes += 1;
			}
		} 

		return nombreDeJours - nombreDeSamediEtDimanche - nombreDeJoursFermes;
	} 

	/**
	 * traitement de nuit des demandes d'absences
	 */
	@Transactional
	public void traitementDeNuit() {

		// traitement des RTT Employeur
		List<JourFerme> listeRttEmployeurs = jourFermeRepository.findByType(TypeJourFerme.RTT_EMPLOYEUR).orElseThrow
				(() -> new  JoursFermesNotFoundByType("Les jours fermés de type RTT employeur n'ont pas été trouvés.")); 
		
		for (JourFerme rtt_employeur : listeRttEmployeurs) {
			if (rtt_employeur.getStatut().equals(Statut.INITIALE)) {
				rtt_employeur.setStatut(Statut.VALIDEE);
				jourFermeRepository.save(rtt_employeur);
				if (rtt_employeur.getType().equals(TypeJourFerme.RTT_EMPLOYEUR)) {
					for (Collegue collegue : collegueRepository.findAll()) {
						for (Solde solde : collegue.getSoldes()) {
							if (solde.getType().equals(TypeSolde.RTT_EMPLOYE)) {
								solde.setNombreDeJours(solde.getNombreDeJours() - 1);
							}
						}
						collegueRepository.save(collegue);
					}
				}
			}
		}
		
		//Traitement des absences par collegue
		for (Collegue collegue : collegueRepository.findAll()) {
			
			List<Absence> listeAbsences = collegue.getAbsences();
			
			for (Absence absence : listeAbsences) {
				
				// on ne s'occupe que des absences au statut inital 
				if (absence.getStatut().equals(Statut.INITIALE)) {
					
					int nombreDeJoursOuvresPendantAbsence = joursOuvresEntreDeuxDates(absence.getDateDebut(), absence.getDateFin());
					
					if (absence.getType().equals(TypeAbsence.CONGES_SANS_SOLDE)) {
						absence.setStatut(Statut.EN_ATTENTE_VALIDATION);
						absenceRepository.save(absence);
					}
					
					List<Solde> soldes = collegue.getSoldes();
					
					for (Solde solde : soldes) {
						if (solde.getType().toString().equals(absence.getType().toString())){
							
							// si le collègue n'a pas assez de soldes (RTT_EMPLOYE ou CONGES_PAYES)
							if (solde.getNombreDeJours() - nombreDeJoursOuvresPendantAbsence < 0) {
								absence.setStatut(Statut.REJETEE);
								absenceRepository.save(absence);
							// sinon 	
							} else {
								absence.setStatut(Statut.EN_ATTENTE_VALIDATION);
								absenceRepository.save(absence);
								solde.setNombreDeJours(solde.getNombreDeJours() - nombreDeJoursOuvresPendantAbsence);
							}
						}
					}
				}
			}
			// sauvegarde le collegue pour avoir ses soldes mis à jour
			collegueRepository.save(collegue);
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
