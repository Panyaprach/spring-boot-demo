package com.example.demo.jpa.model.binding;

import com.example.demo.jpa.model.Category;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {

    @Override
    public String convertToDatabaseColumn(Category category) {
        return category == null ? null : category.getCode();
    }

    @Override
    public Category convertToEntityAttribute(String code) {
        if (code == null)
            return null;

        return Stream.of(Category.values())
                .filter(category -> category.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to convert code to category: %s", code)));
    }
}
