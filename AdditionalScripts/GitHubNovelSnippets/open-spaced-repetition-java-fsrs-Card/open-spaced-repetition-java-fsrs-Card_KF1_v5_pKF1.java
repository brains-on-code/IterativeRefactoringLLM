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
@JsonDeserialize(builder = CardState.CardStateBuilder.class)
public class CardState {

    private final int id;

    private State state;
    private Integer repetitionCount;
    private Double easeFactor;
    private Double interval;
    private Instant createdAt;
    private Instant updatedAt;

    private CardState(@NonNull CardStateBuilder builder) {
        this.id = builder.cardId;
        this.state = builder.state;
        this.repetitionCount = builder.repetitionCount;
        this.easeFactor = builder.easeFactor;
        this.interval = builder.interval;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public static CardStateBuilder builder() {
        return new CardStateBuilder();
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    @JsonPOJOBuilder(withPrefix = "")
    public static class CardStateBuilder {

        private Integer cardId = null;

        private State state = State.LEARNING;
        private Integer repetitionCount = null;
        private Double easeFactor = null;
        private Double interval = null;
        private Instant createdAt = null;
        private Instant updatedAt = null;

        public CardState build() {
            initializeIdIfMissing();
            initializeRepetitionCountForLearningState();
            initializeCreatedAtIfMissing();
            return new CardState(this);
        }

        private void initializeIdIfMissing() {
            if (this.cardId != null) {
                return;
            }

            this.cardId = (int) Instant.now().toEpochMilli();
            try {
                Thread.sleep(1);
            } catch (InterruptedException interruption) {
                Thread.currentThread().interrupt();
            }
        }

        private void initializeRepetitionCountForLearningState() {
            if (this.state == State.LEARNING && this.repetitionCount == null) {
                this.repetitionCount = 0;
            }
        }

        private void initializeCreatedAtIfMissing() {
            if (this.createdAt == null) {
                this.createdAt = Instant.now();
            }
        }
    }

    public CardState(@NonNull CardState source) {
        this.id = source.id;
        this.state = source.state;
        this.repetitionCount = source.repetitionCount;
        this.easeFactor = source.easeFactor;
        this.interval = source.interval;
        this.createdAt = source.createdAt;
        this.updatedAt = source.updatedAt;
    }

    public String toJson() {
        ObjectMapper objectMapper = createConfiguredObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException jsonException) {
            throw new RuntimeException(jsonException);
        }
    }

    public static CardState fromJson(@NonNull String json) {
        ObjectMapper objectMapper = createConfiguredObjectMapper();
        try {
            return objectMapper.readValue(json, CardState.class);
        } catch (JsonProcessingException jsonException) {
            throw new RuntimeException(jsonException);
        }
    }

    private static ObjectMapper createConfiguredObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }
}