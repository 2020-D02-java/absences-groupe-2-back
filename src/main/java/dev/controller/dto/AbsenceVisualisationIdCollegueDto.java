/**
 * 
 */
package dev.controller.dto;

import java.time.LocalDate;

import dev.entites.Statut;
import dev.entites.TypeAbsence;

/**
 * Structure modélisant une visualisation d'absence servant à communiquer avec
 * l'extérieur (WEB API).
 *
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class AbsenceVisualisationIdCollegueDto {

	// Declarations
	private Integer id;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private TypeAbsence type;
	private String motif;
	private Statut statut;
	private String emailCollegue;

	/**
	 * Constructeur
	 *
	 * @param id
	 * @param dateDebut
	 * @param dateFin
	 * @param type
	 * @param motif
	 * @param statut
	 */
	public AbsenceVisualisationIdCollegueDto(Integer id, LocalDate dateDebut, LocalDate dateFin, TypeAbsence type, String motif, Statut statut, String emailCollegue) {
		super();
		this.id = id;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.type = type;
		this.motif = motif;
		this.statut = statut;
		this.emailCollegue = emailCollegue;
	}

	/**
	 * Constructeur vide
	 *
	 */
	public AbsenceVisualisationIdCollegueDto() {

	}

	/**
	 * Getter
	 *
	 * @return the dateDebut
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}

	/**
	 * Setter
	 *
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * Getter
	 *
	 * @return the dateFin
	 */
	public LocalDate getDateFin() {
		return dateFin;
	}

	/**
	 * Setter
	 *
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * Getter
	 *
	 * @return the typeAbsence
	 */
	public TypeAbsence getType() {
		return type;
	}

	/**
	 * Setter
	 *
	 * @param typeAbsence the typeAbsence to set
	 */
	public void setType(TypeAbsence type) {
		this.type = type;
	}

	/**
	 * Getter
	 *
	 * @return the motif
	 */
	public String getMotif() {
		return motif;
	}

	/**
	 * Setter
	 *
	 * @param motif the motif to set
	 */
	public void setMotif(String motif) {
		this.motif = motif;
	}

	/**
	 * Getter
	 *
	 * @return the statutAbs
	 */
	public Statut getStatut() {
		return statut;
	}

	/**
	 * Setter
	 *
	 * @param statutAbs the statutAbs to set
	 */
	public void setStatut(Statut statut) {
		this.statut = statut;
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

	/** Getter
	 * @return the emailCollegue
	 */
	public String getEmailCollegue() {
		return emailCollegue;
	}

	/** Setter
	 * @param emailCollegue the emailCollegue to set
	 */
	public void setEmailCollegue(String emailCollegue) {
		this.emailCollegue = emailCollegue;
	}


}
