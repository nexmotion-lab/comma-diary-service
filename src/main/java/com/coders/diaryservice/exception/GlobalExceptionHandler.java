package com.coders.diaryservice.exception;

import com.coders.diaryservice.exception.eventTag.CannotDeleteEventTagException;
import com.coders.diaryservice.exception.eventTag.DuplicateEventTagUpdateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.util.Arrays;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleAllExceptions(Exception ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(determineHttpStatus(ex), ex.getMessage());
        problemDetail.setInstance(URI.create(request.getDescription(false)));
        problemDetail.setProperty("errorCode", determineErrorCode(ex));

        return new ResponseEntity<>(problemDetail, determineHttpStatus(ex));
    }

    private HttpStatus determineHttpStatus(Exception ex) {
        if (ex instanceof DuplicateEventTagUpdateException) {
            return HttpStatus.CONFLICT;
        } else if (ex instanceof CannotDeleteEventTagException) {
            return HttpStatus.CONFLICT;
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String determineErrorCode(Exception ex) {
        if (ex instanceof DuplicateEventTagUpdateException) {
            return "101";
        } else if (ex instanceof CannotDeleteEventTagException) {
            return "102";
        }
        return "500";
    }


}
