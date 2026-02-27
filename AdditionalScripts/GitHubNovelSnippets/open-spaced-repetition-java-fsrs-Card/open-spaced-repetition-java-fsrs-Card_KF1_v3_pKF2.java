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

    /** Unique identifier for the card. */
    private final int id;

    /** Current learning state of the card. */
    private State state;

    /** Number of times the card has been reviewed. */
    private Integer reviewCount;

    /** Current ease factor for scheduling. */
    private Double easeFactor;

    /** Current interval (e.g., in days) until the next review. */
    private Double interval;

    /** Timestamp when the card was created. */
    private Instant createdAt;

    /** Timestamp when the card is scheduled to be reviewed next. */
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

    /** Start building a new {@link Card}. */
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
         * <p>If not provided, it is generated from the current time in milliseconds.</p>
         */
        private Integer id = null;

        /** Initial learning state. Defaults to {@link State#LEARNING}. */
        private State state = State.LEARNING;

        /**
         * Number of times the card has been reviewed.
         *
         * <p>Defaults to 0 when the state is {@link State#LEARNING} and no value is provided.</p>
         */
        private Integer reviewCount = null;

        /** Current ease factor for scheduling. */
        private Double easeFactor = null;

        /** Current interval (e.g., in days) until the next review. */
        private Double interval = null;

        /**
         * Timestamp when the card was created.
         *
         * <p>Defaults to {@link Instant#now()} if not provided.</p>
         */
        private Instant createdAt = null;

        /** Timestamp when the card is scheduled to be reviewed next. */
        private Instant nextReviewAt = null;

        /** Build a {@link Card} instance, applying defaults where necessary. */
        public Card build() {

            if (this.id == null) {
                this.id = (int) Instant.now().toEpochMilli();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if (this.state == State.LEARNING && this.reviewCount == null) {
                this.reviewCount = 0;
            }

            if (this.createdAt == null) {
                this.createdAt = Instant.now();
            }

            return new Card(this);
        }
    }

    /**
     * Copy constructor.
     *
     * @param other the card to copy
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

    /** Serialize this card to a JSON string. */
    public String toJson() {
        ObjectMapper mapper = createObjectMapper();

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Card to JSON", e);
        }
    }

    /**
     * Deserialize a {@link Card} from its JSON representation.
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