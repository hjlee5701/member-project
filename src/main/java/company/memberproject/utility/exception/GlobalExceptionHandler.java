package company.memberproject.utility.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<Object> handleMemberException(MemberException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("errorCode", ex.getHttpStatus());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }
}
