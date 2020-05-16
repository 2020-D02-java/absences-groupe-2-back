package dev.exceptions;

/**
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class DeleteRttEmployeurDejaValideException extends RuntimeException {

	/** Constructeur
	 * @param message
	 */
	public DeleteRttEmployeurDejaValideException(String message) {
		super(message);
	}
}
