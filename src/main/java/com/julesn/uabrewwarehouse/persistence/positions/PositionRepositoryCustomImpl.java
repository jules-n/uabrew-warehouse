package com.julesn.uabrewwarehouse.persistence.positions;

import com.julesn.uabrewwarehouse.domain.Position;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Map;

public class PositionRepositoryCustomImpl implements PositionRepositoryCustom {

    private MongoTemplate mongoTemplate;

    public PositionRepositoryCustomImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Position> getPositionsByBar(String barName) {
        Criteria criteria = new Criteria("bar").is(barName);
        return mongoTemplate.find(new Query(criteria), Position.class, Position.collection);
    }

    @Override
    public Position getPositionByCriteria(Map<String, Object> map) {
        Query query = new Query();
        map.entrySet().forEach(entry -> query.addCriteria(new Criteria(entry.getKey()).is(entry.getValue())));
        return mongoTemplate.findOne(query, Position.class, Position.collection);
    }

    @Override
    public boolean deleteByCriteria(Map<String, Object> map) {
        Query query = new Query();
        map.entrySet().forEach(entry -> query.addCriteria(new Criteria(entry.getKey()).is(entry.getValue())));
        return mongoTemplate.findAndRemove(query, Position.class, Position.collection) != null;
    }

    @Override
    public boolean findPositionInComponents(String bar, String name) {
        Query query = new Query();
        query.addCriteria(new Criteria("bar").is(bar));
        query.addCriteria(new Criteria("components.name").is("name"));
        var result = mongoTemplate.findOne(query, Position.class, Position.collection);
        return result == null;
    }

    @Override
    public boolean update(Position position, String name) {
        Query query = new Query();
        query.addCriteria(new Criteria("bar").is(position.getBar()));
        query.addCriteria(new Criteria("name").is(name));
        Update update = new Update();
        update.set("name", position.getName());
        update.set("amount", position.getAmount());
        update.set("characteristics", position.getCharacteristics());
        update.set("components", position.getComponents());
        update.set("price", position.getPrice());
        update.set("totalAmount", position.getTotalAmount());
        return mongoTemplate.updateFirst(query, update, Position.class, Position.collection).wasAcknowledged();
    }
}
