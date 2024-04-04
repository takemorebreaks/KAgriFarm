package GlobalexceptionHandler;

public class ElementNotEnteract extends RuntimeException{
	 
	public ElementNotEnteract(String message){
	        super(message);
	        System.out.println( "exception "+message);
	    }
}