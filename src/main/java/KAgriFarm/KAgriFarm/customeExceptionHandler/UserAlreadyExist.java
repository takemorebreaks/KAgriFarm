package KAgriFarm.KAgriFarm.customeExceptionHandler;

public class UserAlreadyExist extends RuntimeException {
	public UserAlreadyExist(String message){
        super(message);
	}

}
