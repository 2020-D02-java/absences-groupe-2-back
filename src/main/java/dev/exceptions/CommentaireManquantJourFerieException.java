package dev.exceptions;

/**
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class CommentaireManquantJourFerieException extends RuntimeException{

	/** Constructeur
	 * @param message
	 */
	public CommentaireManquantJourFerieException(String message) {
		super(message);
	}
}

