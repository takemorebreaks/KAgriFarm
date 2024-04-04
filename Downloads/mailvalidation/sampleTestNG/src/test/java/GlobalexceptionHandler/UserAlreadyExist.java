package GlobalexceptionHandler;

public class UserAlreadyExist extends RuntimeException{
	 
	public UserAlreadyExist(String message){
	        super(message);
	        System.out.println( "exception "+message);
	    }
}