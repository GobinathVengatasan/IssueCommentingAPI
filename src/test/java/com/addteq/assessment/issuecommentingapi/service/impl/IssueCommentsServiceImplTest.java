package com.addteq.assessment.issuecommentingapi.service.impl;

import com.addteq.assessment.issuecommentingapi.exception.IssueCommentingException;
import com.addteq.assessment.issuecommentingapi.model.Comment;
import com.addteq.assessment.issuecommentingapi.repository.IssueCommentsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IssueCommentsServiceImplTest {

    @InjectMocks
    private IssueCommentsServiceImpl issueCommentsServiceImpl;

    @Mock
    private IssueCommentsRepository issueCommentsRepository;

    private List<Comment> comments;

    @BeforeEach
    void setUp() {
        comments = Arrays.asList(
                Comment.builder().issueId("issue-id-1234").author("Gobinath").message("Remove unused imports").build(),
                Comment.builder().author("Gobinath").message("Remove wildcard imports").build(),
                Comment.builder().author("Chayan").message("Add null checks to avoid NPE").build(),
                Comment.builder().author("Chayan").message("Remove duplicate condition checks").build()
        );
    }

    @Test
    void addComment() {
        assertDoesNotThrow(() -> issueCommentsServiceImpl.addComment(comments.get(0)));
        assertDoesNotThrow(() -> issueCommentsServiceImpl.addComment(comments.get(1)));
        assertDoesNotThrow(() -> issueCommentsServiceImpl.addComment(comments.get(2)));
        assertDoesNotThrow(() -> issueCommentsServiceImpl.addComment(comments.get(3)));
    }

    @Test
    void addingNullCommentExceptionFlow() {
        issueCommentsServiceImpl.issueCommentsRepository = null;
        assertThrows(IssueCommentingException.class, ()->issueCommentsServiceImpl.addComment(null));
    }

    @Test
    void addCommentOtherExceptionFlow() {
        issueCommentsServiceImpl.issueCommentsRepository = null;
        assertThrows(IssueCommentingException.class, ()->issueCommentsServiceImpl.addComment(comments.get(0)));
    }

    @Test
    void getCommentsByIssueID() {
        when(issueCommentsRepository.getCommentsByIssueID("issue-id-1234")).thenReturn(Arrays.asList(comments.get(0)));
        comments = issueCommentsServiceImpl.getCommentsByIssueID("issue-id-1234");
        assertEquals("issue-id-1234", comments.get(0).getIssueId());
        assertEquals("Gobinath", comments.get(0).getAuthor());
        assertEquals("Remove unused imports", comments.get(0).getMessage());
    }

    @Test
    void getCommentsByAuthor() {
        when(issueCommentsRepository.getCommentsByAuthor("Gobinath")).thenReturn(Arrays.asList(comments.get(0),comments.get(1)));
        comments = issueCommentsServiceImpl.getCommentsByAuthor("Gobinath");
        assertEquals("issue-id-1234", comments.get(0).getIssueId());
        assertEquals("Gobinath", comments.get(0).getAuthor());
        assertEquals("Remove unused imports", comments.get(0).getMessage());

        assertEquals("Gobinath", comments.get(1).getAuthor());
        assertEquals("Remove wildcard imports", comments.get(1).getMessage());
    }
}