/**
 * 
 */
package dev.controller.dto;

import java.time.LocalDate;

import dev.entites.Statut;
import dev.entites.TypeJourFerme;

/**
 * Structure modélisant une visualisation de JourFerme servant à communiquer avec 
 * l'extérieur (WEB API).
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class JourFermeVisualisationDto {

	// Déclarations
	private Integer id;
	private LocalDate date;
	private TypeJourFerme type;
	private Statut statut;
	private String commentaire;

	/**
	 * Constructeur
	 * 
	 * @param date
	 * @param typeJourFerme
	 * @param commentaire
	 */
	public JourFermeVisualisationDto(Integer id, LocalDate date, TypeJourFerme type, String commentaire) {
		super();
		this.id = id;
		this.date = date;
		this.type = type;
		this.commentaire = commentaire;
		this.statut = Statut.INITIALE;
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
