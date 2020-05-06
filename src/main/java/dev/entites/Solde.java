/**
 * 
 */
package dev.entites;

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
	
	/** id du solde **/
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	/** nombre de jours pour le solde **/
	private Integer nombreDeJours;
	
	/** type de l'absence **/
	@Enumerated(EnumType.STRING)
	private TypeAbsence typeAbsence;
	
	/** collegue auquel le solde est associé **/
	@ManyToOne
    @JoinColumn(name = "collegue_id")
    private Collegue collegue;

	/** Getter
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/** Setter
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/** Getter
	 *
	 * @return the nombreDeJours
	 */
	public Integer getNombreDeJours() {
		return nombreDeJours;
	}

	/** Setter
	 *
	 * @param nombreDeJours the nombreDeJours to set
	 */
	public void setNombreDeJours(Integer nombreDeJours) {
		this.nombreDeJours = nombreDeJours;
	}

	/** Getter
	 *
	 * @return the typeAbsence
	 */
	public TypeAbsence getTypeAbsence() {
		return typeAbsence;
	}

	/** Setter
	 *
	 * @param typeAbsence the typeAbsence to set
	 */
	public void setTypeAbsence(TypeAbsence typeAbsence) {
		this.typeAbsence = typeAbsence;
	}

	/** Getter
	 *
	 * @return the collegue
	 */
	public Collegue getCollegue() {
		return collegue;
	}

	/** Setter
	 *
	 * @param collegue the collegue to set
	 */
	public void setCollegue(Collegue collegue) {
		this.collegue = collegue;
	}
	


}
