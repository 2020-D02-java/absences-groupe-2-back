package dev.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.JourFerme;
import dev.entites.TypeJourFerme;

/** Repository des jours fermés
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public interface JourFermeRepo extends JpaRepository<JourFerme, Integer> {

	public Optional<List<JourFerme>> findByType(TypeJourFerme type);
}
