package dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.entites.JourFerme;

/**
 * Repository de l'entité jour férié
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@Repository
public interface JourFerieRepo extends JpaRepository<JourFerme, Long> {
	
	@Override
	default List<JourFerme> findAll() {
		return null;
	}
}