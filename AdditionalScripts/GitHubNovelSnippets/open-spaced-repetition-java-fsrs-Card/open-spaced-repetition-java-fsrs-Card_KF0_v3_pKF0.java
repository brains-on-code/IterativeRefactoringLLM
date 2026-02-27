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

    private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    private final int cardId;

    private State state;
    private Integer step;
    private Double stability;
    private Double difficulty;
    private Instant due;
    private Instant lastReview;

    private Card(@NonNull Builder builder) {
        this.cardId = builder.cardId;
        this.state = builder.state;
        this.step = builder.step;
        this.stability = builder.stability;
        this.difficulty = builder.difficulty;
        this.due = builder.due;
        this.lastReview = builder.lastReview;
    }

    public Card(@NonNull Card otherCard) {
        this.cardId = otherCard.cardId;
        this.state = otherCard.state;
        this.step = otherCard.step;
        this.stability = otherCard.stability;
        this.difficulty = otherCard.difficulty;
        this.due = otherCard.due;
        this.lastReview = otherCard.lastReview;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String toJson() {
        try {
            return OBJECT_MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Card to JSON", e);
        }
    }

    public static Card fromJson(@NonNull String json) {
        try {
            return OBJECT_MAPPER.readValue(json, Card.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize Card from JSON", e);
        }
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

        private Integer cardId;

        private State state = State.LEARNING;
        private Integer step;
        private Double stability;
        private Double difficulty;
        private Instant due;
        private Instant lastReview;

        public Card build() {
            ensureCardId();
            ensureStepForLearningState();
            ensureDueDate();
            return new Card(this);
        }

        private void ensureCardId() {
            if (cardId != null) {
                return;
            }
            cardId = (int) Instant.now().toEpochMilli();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void ensureStepForLearningState() {
            if (state == State.LEARNING && step == null) {
                step = 0;
            }
        }

        private void ensureDueDate() {
            if (due == null) {
                due = Instant.now();
            }
        }
    }
}