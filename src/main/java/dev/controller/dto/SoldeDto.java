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
	private TypeSolde typeSolde;
	
	private Integer nombredeJours;

	/** Getter
	 *
	 * @return the typeSolde
	 */
	public TypeSolde getTypeSolde() {
		return typeSolde;
	}

	/** Setter
	 *
	 * @param typeSolde the typeSolde to set
	 */
	public void setTypeSolde(TypeSolde typeSolde) {
		this.typeSolde = typeSolde;
	}

	/** Getter
	 *
	 * @return the nombredeJours
	 */
	public Integer getNombredeJours() {
		return nombredeJours;
	}

	/** Setter
	 *
	 * @param nombredeJours the nombredeJours to set
	 */
	public void setNombredeJours(Integer nombredeJours) {
		this.nombredeJours = nombredeJours;
	}
}
