/**
 * 
 */
package dev.controller.dto;

import java.time.LocalDate;

import dev.entites.TypeJourFerme;

/**
 * Structure modélisant un ajout de jour fermé servant à communiquer avec 
 * l'extérieur (WEB API).
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class JourFermeAjoutDto {

	// Déclarations
	private LocalDate date;
	private TypeJourFerme type;
	private String commentaire;

	/**
	 * Constructeur
	 * 
	 * @param date
	 * @param type
	 * @param commentaire
	 */
	public JourFermeAjoutDto(LocalDate date, TypeJourFerme type, String commentaire) {
		super();
		this.date = date;
		this.type = type;
		this.commentaire = commentaire;
	}

	/**
	 * Getter
	 * 
	 * @return date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Setter
	 * 
	 * @param date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Getter
	 * 
	 * @return type
	 */
	public TypeJourFerme getType() {
		return type;
	}

	/**
	 * Setter
	 * 
	 * @param type to set
	 */
	public void setType(TypeJourFerme type) {
		this.type = type;
	}

	/**
	 * Getter
	 * 
	 * @return commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/**
	 * Setter
	 * 
	 * @param commentaire to set
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

}
