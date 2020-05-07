package dev.exceptions;

public class CollegueByEmailNotExistException extends RuntimeException{

	public CollegueByEmailNotExistException(String message) {
		super(message);
	}
}
