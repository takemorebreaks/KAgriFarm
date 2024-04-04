package GlobalexceptionHandler;

public class CredantialError extends RuntimeException{
	 
	public CredantialError(String message){
	        super(message);
	        System.out.println( "exception "+message);
	    }
}
