package com.julesn.uabrewwarehouse.persistence.ingredients;

import com.julesn.uabrewwarehouse.domain.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends MongoRepository<Ingredient, String>, IngredientRepositoryCustom {
}
