package com.julesn.uabrewwarehouse.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = Position.collection)
public class Position extends Product {
    public static final String collection = "positions";
    @Nullable
    private List<Characteristic> characteristics;
    @Nullable
    private List<Component> components;
    private Integer price;
}
