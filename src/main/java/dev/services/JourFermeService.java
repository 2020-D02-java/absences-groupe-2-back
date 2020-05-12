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

import dev.controller.dto.JourFermeDto;
import dev.entites.JourFerme;
import dev.entites.Statut;
import dev.entites.TypeJourFerme;
import dev.exceptions.CommentaireManquantJourFerieException;
import dev.exceptions.DateDansLePasseException;
import dev.exceptions.JourRttUnWeekEndException;
import dev.exceptions.RttEmployeurDejaValideException;
import dev.exceptions.SaisieJourFeriesUnJourDejaFeriesException;
import dev.repository.JourFermeRepo;

/**
 * Service de l'entité Jour Ferme
 *
 * @author BATIGNES Pierre
 *
 */
@Service
public class JourFermeService {

	private JourFermeRepo jourFermeRepository;

	/**
	 * Constructeur
	 *
	 * @param absenceRepository
	 */
	public JourFermeService(JourFermeRepo jourFermeRepository) {
		this.jourFermeRepository = jourFermeRepository;
	}

	public List<JourFerme> getAllJourFermes() {

		return this.jourFermeRepository.findAll();
	}

	public List<JourFerme> getJourFermesParDate(Integer annee) {
		List<JourFerme> listJourFerme = this.jourFermeRepository.findAll();
		List<JourFerme> list = new ArrayList<>();

		for (JourFerme jour : listJourFerme) {
			if (jour.getDate().getYear() == annee) {
				list.add(jour);
			}
		}

		return list;
	}

	// Ajouter jour ferme + regles métier
	@Transactional
	public JourFerme postJourFerme(@Valid JourFermeDto jourFermeDto) {
		JourFerme jourFerme = new JourFerme(jourFermeDto.getDate(), jourFermeDto.getTypeJourFerme(), jourFermeDto.getCommentaire());

		// Cas jour saisi dans le passé, erreur
		if (jourFerme.getDate().isBefore(LocalDate.now())) 
		{
			throw new DateDansLePasseException("Il n'est pas possible de saisir une date dans le passé.");
		} 
		// Cas jour ferié selectionné, et commentaire manquant
		else if (jourFerme.getType().equals(TypeJourFerme.JOURS_FERIES) && jourFerme.getCommentaire().isEmpty()) 
		{
			throw new CommentaireManquantJourFerieException("Un commentaire est obligatoire dans le cas ou un jour férié est selectionné.");
		} 
		// interdire la saisie de RTT le samedi ou dimanche
		else if (jourFerme.getType().equals(TypeJourFerme.RTT_EMPLOYEUR) && (jourFerme.getDate().getDayOfWeek().toString() == "SATURDAY" || jourFerme.getDate().getDayOfWeek().toString() == "SUNDAY")) 
		{
			throw new JourRttUnWeekEndException("Il n'est pas possible de saisir un RTT le week-end.");
		} 
		// Si jour feriés, on vérifie qu'il n'existe pas déjà un jour ferié à cette date
		else if (jourFerme.getType().equals(TypeJourFerme.JOURS_FERIES)) 
		{
			// Je créé une liste de tous les jours fermés
			List<JourFerme> listJourFerme = new ArrayList<>();
			listJourFerme = this.jourFermeRepository.findAll();

			// Je boucle sur la liste de jours
			for (JourFerme jour : listJourFerme) {
				// Si je trouve deux jours feries à la même date, je leve une exception
				if ((jour.getDate().toString().equals(jourFerme.getDate().toString()) && (jour.getType().equals(TypeJourFerme.JOURS_FERIES)))) {
					throw new SaisieJourFeriesUnJourDejaFeriesException("Il n'est pas possible de saisir un jour férié à la même date qu'un autre jour férié.");
				}
			}

		}

		// Tous les cas sont passant, je sauvegarde le jour
		this.jourFermeRepository.save(jourFerme);
		return jourFerme;

	}

	// Supprimer jour ferme
	/*
	 * Règles métier:
	 * 
	 * il n'est pas possible de supprimer un jour férié ou une RTT employeur dans le
	 * passé il n'est pas possible de supprimer une RTT employeur validée
	 */
	@Transactional
	public String deleteJourFerme(@Valid Long id) {
		Optional<JourFerme> jourFerme = this.jourFermeRepository.findById(id);

		if (jourFerme.isPresent()) {
			System.out.println(jourFerme.get().getStatut());
			// il n'est pas possible de supprimer un jour férié ou une RTT employeur dans le passé
			if (jourFerme.get().getDate().isBefore(LocalDate.now())) 
			{
				throw new DateDansLePasseException("Il n'est pas possible de faire la suppression d'un jour fermé dans le passé.");
			}
			// il n'est pas possible de supprimer une RTT employeur validée
			else if (jourFerme.get().getStatut().equals(Statut.VALIDEE) && jourFerme.get().getType().equals(TypeJourFerme.RTT_EMPLOYEUR)) 
			{
				throw new RttEmployeurDejaValideException("Il n'est pas possible de faire la suppression d'un RTT employeur déjà validé.");
			}
			this.jourFermeRepository.delete(jourFerme.get());
			return "\"mission supprimee\"";

		} else {
			return "\"erreur dans la suppression\"";
		}

	}
}
