/**
 * 
 */
package dev.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/** Représentation des différents compteurs
 *
 * @author KOMINIARZ Anaïs
 *
 */
@Entity
public class Solde {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private Integer nombreDeJours;
	
	@Enumerated(EnumType.STRING)
	private TypeAbsence typeAbsence;
	
	@ManyToOne
    @JoinColumn(name = "collegue_id")
    private Collegue collegue;
	
	@ManyToMany(mappedBy="soldes")
    private List<Collegue> collegues;


}
