package GlobalexceptionHandler;

public class NoSuchElementException extends RuntimeException{
	 
	public NoSuchElementException(String message){
	        super(message);
	        System.out.println( "exception "+message);
	    }
}