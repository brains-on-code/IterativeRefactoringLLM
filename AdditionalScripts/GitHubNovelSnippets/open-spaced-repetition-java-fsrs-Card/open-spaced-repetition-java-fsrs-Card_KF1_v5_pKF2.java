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
@JsonDeserialize(builder = Card.CardBuilder.class)
public class Card {

    /** Unique identifier for this card. */
    private final int id;

    /** Current learning state of the card. */
    private State state;

    /** Total number of reviews performed for this card. */
    private Integer reviewCount;

    /** Ease factor used by the scheduling algorithm. */
    private Double easeFactor;

    /** Current interval (e.g., in days) until the next review. */
    private Double interval;

    /** Timestamp when the card was created. */
    private Instant createdAt;

    /** Timestamp when the next review is scheduled. */
    private Instant nextReviewAt;

    private Card(@NonNull CardBuilder builder) {
        this.id = builder.id;
        this.state = builder.state;
        this.reviewCount = builder.reviewCount;
        this.easeFactor = builder.easeFactor;
        this.interval = builder.interval;
        this.createdAt = builder.createdAt;
        this.nextReviewAt = builder.nextReviewAt;
    }

    /** Returns a new builder instance for {@link Card}. */
    public static CardBuilder builder() {
        return new CardBuilder();
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    @JsonPOJOBuilder(withPrefix = "")
    public static class CardBuilder {

        /**
         * Unique identifier for the card.
         *
         * <p>If {@code null}, a value derived from the current time in milliseconds is used.</p>
         */
        private Integer id;

        /** Initial learning state (defaults to {@link State#LEARNING}). */
        private State state = State.LEARNING;

        /**
         * Number of times the card has been reviewed.
         *
         * <p>If {@code null} and {@code state == State.LEARNING}, defaults to {@code 0}.</p>
         */
        private Integer reviewCount;

        /** Ease factor used by the scheduling algorithm. */
        private Double easeFactor;

        /** Interval (e.g., in days) until the next review. */
        private Double interval;

        /**
         * Creation timestamp.
         *
         * <p>If {@code null}, defaults to {@link Instant#now()}.</p>
         */
        private Instant createdAt;

        /** Scheduled time for the next review. */
        private Instant nextReviewAt;

        /** Builds a {@link Card}, applying default values where needed. */
        public Card build() {
            assignDefaultIdIfNecessary();
            assignDefaultReviewCountIfNecessary();
            assignDefaultCreatedAtIfNecessary();
            return new Card(this);
        }

        private void assignDefaultIdIfNecessary() {
            if (this.id != null) {
                return;
            }
            this.id = (int) Instant.now().toEpochMilli();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void assignDefaultReviewCountIfNecessary() {
            if (this.state == State.LEARNING && this.reviewCount == null) {
                this.reviewCount = 0;
            }
        }

        private void assignDefaultCreatedAtIfNecessary() {
            if (this.createdAt == null) {
                this.createdAt = Instant.now();
            }
        }
    }

    /**
     * Creates a new {@link Card} by copying all fields from another instance.
     *
     * @param other card to copy
     */
    public Card(@NonNull Card other) {
        this.id = other.id;
        this.state = other.state;
        this.reviewCount = other.reviewCount;
        this.easeFactor = other.easeFactor;
        this.interval = other.interval;
        this.createdAt = other.createdAt;
        this.nextReviewAt = other.nextReviewAt;
    }

    /** Serializes this card to a JSON string. */
    public String toJson() {
        ObjectMapper mapper = createObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Card to JSON", e);
        }
    }

    /**
     * Deserializes a {@link Card} from its JSON representation.
     *
     * @param json JSON string representing a card
     * @return deserialized {@link Card}
     */
    public static Card fromJson(@NonNull String json) {
        ObjectMapper mapper = createObjectMapper();
        try {
            return mapper.readValue(json, Card.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize Card from JSON", e);
        }
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}