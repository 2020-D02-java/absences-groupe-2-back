/**
 * 
 */
package dev.controller.dto;

/**
 * Structure modélisant une erreur servant à communiquer avec 
 * l'extérieur (WEB API)
 *
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class ErreurDto {

	// Déclarations
	private String message;

	/**
	 * Constructeur vide
	 */
	public ErreurDto() {
	}

	/**
	 * Getter
	 *
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter
	 *
	 * @param message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
