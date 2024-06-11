package com.keycloak.UserIdBasedAuthentication.customException;

import com.keycloak.UserIdBasedAuthentication.entity.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ResponseStatus
@ControllerAdvice
public class RestResponseEntityException {

    @ExceptionHandler(AdminTokenException.class)
    public ResponseEntity<ErrorMessage> adminTokenFailedException(AdminTokenException exception , WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED,exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(AdminTokenException exception , WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

}
