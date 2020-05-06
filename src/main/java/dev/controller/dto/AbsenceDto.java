/**
 * 
 */
package dev.controller.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import dev.entites.Statut;
import dev.entites.TypeAbsence;

/** Représentation 
 *
 * @author KOMINIARZ Anaïs
 *
 */
public class AbsenceDto {

	private LocalDate dateDebut;
	private LocalDate dateFin;
	@Enumerated(EnumType.STRING)
	private TypeAbsence typeAbsence;
	@Enumerated(EnumType.STRING)
	private Statut statut;
	
	
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
	public TypeAbsence getTypeAbsence() {
		return typeAbsence;
	}
	/** Setter
	 *
	 * @param typeAbsence the typeAbsence to set
	 */
	public void setTypeAbsence(TypeAbsence typeAbsence) {
		this.typeAbsence = typeAbsence;
	}
	/** Getter
	 *
	 * @return the statut
	 */
	public Statut getStatut() {
		return statut;
	}
	/** Setter
	 *
	 * @param statut the statut to set
	 */
	public void setStatut(Statut statut) {
		this.statut = statut;
	}
	
	
}
