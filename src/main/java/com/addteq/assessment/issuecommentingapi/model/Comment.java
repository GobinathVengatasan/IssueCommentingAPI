package com.addteq.assessment.issuecommentingapi.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    String id;

    String issueId = UUID.randomUUID().toString();

    @NotEmpty(message = "Author must not be null/empty")
    String author;

    @NotEmpty(message = "Message must not be null/empty")
    String message;

    Instant createdAt = Instant.now();
}
