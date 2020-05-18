package dev.exceptions;

/**
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class AbsenceMotifManquantCongesSansSoldeException extends RuntimeException {

	/** Constructeur
	 * @param message
	 */
	public AbsenceMotifManquantCongesSansSoldeException(String message) {
		super(message);
	}
}
