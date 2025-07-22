package com.addteq.assessment.issuecommentingapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class IssueCommentError {

    @JsonIgnore
    HttpStatus httpStatus;
    String traceID;
    String message;
    String errorCode;
}
