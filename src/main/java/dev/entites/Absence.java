/**
 * 
 */
package dev.entites;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/** Représentation 
 *
 * @author KOMINIARZ Anaïs
 *
 */
@Entity
public class Absence {
	
	/** id de l'absence**/
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	/** date de debut de l'absence **/
	private LocalDate dateDebut;
	/** date de fin de l'absence **/
	private LocalDate dateFin;
	
	/** type de l'absence **/
	@Enumerated(EnumType.STRING)
	private TypeAbsence type;
	 
	/** motif de l'absence **/
	private String motif;
	 
	/** statut de l'absence **/
	@Enumerated(EnumType.STRING)
	private Statut statut;
	
	/** collegue auquel l'absence est associée **/
	@ManyToOne
    @JoinColumn(name = "collegue_id")
    private Collegue collegue;

	
	/** Constructeur
	 *
	 */
	public Absence() {
		
	}
	
	/** Constructeur
	 *
	 * @param dateDebut
	 * @param dateFin
	 * @param typeAbsence
	 * @param motif
	 * @param statut
	 * @param collegue
	 */
	public Absence(LocalDate dateDebut, LocalDate dateFin, TypeAbsence type, String motif, Statut statut,
			Collegue collegue) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.type = type;
		this.motif = motif;
		this.statut = statut;
		this.collegue = collegue;
	}

	/** Getter
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/** Setter
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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

	/** Getter
	 *
	 * @return the collegue
	 */
	public Collegue getCollegue() {
		return collegue;
	}

	/** Setter
	 *
	 * @param collegue the collegue to set
	 */
	public void setCollegue(Collegue collegue) {
		this.collegue = collegue;
	}
	 
	 


}
