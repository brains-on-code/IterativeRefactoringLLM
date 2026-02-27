/* (C)2025 */
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

    private State state;
    private Integer learningStep;
    private Double stability;
    private Double difficulty;
    private Instant dueAt;
    private Instant lastReviewedAt;

    private Card(@NonNull Builder builder) {
        this.id = builder.cardId;
        this.state = builder.state;
        this.learningStep = builder.learningStep;
        this.stability = builder.stability;
        this.difficulty = builder.difficulty;
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

        private Integer cardId = null;

        private State state = State.LEARNING;
        private Integer learningStep = null;
        private Double stability = null;
        private Double difficulty = null;
        private Instant dueAt = null;
        private Instant lastReviewedAt = null;

        public Card build() {

            if (this.cardId == null) {
                this.cardId = (int) Instant.now().toEpochMilli();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                }
            }

            if (this.state == State.LEARNING && this.learningStep == null) {
                this.learningStep = 0;
            }

            if (this.dueAt == null) {
                this.dueAt = Instant.now();
            }

            return new Card(this);
        }
    }

    public Card(@NonNull Card sourceCard) {
        this.id = sourceCard.id;
        this.state = sourceCard.state;
        this.learningStep = sourceCard.learningStep;
        this.stability = sourceCard.stability;
        this.difficulty = sourceCard.difficulty;
        this.dueAt = sourceCard.dueAt;
        this.lastReviewedAt = sourceCard.lastReviewedAt;
    }

    public String toJson() {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Card fromJson(@NonNull String json) {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return objectMapper.readValue(json, Card.class);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }
}