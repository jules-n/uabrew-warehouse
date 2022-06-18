package com.julesn.uabrewwarehouse.persistence.ingredients;

import com.julesn.uabrewwarehouse.domain.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class IngredientRepositoryCustomImpl implements IngredientRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Ingredient getIngredientByName(String name) {
        Criteria criteria = new Criteria("name").is(name);
        return mongoTemplate.findOne(new Query(criteria), Ingredient.class, Ingredient.collection);
    }
}
