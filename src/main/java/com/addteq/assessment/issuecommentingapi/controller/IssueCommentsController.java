package com.addteq.assessment.issuecommentingapi.controller;

import ch.qos.logback.core.util.StringUtil;
import com.addteq.assessment.issuecommentingapi.exception.IssueCommentingException;
import com.addteq.assessment.issuecommentingapi.model.Comment;
import com.addteq.assessment.issuecommentingapi.service.IssueCommentsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.addteq.assessment.issuecommentingapi.utils.Contants.Endpoint.ADD_COMMENTS;
import static com.addteq.assessment.issuecommentingapi.utils.Contants.Endpoint.GET_COMMENTS_BY;

@RestController
@Slf4j
@RequestMapping("/api")
public class IssueCommentsController {

    @Autowired
    IssueCommentsService issueCommentsService;

    @PostMapping(value = ADD_COMMENTS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> addComments(@RequestBody @Valid Comment comment) {

        log.info("Request to add comment for issueId : {} is received.", comment.getIssueId());
        log.debug("Request to add comment for issueId : {}, Author : {}, Message : {}", comment.getIssueId(), comment.getAuthor(), comment.getMessage());

        try{
            issueCommentsService.addComment(comment);
        } catch (IssueCommentingException ex){
            log.info("IssueCommentingException while adding comment for issueId {}, Exception is : {}", comment.getIssueId(), ex.getMessage());
            throw new IssueCommentingException(ex.getIssueCommentError().getMessage());
        }catch (Exception ex){
            log.info("Exception while adding comment for issueId {}, Exception is : {}", comment.getIssueId(), ex.getMessage());
            throw new IssueCommentingException(ex.getMessage());
        }

        log.info("Request to add comment for issueId : {} is success.", comment.getIssueId());
        return ResponseEntity.ok(issueCommentsService.getCommentsByAuthor(comment.getAuthor()));
    }

    @GetMapping(value = GET_COMMENTS_BY, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> getCommentsBy(@RequestParam(name = "issueId", required = false) String issueId, @RequestParam(name = "author",required = false) String author) {

        log.info("Request to retrieve all comments for issueId {} and author {} is received.", issueId, author);

        List<Comment> response = null;
        try{
            if(StringUtil.notNullNorEmpty(issueId))
                response = issueCommentsService.getCommentsByIssueID(issueId);
            if(StringUtil.notNullNorEmpty(author))
                response = issueCommentsService.getCommentsByAuthor(author);
        } catch (IssueCommentingException ex){
            log.info("IssueCommentingException while retrieve all comments for issueId {} and author {}, Exception is : {}", issueId, author, ex.getMessage());
            throw new IssueCommentingException(ex.getIssueCommentError().getMessage());
        }catch (Exception ex){
            log.info("Exception while retrieve all comments for issueId {} and author {}, Exception is : {}", issueId, author, ex.getMessage());
            throw new IssueCommentingException(ex.getMessage());
        }

        log.info("Request to retrieve all comment for issueId {} and author {} is success.", issueId, author);
        return ResponseEntity.ok(response);
    }
}
