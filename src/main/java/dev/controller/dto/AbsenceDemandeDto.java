package dev.controller.dto;

import java.time.LocalDate;

import dev.entites.Statut;
import dev.entites.TypeAbsence;

/**
 * Structure modélisant une demande d'absence servant à communiquer avec 
 * l'extérieur (WEB API).
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class AbsenceDemandeDto {

	// Déclarations
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private TypeAbsence type;
	private String motif;
	private Statut statut;

	/**
	 * Constructeur
	 *
	 * @param dateDebut
	 * @param dateFin
	 * @param type
	 * @param motif
	 * @param statut
	 */
	public AbsenceDemandeDto(LocalDate dateDebut, LocalDate dateFin, TypeAbsence type, String motif, Statut statut) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.type = type;
		this.motif = motif;
		this.statut = statut;
	}

	/**
	 * Getter
	 *
	 * @return dateDebut
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}

	/**
	 * Setter
	 *
	 * @param dateDebut to set
	 */
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * Getter
	 *
	 * @return dateFin
	 */
	public LocalDate getDateFin() {
		return dateFin;
	}

	/**
	 * Setter
	 *
	 * @param dateFin to set
	 */
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * Getter
	 *
	 * @return type
	 */
	public TypeAbsence getType() {
		return type;
	}

	/**
	 * Setter
	 *
	 * @param type to set
	 */
	public void setType(TypeAbsence type) {
		this.type = type;
	}

	/**
	 * Getter
	 *
	 * @return motif
	 */
	public String getMotif() {
		return motif;
	}

	/**
	 * Setter
	 *
	 * @param motif to set
	 */
	public void setMotif(String motif) {
		this.motif = motif;
	}

	/**
	 * Getter
	 *
	 * @return statut
	 */
	public Statut getStatut() {
		return statut;
	}

	/**
	 * Setter
	 *
	 * @param statut to set
	 */
	public void setStatut(Statut statut) {
		this.statut = statut;
	}
}
