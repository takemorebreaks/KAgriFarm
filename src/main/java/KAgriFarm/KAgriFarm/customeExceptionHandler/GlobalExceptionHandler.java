package KAgriFarm.KAgriFarm.customeExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler({CredantialError.class})
	    public ResponseEntity<Object> HandleBadCredantialException(CredantialError exception) {
	        return ResponseEntity
	        		 .status(HttpStatus.BAD_REQUEST)
	                .body(exception.getMessage());
	    }
	 @ExceptionHandler({UserAlreadyExist.class})
	 public ResponseEntity<Object> HandleUserAlreadyExistException(UserAlreadyExist exception){
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
				.body(exception.getMessage());
		 
	 }
	 @ExceptionHandler({TokenValidationException.class})
	 public ResponseEntity<Object>HandleTokenValidationException(TokenValidationException exception){
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				 .body(exception.getMessage());
	 }
	 @ExceptionHandler({UserNotFoundException.class})
	 public ResponseEntity<Object>HandleUserNotFoundExceptionException(UserNotFoundException exception){
		 return ResponseEntity.status(HttpStatus.NOT_FOUND)
				 .body(exception.getMessage());
	 }
	 @ExceptionHandler({RegistrationDataValidationException.class})
	 public ResponseEntity<Object>RegistrationDataValidationException(RegistrationDataValidationException exception){
		 return ResponseEntity.status(HttpStatus.NOT_FOUND)
				 .body(exception.getMessage());
	 }
}
