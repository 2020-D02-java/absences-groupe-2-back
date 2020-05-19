/**
 * 
 */
package dev.controller.dto;

import dev.entites.TypeSolde;

/**
 * Structure modélisant un solde servant à communiquer avec 
 * l'extérieur (WEB API).
 *
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class SoldeDto {

	// Déclarations
	private TypeSolde type;
	private Integer nombreDeJours;

	/**
	 * Constructeur
	 *
	 * @param type
	 * @param nombreDeJours
	 */
	public SoldeDto(TypeSolde type, Integer nombreDeJours) {
		super();
		this.type = type;
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
	public void setType(TypeSolde type) {
		this.type = type;
	}

	/**
	 * Getter
	 *
	 * @return nombredeJours
	 */
	public Integer getNombreDeJours() {
		return nombreDeJours;
	}

	/**
	 * Setter
	 *
	 * @param nombredeJours to set
	 */
	public void setNombreDeJours(Integer nombreDeJours) {
		this.nombreDeJours = nombreDeJours;
	}
}
