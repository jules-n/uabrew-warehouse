package com.julesn.uabrewwarehouse.persistence.ingredients;

import com.julesn.uabrewwarehouse.domain.Ingredient;

public interface IngredientRepositoryCustom {

    Ingredient getIngredientByName(String name);
}
