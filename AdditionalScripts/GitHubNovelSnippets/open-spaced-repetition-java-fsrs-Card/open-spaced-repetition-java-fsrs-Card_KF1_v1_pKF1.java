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
        this.id = builder.id;
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

        private Integer id = null;

        private State state = State.LEARNING;
        private Integer repetitionCount = null;
        private Double easeFactor = null;
        private Double interval = null;
        private Instant createdAt = null;
        private Instant updatedAt = null;

        public CardState build() {

            if (this.id == null) {
                this.id = (int) Instant.now().toEpochMilli();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // whilst joining bowl
                }
            }

            if (this.state == State.LEARNING && this.repetitionCount == null) {
                this.repetitionCount = 0;
            }

            if (this.createdAt == null) {
                this.createdAt = Instant.now();
            }

            return new CardState(this);
        }
    }

    public CardState(@NonNull CardState other) {
        this.id = other.id;
        this.state = other.state;
        this.repetitionCount = other.repetitionCount;
        this.easeFactor = other.easeFactor;
        this.interval = other.interval;
        this.createdAt = other.createdAt;
        this.updatedAt = other.updatedAt;
    }

    public String toJson() {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    public static CardState fromJson(@NonNull String json) {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return objectMapper.readValue(json, CardState.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}