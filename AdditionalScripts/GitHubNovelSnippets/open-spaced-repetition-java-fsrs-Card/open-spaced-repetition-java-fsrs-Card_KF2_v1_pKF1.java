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
    private Instant dueDate;
    private Instant lastReviewDate;

    private Card(@NonNull Builder builder) {
        this.id = builder.id;
        this.state = builder.state;
        this.learningStep = builder.learningStep;
        this.stability = builder.stability;
        this.difficulty = builder.difficulty;
        this.dueDate = builder.dueDate;
        this.lastReviewDate = builder.lastReviewDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

        private Integer id = null;

        private State state = State.LEARNING;
        private Integer learningStep = null;
        private Double stability = null;
        private Double difficulty = null;
        private Instant dueDate = null;
        private Instant lastReviewDate = null;

        public Card build() {
            if (this.id == null) {
                this.id = (int) Instant.now().toEpochMilli();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                }
            }

            if (this.state == State.LEARNING && this.learningStep == null) {
                this.learningStep = 0;
            }

            if (this.dueDate == null) {
                this.dueDate = Instant.now();
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
        this.dueDate = sourceCard.dueDate;
        this.lastReviewDate = sourceCard.lastReviewDate;
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