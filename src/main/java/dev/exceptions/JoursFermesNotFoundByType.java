package dev.exceptions;

/**
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class JoursFermesNotFoundByType extends RuntimeException{

	/** Constructeur
	 * @param message
	 */
	public JoursFermesNotFoundByType(String message) {
		super(message);
	}
}
