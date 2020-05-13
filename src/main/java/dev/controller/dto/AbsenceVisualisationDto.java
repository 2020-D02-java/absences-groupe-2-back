/**
 * 
 */
package dev.controller.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import dev.entites.Statut;
import dev.entites.TypeAbsence;

/** Structure modélisant une visualisation d'absence servant à communiquer avec l'extérieur (WEB API).
 *
 * @author KOMINIARZ Anaïs
 *
 */
public class AbsenceVisualisationDto {

	private Long id;
	
	private LocalDate dateDebut;
	private LocalDate dateFin;
	@Enumerated(EnumType.STRING)
	private TypeAbsence type;
	@Enumerated(EnumType.STRING)
	private Statut statut;

	

	public AbsenceVisualisationDto() {
		
	}

	/** Constructeur
	 *
	 * @param dateDebut
	 * @param dateFin
	 * @param type
	 * @param statut
	 */
	public AbsenceVisualisationDto(LocalDate dateDebut, LocalDate dateFin, TypeAbsence type, Statut statut) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.type = type;
		this.statut = statut;
	}
	/** Getter
	 *
	 * @return the dateDebut
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}
	/** Setter
	 *
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}
	/** Getter
	 *
	 * @return the dateFin
	 */
	public LocalDate getDateFin() {
		return dateFin;
	}
	/** Setter
	 *
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}
	/** Getter
	 *
	 * @return the typeAbsence
	 */
	public TypeAbsence getType() {
		return type;
	}
	/** Setter
	 *
	 * @param typeAbsence the typeAbsence to set
	 */
	public void setType(TypeAbsence type) {
		this.type = type;
	}
	/** Getter
	 *
	 * @return the statutAbs
	 */
	public Statut getStatut() {
		return statut;
	}
	/** Setter
	 *
	 * @param statutAbs the statutAbs to set
	 */
	public void setStatut(Statut statut) {
		this.statut = statut;
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

	
	
}