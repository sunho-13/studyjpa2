package com.jpa.ICategory;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDto implements ICategory{
    private Long id;
    private String name;
}
