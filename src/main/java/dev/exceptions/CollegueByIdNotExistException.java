package dev.exceptions;

public class CollegueByIdNotExistException extends RuntimeException{

	public CollegueByIdNotExistException(String message) {
		super(message);
	}
}
