/**
 * 
 */
package dev.controller.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import dev.entites.Statut;
import dev.entites.TypeAbsence;

/** Structure modélisant une absence servant à communiquer avec l'extérieur (WEB API).
 *
 * @author KOMINIARZ Anaïs
 *
 */
public class AbsenceDto {

	private LocalDate dateDebut;
	private LocalDate dateFin;
	@Enumerated(EnumType.STRING)
	private TypeAbsence type;
	private String motif;
	@Enumerated(EnumType.STRING)
	private Statut statut;

	

	public AbsenceDto() {
		
	}

	/** Constructeur
	 *
	 * @param dateDebut
	 * @param dateFin
	 * @param type
	 * @param motif
	 * @param statut
	 */
	public AbsenceDto(LocalDate dateDebut, LocalDate dateFin, TypeAbsence type, String motif, Statut statut) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.type = type;
		this.motif = motif;
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
	 * @return the motif
	 */
	public String getMotif() {
		return motif;
	}
	/** Setter
	 *
	 * @param motif the motif to set
	 */
	public void setMotif(String motif) {
		this.motif = motif;
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

	
	
}
