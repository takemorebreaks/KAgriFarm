package KAgriFarm.KAgriFarm.customeExceptionHandler;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String message){
        super(message);
	}
}
