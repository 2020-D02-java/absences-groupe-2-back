/**
 * 
 */
package dev.controller.dto;

import java.time.LocalDate;

import dev.entites.Collegue;
import dev.entites.Statut;
import dev.entites.TypeAbsence;

/**
 * Structure modélisant une visualisation d'absence servant à communiquer avec
 * l'extérieur (WEB API).
 *
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class AbsenceManagerVisualisationDto {

	// Declarations
	private Integer id;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private TypeAbsence type;
	private String motif;
	private Statut statut;
	private CollegueAbsenceDto collegue;
	private CollegueAbsenceDto manager;

	/**
	 * Constructeur
	 *
	 * @param id
	 * @param dateDebut
	 * @param dateFin
	 * @param type
	 * @param motif
	 * @param statut
	 * @param collegue
	 */
	public AbsenceManagerVisualisationDto(Integer id, LocalDate dateDebut, LocalDate dateFin, TypeAbsence type, String motif, Statut statut, CollegueAbsenceDto collegue, CollegueAbsenceDto manager) {
		super();
		this.id = id;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.type = type;
		this.motif = motif;
		this.statut = statut;
		this.collegue = collegue;
		this.manager = manager;
	}

	/**
	 * Constructeur vide
	 *
	 */
	public AbsenceManagerVisualisationDto() {

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

	/**Getter
	 *
	 * @return the collegue
	 */
	public CollegueAbsenceDto getCollegue() {
		return collegue;
	}

	/**Setter
	 *
	 * @param collegue the collegue to set
	 */
	public void setCollegue(CollegueAbsenceDto collegue) {
		this.collegue = collegue;
	}

	/**Getter
	 *
	 * @return the manager
	 */
	public CollegueAbsenceDto getManager() {
		return manager;
	}

	/**Setter
	 *
	 * @param manager the manager to set
	 */
	public void setManager(CollegueAbsenceDto manager) {
		this.manager = manager;
	}
}
