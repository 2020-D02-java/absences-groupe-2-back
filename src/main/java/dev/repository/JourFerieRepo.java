package dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.entites.JourFerme;

/** Repository de l'absence
 *
 * @author KOMINIARZ Anaïs
 *
 */
@Repository
public interface JourFerieRepo extends JpaRepository<JourFerme, Long> {
	
	@Override
	default List<JourFerme> findAll() {
		return null;
	}
}