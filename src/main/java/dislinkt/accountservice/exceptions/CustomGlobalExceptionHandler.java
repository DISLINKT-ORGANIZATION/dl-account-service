package dislinkt.accountservice.exceptions;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidToken.class)
	public ResponseEntity<Object> handleInvalidToken(HttpServletResponse response) {
		return new ResponseEntity<>("Invalid token.", HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AccountAlreadyExists.class)
	public ResponseEntity<Object> handleResumeAlreadyExists(HttpServletResponse response) {
		return new ResponseEntity<>("Resume already exists.", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(EntityNotFound.class)
	public ResponseEntity<Object> handleNotFound(HttpServletResponse response) {
		return new ResponseEntity<>("Entity not found.", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InconsistentData.class)
	public ResponseEntity<Object> handleInconsistentData(HttpServletResponse response) {
		return new ResponseEntity<>("Data is not consistent.", HttpStatus.CONFLICT);
	}

	@ExceptionHandler(DateException.class)
	public ResponseEntity<Object> handleInvalidDateRange(HttpServletResponse response) {
		return new ResponseEntity<>("Invalid date range.", HttpStatus.BAD_REQUEST);
	}


}
