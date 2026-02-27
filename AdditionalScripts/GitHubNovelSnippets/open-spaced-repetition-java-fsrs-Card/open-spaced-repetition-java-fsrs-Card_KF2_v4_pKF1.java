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

    public Card(@NonNull Card source) {
        this.id = source.id;
        this.reviewState = source.reviewState;
        this.learningStepIndex = source.learningStepIndex;
        this.stabilityScore = source.stabilityScore;
        this.difficultyRating = source.difficultyRating;
        this.dueAt = source.dueAt;
        this.lastReviewedAt = source.lastReviewedAt;
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Card fromJson(@NonNull String json) {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return mapper.readValue(json, Card.class);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }
}