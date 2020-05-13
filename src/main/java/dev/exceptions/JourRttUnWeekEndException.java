package dev.exceptions;

/**
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
public class JourRttUnWeekEndException extends RuntimeException{

	/** Constructeur
	 * @param message
	 */
	public JourRttUnWeekEndException(String message) {
		super(message);
	}
}
