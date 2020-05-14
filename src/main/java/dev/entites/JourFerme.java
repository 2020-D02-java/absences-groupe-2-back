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
 * Entité jour ferme
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@Entity
public class JourFerme {

	// Déclarations
	/** id d'un jour ferme **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/** date d'un jour ferme **/
	LocalDate date;

	/** type d'un jour ferme **/
	@Enumerated(EnumType.STRING)
	private TypeJourFerme type;

	/** commentaire du jour ferme **/
	private String commentaire;

	@Enumerated(EnumType.STRING)
	private Statut statut;

	/**
	 * Constructeur
	 * 
	 * @param id
	 * @param date
	 * @param type
	 * @param commentaire
	 */
	public JourFerme(LocalDate date, TypeJourFerme type, String commentaire) {
		super();
		this.date = date;
		this.type = type;
		this.commentaire = commentaire;
		this.statut = Statut.INITIALE;
	}

	/**
	 * Constructeur vide
	 */
	public JourFerme() {
	}

	/**
	 * Getter
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Setter
	 *
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Getter
	 *
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Setter
	 *
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Getter
	 *
	 * @return the typeJourFerme
	 */
	public TypeJourFerme getType() {
		return type;
	}

	/**
	 * Setter
	 *
	 * @param typeJourFerme the typeJourFerme to set
	 */
	public void setType(TypeJourFerme type) {
		this.type = type;
	}

	/**
	 * Getter
	 *
	 * @return the commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/**
	 * Setter
	 *
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	/**
	 * Getter
	 * 
	 * @return the statut
	 */
	public Statut getStatut() {
		return statut;
	}

	/**
	 * Setter
	 * 
	 * @param statut the statut to set
	 */
	public void setStatut(Statut statut) {
		this.statut = statut;
	}
}
