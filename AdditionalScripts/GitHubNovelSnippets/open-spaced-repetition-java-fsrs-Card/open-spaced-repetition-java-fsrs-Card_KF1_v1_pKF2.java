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
@JsonDeserialize(builder = Class1.Class2.class)
public class Class1 {

    private final int var3;

    private State var4;
    private Integer var5;
    private Double var6;
    private Double var7;
    private Instant var8;
    private Instant var9;

    private Class1(@NonNull Class2 builder) {
        this.var3 = builder.var3;
        this.var4 = builder.var4;
        this.var5 = builder.var5;
        this.var6 = builder.var6;
        this.var7 = builder.var7;
        this.var8 = builder.var8;
        this.var9 = builder.var9;
    }

    public static Class2 method1() {
        return new Class2();
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    @JsonPOJOBuilder(withPrefix = "")
    public static class Class2 {

        private Integer var3 = null;

        private State var4 = State.LEARNING;
        private Integer var5 = null;
        private Double var6 = null;
        private Double var7 = null;
        private Instant var8 = null;
        private Instant var9 = null;

        public Class1 method2() {

            if (this.var3 == null) {
                this.var3 = (int) Instant.now().toEpochMilli();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // preserve interrupt status
                }
            }

            if (this.var4 == State.LEARNING && this.var5 == null) {
                this.var5 = 0;
            }

            if (this.var8 == null) {
                this.var8 = Instant.now();
            }

            return new Class1(this);
        }
    }

    public Class1(@NonNull Class1 other) {
        this.var3 = other.var3;
        this.var4 = other.var4;
        this.var5 = other.var5;
        this.var6 = other.var6;
        this.var7 = other.var7;
        this.var8 = other.var8;
        this.var9 = other.var9;
    }

    public String method3() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class1 method4(@NonNull String json) {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return mapper.readValue(json, Class1.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}