/**
 * 
 */
package dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.JourFerme;

/** Repository des jours ferme
 *
 * @author GIRARD Vincent
 *
 */
public interface JourFermeRepo extends JpaRepository<JourFerme, Long> {
	
}
