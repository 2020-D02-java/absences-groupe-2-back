/**
 * 
 */
package dev.controller.dto;

import java.time.LocalDate;

import dev.entites.TypeJourFerme;

/**
 * @author 20-100
 *
 */
public class JourFermeAjoutDto {

	private LocalDate date;
	private TypeJourFerme type;
	private String commentaire;
	
	/** Getter
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}
	/** Setter
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	/** Getter
	 * @return the type
	 */
	public TypeJourFerme getType() {
		return type;
	}
	/** Setter
	 * @param type the type to set
	 */
	public void setType(TypeJourFerme type) {
		this.type = type;
	}
	/** Getter
	 * @return the commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}
	/** Setter
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	/** Constructeur
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
}
