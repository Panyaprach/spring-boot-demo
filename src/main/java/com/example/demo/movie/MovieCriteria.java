package com.example.demo.movie;

import com.example.demo.jpa.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieCriteria {
    private String name;
    private Category category;
    private String tag;
}
