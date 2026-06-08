package community.api.handler;

import community.api.exception.BusinessException;
import community.api.exception.NotFoundException;
import community.api.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class) //404
    public ResponseEntity<ApiResponse<Void>> handleNotFound(
            NotFoundException exception
    ) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ApiResponse.of(exception.getCode(), null));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusiness(
            BusinessException exception
    ) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ApiResponse.of(exception.getCode(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //400
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.of("invalid_request", null));
    }

    @ExceptionHandler(Exception.class) //500
    public ResponseEntity<ApiResponse<Void>> handleException(
            Exception exception
    ) {
        return ResponseEntity
                .internalServerError()
                .body(ApiResponse.of("internal_server_error", null));
    }
}