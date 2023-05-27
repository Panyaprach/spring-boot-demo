package com.example.demo.model.binding;

import com.example.demo.jpa.model.Category;
import com.example.demo.jpa.model.binding.CategoryConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.demo.jpa.model.Category.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryConverterTest {
    CategoryConverter converter = new CategoryConverter();

    private static Stream<Arguments> provideExpectedCategories() {
        return Stream.of(
                Arguments.of(ACTION, "Ac"),
                Arguments.of(ANIMATION, "A"),
                Arguments.of(ADVENTURE, "Ad"),
                Arguments.of(CRIME, "Cr"),
                Arguments.of(COMEDY, "C"),
                Arguments.of(DRAMA, "D"),
                Arguments.of(DOCUMENTARIES, "Do"),
                Arguments.of(EROTIC, "E"),
                Arguments.of(FAMILY, "Fa"),
                Arguments.of(FANTASY, "F"),
                Arguments.of(GENRE, "G"),
                Arguments.of(ROMANCE, "R"),
                Arguments.of(SCI_FI, "S"),
                Arguments.of(MUSIC, "M"),
                Arguments.of(TECHNOLOGY, "T")
        );
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @EnumSource(Category.class)
    public void givenArgument_shouldCoverAllField(Category category) {
        boolean hasProvided = provideExpectedCategories()
                .map(Arguments::get)
                .map(objects -> (Category) objects[0])
                .anyMatch(category::equals);

        assertTrue(hasProvided);
    }

    @ParameterizedTest(name = "[{index}] {0} is {1}")
    @MethodSource("provideExpectedCategories")
    public void whenConvertToDBColumn_thenSuccess(Category category, String expected) {
        String actual = converter.convertToDatabaseColumn(category);

        assertEquals(expected, actual, "Failed to convert category");
    }

    @ParameterizedTest(name = "[{index}] {1} is {0}")
    @MethodSource("provideExpectedCategories")
    public void whenConvertToEntityAttribute_thenSuccess(Category expected, String code) {
        Category actual = converter.convertToEntityAttribute(code);

        assertEquals(expected, actual, "Failed to convert category");
    }

    @Test
    public void givenNull_whenConvertToDBColumn_shouldBeNull() {
        String dbColumn = converter.convertToDatabaseColumn(null);

        assertNull(dbColumn);
    }

    @Test
    public void givenNull_whenConvertToEntityAttribute_shouldBeNull() {
        Category category = converter.convertToEntityAttribute(null);

        assertNull(category);
    }
}