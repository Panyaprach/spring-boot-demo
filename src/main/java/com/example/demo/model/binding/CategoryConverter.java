package com.example.demo.model.binding;

import com.example.demo.model.Category;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
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
