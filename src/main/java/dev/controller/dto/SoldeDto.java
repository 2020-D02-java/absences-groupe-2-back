/**
 * 
 */
package dev.controller.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import dev.entites.TypeSolde;

/** Structure modélisant un solde servant à communiquer avec l'extérieur (WEB API).
 *
 * @author KOMINIARZ Anaïs
 *
 */
public class SoldeDto {

	@Enumerated(EnumType.STRING)
	private TypeSolde type;
	
	private Integer nombreDeJours;

	/** Getter
	 *
	 * @return the typeSolde
	 */
	public TypeSolde getType() {
		return type;
	}

	/** Setter
	 *
	 * @param typeSolde the typeSolde to set
	 */
	public void setType(TypeSolde type) {
		this.type = type;
	}

	/** Getter
	 *
	 * @return the nombredeJours
	 */
	public Integer getNombreDeJours() {
		return nombreDeJours;
	}

	/** Setter
	 *
	 * @param nombredeJours the nombredeJours to set
	 */
	public void setNombreDeJours(Integer nombreDeJours) {
		this.nombreDeJours = nombreDeJours;
	}
}
