/**
 * 
 */
package dev.controller.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import dev.entites.Statut;
import dev.entites.TypeJourFerme;

public class JourFermeDto {

	private Long id;
	
	private LocalDate date;
	@Enumerated(EnumType.STRING)
	private TypeJourFerme typeJourFerme;
	
	@Enumerated(EnumType.STRING)
	private Statut statut;
	
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
	 * @return the typeJourFerme
	 */
	public TypeJourFerme getTypeJourFerme() {
		return typeJourFerme;
	}
	/** Setter
	 * @param typeJourFerme the typeJourFerme to set
	 */
	public void setTypeJourFerme(TypeJourFerme typeJourFerme) {
		this.typeJourFerme = typeJourFerme;
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
	 * @param typeJourFerme
	 * @param commentaire
	 */
	public JourFermeDto(LocalDate date, TypeJourFerme typeJourFerme, String commentaire) {
		super();
		this.date = date;
		this.typeJourFerme = typeJourFerme;
		this.commentaire = commentaire;
		this.statut = Statut.INITIALE;
	}
	/** Getter
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/** Setter
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/** Getter
	 * @return the statut
	 */
	public Statut getStatut() {
		return statut;
	}
	/** Setter
	 * @param statut the statut to set
	 */
	public void setStatut(Statut statut) {
		this.statut = statut;
	}
	
	
	
}
