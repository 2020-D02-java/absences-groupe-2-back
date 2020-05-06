/**
 * 
 */
package dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Absence;

/** Repository de l'absence
 *
 * @author KOMINIARZ Anaïs
 *
 */
public interface AbsenceRepo extends JpaRepository<Absence, Long> {

}
