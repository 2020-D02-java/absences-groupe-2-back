package dev.exceptions;

/**
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class CollegueAuthentifieNonRecupereException extends RuntimeException{

	/** Constructeur
	 * @param message
	 */
	public CollegueAuthentifieNonRecupereException(String message) {
		super(message);
	}
}
