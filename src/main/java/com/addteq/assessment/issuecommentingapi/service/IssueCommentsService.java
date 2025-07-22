package com.addteq.assessment.issuecommentingapi.service;

import com.addteq.assessment.issuecommentingapi.model.Comment;

import java.util.List;

public interface IssueCommentsService {

    void addComment(Comment comment);
    List<Comment> getCommentsByIssueID(String issueId);
    List<Comment> getCommentsByAuthor(String author);
}
