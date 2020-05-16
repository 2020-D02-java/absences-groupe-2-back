package dev.exceptions;

/**
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class SaisieJourFerieUnJourDejaFerieException extends RuntimeException{

	/** Constructeur
	 * @param message
	 */
	public SaisieJourFerieUnJourDejaFerieException(String message) {
		super(message);
	}
}
