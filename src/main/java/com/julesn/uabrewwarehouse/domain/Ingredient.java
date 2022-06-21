package com.julesn.uabrewwarehouse.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Builder
@Document(collection = Ingredient.collection)
public class Ingredient extends Product {
    public static final String collection = "ingredients";
}
