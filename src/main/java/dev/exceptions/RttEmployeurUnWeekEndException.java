package dev.exceptions;

/**
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class RttEmployeurUnWeekEndException extends RuntimeException{

	/** Constructeur
	 * @param message
	 */
	public RttEmployeurUnWeekEndException(String message) {
		super(message);
	}
}
