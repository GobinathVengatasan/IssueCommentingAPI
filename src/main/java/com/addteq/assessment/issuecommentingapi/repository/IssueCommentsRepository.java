package com.addteq.assessment.issuecommentingapi.repository;

import com.addteq.assessment.issuecommentingapi.model.Comment;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Data
public class IssueCommentsRepository {

    private List<Comment> issueComments = new ArrayList<>();

    public void addComment(Comment comment) {
        this.issueComments.add(comment);
    }

    public List<Comment> getCommentsByIssueID(String issueId) {
        return this.issueComments.stream().filter(comment -> issueId.equalsIgnoreCase(comment.getIssueId())).collect(Collectors.toList());
    }

    public List<Comment> getCommentsByAuthor(String author) {
        return this.issueComments.stream().filter(comment -> author.equalsIgnoreCase(comment.getAuthor())).collect(Collectors.toList());
    }
}
