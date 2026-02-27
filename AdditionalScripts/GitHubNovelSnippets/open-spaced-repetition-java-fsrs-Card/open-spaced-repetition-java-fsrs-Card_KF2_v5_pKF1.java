package io.github.openspacedrepetition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonDeserialize(builder = Card.Builder.class)
public class Card {

    private final int id;

    private State reviewState;
    private Integer learningStepIndex;
    private Double stabilityScore;
    private Double difficultyRating;
    private Instant dueAt;
    private Instant lastReviewedAt;

    private Card(@NonNull Builder builder) {
        this.id = builder.id;
        this.reviewState = builder.reviewState;
        this.learningStepIndex = builder.learningStepIndex;
        this.stabilityScore = builder.stabilityScore;
        this.difficultyRating = builder.difficultyRating;
        this.dueAt = builder.dueAt;
        this.lastReviewedAt = builder.lastReviewedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

        private Integer id = null;

        private State reviewState = State.LEARNING;
        private Integer learningStepIndex = null;
        private Double stabilityScore = null;
        private Double difficultyRating = null;
        private Instant dueAt = null;
        private Instant lastReviewedAt = null;

        public Card build() {
            if (this.id == null) {
                this.id = (int) Instant.now().toEpochMilli();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                }
            }

            if (this.reviewState == State.LEARNING && this.learningStepIndex == null) {
                this.learningStepIndex = 0;
            }

            if (this.dueAt == null) {
                this.dueAt = Instant.now();
            }

            return new Card(this);
        }
    }

    public Card(@NonNull Card sourceCard) {
        this.id = sourceCard.id;
        this.reviewState = sourceCard.reviewState;
        this.learningStepIndex = sourceCard.learningStepIndex;
        this.stabilityScore = sourceCard.stabilityScore;
        this.difficultyRating = sourceCard.difficultyRating;
        this.dueAt = sourceCard.dueAt;
        this.lastReviewedAt = sourceCard.lastReviewedAt;
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new RuntimeException(jsonProcessingException);
        }
    }

    public static Card fromJson(@NonNull String json) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return objectMapper.readValue(json, Card.class);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new RuntimeException(jsonProcessingException);
        }
    }
}