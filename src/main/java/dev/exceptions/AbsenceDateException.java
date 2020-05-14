package dev.exceptions;

/**
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class AbsenceDateException extends RuntimeException {

	/** Constructeur
	 * @param message
	 */
	public AbsenceDateException(String message) {
		super(message);
	}
}