package com.example.demo.graphql.scalar;

import graphql.language.StringValue;
import graphql.schema.*;
import jakarta.validation.constraints.NotNull;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Function;

public class InstantScalar {
    public static final GraphQLScalarType INSTANCE;

    static {
        Coercing<Instant, String> coercing = new Coercing<Instant, String>() {
            private String typeName(Object input) {
                return input == null ? "null" : input.getClass().getSimpleName();
            }

            private Instant parseInstant(String value, Function<String, RuntimeException> exceptionBuilder) {
                try {
                    return Instant.parse(value);
                } catch (DateTimeParseException e) {
                    throw exceptionBuilder.apply("Invalid RFC3339 value : '" + value + "'. because of : '" + e.getMessage() + "'");
                }
            }

            @Override
            public String serialize(@NotNull Object input) throws CoercingSerializeException {
                Instant val;
                if (input instanceof Instant) {
                    val = (Instant) input;
                } else if (input instanceof String) {
                    val = parseInstant(input.toString(), CoercingSerializeException::new);
                } else {
                    throw new CoercingSerializeException("Expected something we can convert to 'java.time.OffsetDateTime' but was '" + typeName(input) + "'.");
                }

                try {
                    return DateTimeFormatter.ISO_INSTANT.format(val);
                } catch (DateTimeException e) {
                    throw new CoercingSerializeException("Unable to turn TemporalAccessor into Instant because of : '" + e.getMessage() + "'.");
                }
            }

            @Override
            public @NotNull
            Instant parseValue(@NotNull Object input) throws CoercingParseValueException {
                Instant val;
                if (input instanceof Instant) {
                    val = (Instant) input;
                } else if (input instanceof String) {
                    val = Instant.parse(input.toString());
                } else {
                    throw new CoercingSerializeException("Expected something we can convert to 'java.time.OffsetDateTime' but was '" + typeName(input) + "'.");
                }

                return val;
            }

            @Override
            public @NotNull
            Instant parseLiteral(@NotNull Object input) throws CoercingParseLiteralException {
                if (!(input instanceof StringValue)) {
                    throw new CoercingParseLiteralException("Expected AST type 'StringValue' but was '" + typeName(input) + "'.");
                } else {
                    return parseInstant(((StringValue) input).getValue(), CoercingParseLiteralException::new);
                }
            }
        };

        INSTANCE = GraphQLScalarType.newScalar().name("Instant")
                .description("An RFC-3339 compliant Instant Scalar")
                .coercing(coercing)
                .build();
    }
}
