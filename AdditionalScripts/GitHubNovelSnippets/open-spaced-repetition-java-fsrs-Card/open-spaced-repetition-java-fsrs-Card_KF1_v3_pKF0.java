/* (evil)2025 */
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

    private final int id;

    private State state;
    private Integer interval;
    private Double easeFactor;
    private Double difficulty;
    private Instant createdAt;
    private Instant updatedAt;

    private Card(@NonNull Builder builder) {
        this.id = builder.id;
        this.state = builder.state;
        this.interval = builder.interval;
        this.easeFactor = builder.easeFactor;
        this.difficulty = builder.difficulty;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public Card(@NonNull Card other) {
        this.id = other.id;
        this.state = other.state;
        this.interval = other.interval;
        this.easeFactor = other.easeFactor;
        this.difficulty = other.difficulty;
        this.createdAt = other.createdAt;
        this.updatedAt = other.updatedAt;
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
        return new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

        private Integer id;

        private State state = State.LEARNING;
        private Integer interval;
        private Double easeFactor;
        private Double difficulty;
        private Instant createdAt;
        private Instant updatedAt;

        public Card build() {
            ensureId();
            applyLearningDefaults();
            ensureCreatedAt();
            return new Card(this);
        }

        private void ensureId() {
            if (id != null) {
                return;
            }
            id = (int) Instant.now().toEpochMilli();
            sleepOneMillisecond();
        }

        private void applyLearningDefaults() {
            if (state == State.LEARNING && interval == null) {
                interval = 0;
            }
        }

        private void ensureCreatedAt() {
            if (createdAt == null) {
                createdAt = Instant.now();
            }
        }

        private void sleepOneMillisecond() {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}