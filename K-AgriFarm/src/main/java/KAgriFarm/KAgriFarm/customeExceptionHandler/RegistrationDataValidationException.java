package KAgriFarm.KAgriFarm.customeExceptionHandler;

public class RegistrationDataValidationException extends RuntimeException{
	 
	public RegistrationDataValidationException(String message){
	        super(message);
	    }
}