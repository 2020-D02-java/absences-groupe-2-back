package dev.controller.dto;

import java.time.LocalDate;

/** Modélise une visualisation de JourFerme servant à communiquer avec 
 * l'extérieur (WEB API).
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */

public class JourFermeVisualisationPlanningDto {
	private LocalDate date;
	private String commentaire;
	
	
	/** Constructeur 
	 * 
	 * @param date
	 * @param commentaire
	 */
	public JourFermeVisualisationPlanningDto(LocalDate date, String commentaire) {
		super();
		this.date = date;
		this.commentaire = commentaire;
	}


	/** Getter
	 * @return
	 */
	public LocalDate getDate() {
		return date;
	}


	/** Setter
	 * @param date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}


	/** Getter
	 * @return
	 */
	public String getCommentaire() {
		return commentaire;
	}


	/** Setter
	 * @param commentaire
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
}
