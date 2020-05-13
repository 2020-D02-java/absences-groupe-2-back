/**
 * 
 */
package dev.controller.dto;

/** Structure modélisant une erreur servant à communiquer avec l'extérieur (WEB API)
 *
 * @author KOMINIARZ Anaïs
 *
 */
public class ErreurDto {
	
	private String message;
	
	/** Getter
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/** Setter
	 *
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public ErreurDto() {
	}
	

}
