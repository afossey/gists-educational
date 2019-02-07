package com.afossey.projects.educational.gists;

import java.util.List;
import org.everit.json.schema.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public List<String> validationException(ValidationException e) {
    return e.getAllMessages();
  }

}
