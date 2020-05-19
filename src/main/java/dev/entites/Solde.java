package dev.entites;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Représentation des différents compteurs
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@Entity
public class Solde {

	// Déclarations
	/** id du solde **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/** nombre de jours pour le solde **/
	private Integer nombreDeJours;

	/** type de l'absence **/
	@Enumerated(EnumType.STRING)
	private TypeSolde type;

	/** collegue auquel le solde est associé **/
	@ManyToOne
	@JoinColumn(name = "collegue_id")
	private Collegue collegue;

	/**
	 * Constructeur
	 *
	 * @param nombreDeJours
	 * @param typeSolde
	 * @param collegue
	 */
	public Solde(Integer nombreDeJours, TypeSolde type, Collegue collegue) {
		super();
		this.nombreDeJours = nombreDeJours;
		this.type = type;
		this.collegue = collegue;
	}

	/**
	 * Constructeur vide
	 */
	public Solde() {

	}

	/**
	 * Getter
	 *
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Setter
	 *
	 * @param id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Getter
	 *
	 * @return nombreDeJours
	 */
	public Integer getNombreDeJours() {
		return nombreDeJours;
	}

	/**
	 * Setter
	 *
	 * @param nombreDeJours to set
	 */
	public void setNombreDeJours(Integer nombreDeJours) {
		this.nombreDeJours = nombreDeJours;
	}

	/**
	 * Getter
	 *
	 * @return typeSolde
	 */
	public TypeSolde getType() {
		return type;
	}

	/**
	 * Setter
	 *
	 * @param typeSolde to set
	 */
	public void setTypeSolde(TypeSolde type) {
		this.type = type;
	}

	/**
	 * Getter
	 *
	 * @return collegue
	 */
	public Collegue getCollegue() {
		return collegue;
	}

	/**
	 * Setter
	 *
	 * @param collegue to set
	 */
	public void setCollegue(Collegue collegue) {
		this.collegue = collegue;
	}
}
