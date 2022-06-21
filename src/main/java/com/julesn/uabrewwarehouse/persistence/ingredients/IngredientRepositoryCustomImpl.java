package com.julesn.uabrewwarehouse.persistence.ingredients;

import com.julesn.uabrewwarehouse.domain.Ingredient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Map;

public class IngredientRepositoryCustomImpl implements IngredientRepositoryCustom {

    private MongoTemplate mongoTemplate;

    public IngredientRepositoryCustomImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Ingredient getIngredientByCriteria(Map<String, Object> map) {
        Query query = new Query();
        map.entrySet().forEach(entry -> query.addCriteria(new Criteria(entry.getKey()).is(entry.getValue())));
        return mongoTemplate.findOne(query, Ingredient.class, Ingredient.collection);
    }

    @Override
    public boolean deleteIngredientByCriteria(Map<String, Object> map) {
        Query query = new Query();
        map.entrySet().forEach(entry -> query.addCriteria(new Criteria(entry.getKey()).is(entry.getValue())));
        return mongoTemplate.remove(query, Ingredient.class, Ingredient.collection).getDeletedCount() == 1;
    }

    @Override
    public boolean updateIngredientByName(Ingredient ingredient, String name) {
        Query query = new Query();
        query.addCriteria(new Criteria("name").is(name));
        query.addCriteria(new Criteria("bar").is(ingredient.getBar()));

        var update = new Update();
        update.set("totalAmount", ingredient.getTotalAmount());
        update.set("name", ingredient.getName());
        return mongoTemplate.updateFirst(query, update, Ingredient.class, Ingredient.collection).wasAcknowledged();
    }
}
