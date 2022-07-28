package net.reflix.movie.handler;

import net.reflix.movie.handler.dto.ApiError;
import net.reflix.movie.handler.dto.ValidationError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestHttpExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        var suberrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> ValidationError.of(
                        err.getField(),
                        err.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest()
                .headers(headers)
                .body(ApiError.of(
                        status,
                        "Data Not Valid",
                        suberrors));


    }


    // custom handler
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> handleNotSuchElement(
            Exception ex, WebRequest request)
    {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(ApiError.of(status, "Data Not Found", List.of()));
    }


}
