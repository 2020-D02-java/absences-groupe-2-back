package dev.exceptions;

/**
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class AbsenceDateFinAvantDateDebutException extends RuntimeException {

	/** Constructeur
	 * @param message
	 */
	public AbsenceDateFinAvantDateDebutException(String message) {
		super(message);
	}
}
