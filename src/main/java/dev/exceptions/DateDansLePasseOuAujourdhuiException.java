package dev.exceptions;

/**
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class DateDansLePasseOuAujourdhuiException extends RuntimeException {

	/** Constructeur
	 * @param message
	 */
	public DateDansLePasseOuAujourdhuiException(String message) {
		super(message);
	}
}