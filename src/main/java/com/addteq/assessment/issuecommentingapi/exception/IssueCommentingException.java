package com.addteq.assessment.issuecommentingapi.exception;

import ch.qos.logback.core.util.StringUtil;
import com.addteq.assessment.issuecommentingapi.model.IssueCommentError;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.addteq.assessment.issuecommentingapi.utils.Contants.Error.UNKNOWN_ERROR;

@Data
@EqualsAndHashCode(callSuper = false)
@ControllerAdvice
@Slf4j
public class IssueCommentingException extends RuntimeException implements Serializable {

    private IssueCommentError issueCommentError;

    public IssueCommentingException() {
        this.issueCommentError = IssueCommentError.builder().errorCode("IC-502").httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).message(UNKNOWN_ERROR).traceID(UUID.randomUUID().toString()).build();
    }
    public IssueCommentingException(String message) {
        this.issueCommentError = IssueCommentError.builder().errorCode("IC-501").httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).message(StringUtil.isNullOrEmpty(message) ? UNKNOWN_ERROR : message).traceID(UUID.randomUUID().toString()).build();
    }

    @ExceptionHandler(value = IssueCommentingException.class)
    public ResponseEntity<IssueCommentError> handleIssueCommentingException(IssueCommentingException exception) {

        log.error("Exception while adding comment : {}", exception.issueCommentError.getMessage());
        return new ResponseEntity<>(exception.getIssueCommentError(),exception.getIssueCommentError().getHttpStatus());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<IssueCommentError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<String> errorMessages = exception.getBindingResult().getAllErrors().stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
        String message = errorMessages.toString();
        this.issueCommentError = IssueCommentError.builder().errorCode("IC-401").httpStatus(HttpStatus.BAD_REQUEST).message(message).traceID(UUID.randomUUID().toString()).build();

        log.error("Exception while adding comment : {}", message);

        return new ResponseEntity<>(issueCommentError,issueCommentError.getHttpStatus());
    }

}
