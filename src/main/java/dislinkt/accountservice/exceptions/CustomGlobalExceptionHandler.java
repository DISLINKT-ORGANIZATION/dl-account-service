package dislinkt.accountservice.exceptions;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidToken.class)
	public void handleInvalidToken(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token.");
	}
	
	@ExceptionHandler(AccountAlreadyExists.class)
	public void handleResumeAlreadyExists(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), "Resume already exists.");
	}
	
	@ExceptionHandler(EntityNotFound.class)
	public void handleNotFound(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), "Entity not found.");
	}
	
	@ExceptionHandler(InconsistentData.class)
	public void handleInconsistentData(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), "Data is not consistent.");
	}

}
