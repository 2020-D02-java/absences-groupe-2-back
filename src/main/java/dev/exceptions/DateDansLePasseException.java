package dev.exceptions;

/**
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class DateDansLePasseException extends RuntimeException{

	/** Constructeur
	 * @param message
	 */
	public DateDansLePasseException(String message) {
		super(message);
	}
}
