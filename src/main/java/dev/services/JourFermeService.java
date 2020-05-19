/**
 * 
 */
package dev.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.controller.dto.JourFermeAjoutDto;
import dev.controller.dto.JourFermeVisualisationDto;
import dev.controller.dto.JourFermeVisualisationPlanningDto;
import dev.entites.JourFerme;
import dev.entites.Statut;
import dev.entites.TypeJourFerme;
import dev.exceptions.CommentaireManquantJourFerieException;
import dev.exceptions.DateDansLePasseException;
import dev.exceptions.DeleteRttEmployeurDejaValideException;
import dev.exceptions.RttEmployeurUnWeekEndException;
import dev.exceptions.SaisieJourFerieUnJourDejaFerieException;
import dev.repository.JourFermeRepo;

/**
 * Service de l'entité Jours Fermés
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@Service
public class JourFermeService {

	// Déclarations
	private JourFermeRepo jourFermeRepository;

	/**
	 * Constructeur
	 *
	 * @param jourFermeRepository
	 */
	public JourFermeService(JourFermeRepo jourFermeRepository) {
		this.jourFermeRepository = jourFermeRepository;
	}

	/**
	 * Recupérer tous les jours fermés
	 * 
	 * @return
	 */
	public List<JourFermeVisualisationPlanningDto> getAllJourFermes() {

		List<JourFermeVisualisationPlanningDto> listeJourFerme = new ArrayList<>();

		for (JourFerme jourFerme : jourFermeRepository.findAll()) {

			JourFermeVisualisationPlanningDto jourFermeDto = new JourFermeVisualisationPlanningDto(jourFerme.getDate(), jourFerme.getCommentaire());
			listeJourFerme.add(jourFermeDto);

		}
		return listeJourFerme;
	}

	/**
	 * Récupérer tous les jours fermés par date (année)
	 * 
	 * @param annee
	 * @return
	 */
	public List<JourFermeVisualisationDto> getJourFermesParDate(Integer annee) {

		List<JourFermeVisualisationDto> listeJourFerme = new ArrayList<>();

		for (JourFerme jourFerme : jourFermeRepository.findAll()) {
			if (jourFerme.getDate().getYear() == annee) {
				JourFermeVisualisationDto jourFermeDto = new JourFermeVisualisationDto(jourFerme.getId(), jourFerme.getDate(), jourFerme.getType(), jourFerme.getCommentaire());
				listeJourFerme.add(jourFermeDto);
			}

		}
		return listeJourFerme;

	}

	/**
	 * RECUPERER UN JOUR FERME VIA L'ID
	 * 
	 * @param id
	 * @return
	 */
	public JourFermeVisualisationDto getJourFermesParId(Integer id) {
		JourFermeVisualisationDto jour = null;

		for (JourFerme jourFerme : jourFermeRepository.findAll()) {
			if (jourFerme.getId() == id) {
				jour = new JourFermeVisualisationDto(id, jourFerme.getDate(), jourFerme.getType(), jourFerme.getCommentaire());
			}
		}

		return jour;
	}

	/**
	 * MODIFIER JOURS FERMES
	 * 
	 * @param jourFermeDto
	 * @param id
	 * @return
	 */
	public JourFermeVisualisationDto putJourFerme(@Valid JourFermeVisualisationDto jourFermeDto, Integer id) {
		JourFermeVisualisationDto jourFerme = this.getJourFermesParId(id);

		// Cas jour saisi dans le passé
		if (jourFermeDto.getDate().isBefore(LocalDate.now())) {
			throw new DateDansLePasseException("Il n'est pas possible de saisir une date dans le passé.");
		}
		// Cas jour ferié selectionné, et commentaire manquant
		else if (jourFermeDto.getType().equals(TypeJourFerme.JOURS_FERIES) && jourFermeDto.getCommentaire().isEmpty()) {
			throw new CommentaireManquantJourFerieException("Un commentaire est obligatoire dans le cas ou un jour férié est selectionné.");
		}
		// Interdire la saisie de RTT le samedi ou dimanche
		else if (jourFermeDto.getType().equals(TypeJourFerme.RTT_EMPLOYEUR)
				&& (jourFermeDto.getDate().getDayOfWeek().toString() == "SATURDAY" || jourFermeDto.getDate().getDayOfWeek().toString() == "SUNDAY")) {
			throw new RttEmployeurUnWeekEndException("Il n'est pas possible de saisir un RTT le week-end.");
		}
		// Vérifier si la date a été changée
		else if (!(jourFerme.getDate().toString().equals(jourFermeDto.getDate().toString()))) {
			// Si jour ferié, on vérifie qu'il n'existe pas déjà un jour ferié à cette date
			if (jourFermeDto.getType().equals(TypeJourFerme.JOURS_FERIES)) {
				// Je crée une liste de tous les jours fermés
				List<JourFerme> listJourFerme = new ArrayList<>();
				listJourFerme = this.jourFermeRepository.findAll();

				// Je boucle sur la liste de jours
				for (JourFerme jour : listJourFerme) {
					// Si je trouve deux jours feriés à la même date, je lève une exception
					if ((jour.getDate().toString().equals(jourFermeDto.getDate().toString()) && (jour.getType().equals(TypeJourFerme.JOURS_FERIES)))) {
						throw new SaisieJourFerieUnJourDejaFerieException("Il n'est pas possible de saisir un jour férié à la même date qu'un autre jour férié.");
					}
				}

			}
		}
		jourFerme.setDate(jourFermeDto.getDate());
		jourFerme.setType(jourFermeDto.getType());
		jourFerme.setCommentaire(jourFermeDto.getCommentaire());

		JourFerme jour = new JourFerme(jourFerme.getDate(), jourFerme.getType(), jourFerme.getCommentaire());
		jour.setId(jourFerme.getId());

		this.jourFermeRepository.save(jour);
		return jourFerme;
	}

	/**
	 * AJOUTER UN JOUR FERME
	 * 
	 * @param jourFermeDto
	 * @return
	 */
	@Transactional
	public JourFermeAjoutDto postJourFerme(@Valid JourFermeAjoutDto jourFermeDto) {
		JourFerme jourFerme = new JourFerme(jourFermeDto.getDate(), jourFermeDto.getType(), jourFermeDto.getCommentaire());

		// Cas jour saisi dans le passé
		if (jourFerme.getDate().isBefore(LocalDate.now())) {
			throw new DateDansLePasseException("Il n'est pas possible de saisir une date dans le passé.");
		}
		// Cas jour ferié selectionné, et commentaire manquant
		else if (jourFerme.getType().equals(TypeJourFerme.JOURS_FERIES) && jourFerme.getCommentaire().isEmpty()) {
			throw new CommentaireManquantJourFerieException("Un commentaire est obligatoire dans le cas où un jour férié est selectionné.");
		}
		// Interdire la saisie de RTT le samedi ou dimanche
		else if (jourFerme.getType().equals(TypeJourFerme.RTT_EMPLOYEUR)
				&& (jourFerme.getDate().getDayOfWeek().toString() == "SATURDAY" || jourFerme.getDate().getDayOfWeek().toString() == "SUNDAY")) {
			throw new RttEmployeurUnWeekEndException("Il n'est pas possible de saisir un RTT le week-end.");
		}
		// Si jour ferié, on vérifie qu'il n'existe pas déjà un jour ferié à cette date
		else if (jourFerme.getType().equals(TypeJourFerme.JOURS_FERIES)) {
			// Je crée une liste de tous les jours fermés
			List<JourFerme> listJourFerme = new ArrayList<>();
			listJourFerme = this.jourFermeRepository.findAll();

			// Je boucle sur la liste de jours
			for (JourFerme jour : listJourFerme) {
				// Si je trouve deux jours fériés à la même date, je leve une exception
				if ((jour.getDate().toString().equals(jourFerme.getDate().toString()) && (jour.getType().equals(TypeJourFerme.JOURS_FERIES)))) {
					throw new SaisieJourFerieUnJourDejaFerieException("Il n'est pas possible de saisir un jour férié à la même date qu'un autre jour férié.");
				}
			}

		}

		// Tous les cas sont passants, je sauvegarde le jour
		this.jourFermeRepository.save(jourFerme);
		
		return new JourFermeAjoutDto(jourFerme.getDate(), jourFerme.getType(), jourFerme.getCommentaire());

	}

	/**
	 * SUPPRIMER UN JOUR FERME VIA L'ID
	 * Règles métier:
	 * il n'est pas possible de supprimer un jour férié ou une RTT employeur dans le
	 * passé il n'est pas possible de supprimer une RTT employeur validée
	 * @param id
	 * @return
	 */
	@Transactional
	public String deleteJourFerme(@Valid Integer id) {
		Optional<JourFerme> jourFerme = this.jourFermeRepository.findById(id);

		if (jourFerme.isPresent()) {
			// Il n'est pas possible de supprimer un jour férié ou un RTT employeur dans le
			// passé
			if (jourFerme.get().getDate().isBefore(LocalDate.now())) {
				throw new DateDansLePasseException("Il n'est pas possible de faire la suppression d'un jour fermé dans le passé.");
			}
			// Il n'est pas possible de supprimer un RTT employeur validé
			else if (jourFerme.get().getStatut().equals(Statut.VALIDEE) && jourFerme.get().getType().equals(TypeJourFerme.RTT_EMPLOYEUR)) {
				throw new DeleteRttEmployeurDejaValideException("Il n'est pas possible de faire la suppression d'un RTT employeur déjà validé.");
			}
			this.jourFermeRepository.delete(jourFerme.get());
			return "\"mission supprimee\"";

		} else {
			return "\"erreur dans la suppression\"";
		}

	}
}
