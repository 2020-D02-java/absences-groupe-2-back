package dev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Collegue;

/**
 * Repository de l'entité Collegue
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public interface CollegueRepo extends JpaRepository<Collegue, Integer> {

    Optional<Collegue> findByEmail(String email);
    
   /* @Modifying
    @Query("UPDATE Collegue c SET c.absences = :nouvellesAbsences WHERE c.absences = :anciennesAbsences")
    void ajoutAbsence(@Param("anciennesAbsences")List<Absence> anciennesAbsences, @Param("nouvellesAbsences")List<Absence> nouvellesAbsences);*/
}
