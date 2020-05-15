/**
 * 
 */
package dev.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.entites.Absence;
import dev.entites.Statut;

/** Repository de l'entité Absence
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public interface AbsenceRepo extends JpaRepository<Absence, Integer> {

	Optional<List<Absence>> findByCollegueEmail(String email);

}
