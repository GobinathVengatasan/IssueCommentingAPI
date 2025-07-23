package com.addteq.assessment.issuecommentingapi.service.impl;

import com.addteq.assessment.issuecommentingapi.exception.IssueCommentingException;
import com.addteq.assessment.issuecommentingapi.model.Comment;
import com.addteq.assessment.issuecommentingapi.repository.IssueCommentsRepository;
import com.addteq.assessment.issuecommentingapi.service.IssueCommentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IssueCommentsServiceImpl implements IssueCommentsService {

    @Autowired
    IssueCommentsRepository issueCommentsRepository;

    @Override
    public void addComment(Comment comment) {
        try {
            if(comment!=null){
                issueCommentsRepository.addComment(comment);
            }
            else throw new IssueCommentingException("Comment object is null");
        }catch (Exception e){
            String issueID = comment == null ? null : comment.getIssueId();
            log.info("Exception while adding comment for issueId {}, Exception is : {}", issueID, e.getMessage());
            throw new IssueCommentingException(e.getMessage());
        }
    }

    @Override
    public List<Comment> getCommentsByIssueID(String issueId) {
        try{
            return issueCommentsRepository.getCommentsByIssueID(issueId);
        }catch (Exception e){
            log.info("Exception while retrieving all comments Exception is : {}", e.getMessage());
            throw new IssueCommentingException(e.getMessage());
        }
    }

    @Override
    public List<Comment> getCommentsByAuthor(String author) {
        try{
            return issueCommentsRepository.getCommentsByAuthor(author);
        }catch (Exception e){
            log.info("Exception while retrieving all comments Exception is : {}", e.getMessage());
            throw new IssueCommentingException(e.getMessage());
        }
    }
}
