package GlobalexceptionHandler;

public class TimeOutExceptionoccured extends RuntimeException{
	 
	public TimeOutExceptionoccured(String message){
	        super(message);
	        System.out.println( "exception "+message);
	    }
}