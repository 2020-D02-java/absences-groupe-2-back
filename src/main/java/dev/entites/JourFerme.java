/**
 * 
 */
package dev.entites;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author KOMINIARZ Anaïs
 *
 */
@Entity
public class JourFerme {

	/** id d'un jour ferme **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** date d'un jour ferme **/
	LocalDate date;
	
	/** type d'un jour ferme **/
	@Enumerated(EnumType.STRING)
	private TypeJourFerme typeJourFerme;
	
	/** commentaire du jour ferme **/
	String commentaire;

	
	/** Constructeur
	 * @param id
	 * @param date
	 * @param typeJourFerme
	 * @param commentaire
	 */
	public JourFerme(LocalDate date, TypeJourFerme typeJourFerme, String commentaire) {
		super();
		this.date = date;
		this.typeJourFerme = typeJourFerme;
		this.commentaire = commentaire;
	}	
	public JourFerme() {
	}
	
	
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
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/** Setter
	 *
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/** Getter
	 *
	 * @return the typeJourFerme
	 */
	public TypeJourFerme getTypeJourFerme() {
		return typeJourFerme;
	}

	/** Setter
	 *
	 * @param typeJourFerme the typeJourFerme to set
	 */
	public void setTypeJourFerme(TypeJourFerme typeJourFerme) {
		this.typeJourFerme = typeJourFerme;
	}

	/** Getter
	 *
	 * @return the commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/** Setter
	 *
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}


}
