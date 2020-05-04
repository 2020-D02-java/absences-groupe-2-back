/**
 * 
 */
package dev.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/** Représentation 
 *
 * @author KOMINIARZ Anaïs
 *
 */
@Entity
public class Absence {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private LocalDate dateDebut;
	private LocalDate dateFin;
	
	@Enumerated(EnumType.STRING)
	private TypeAbsence typeAbsence;
	 
	private String motif;
	 
	@Enumerated(EnumType.STRING)
	private Statut statut;
	 
	 


}
