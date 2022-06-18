package com.julesn.uabrewwarehouse.persistence.positions;

import com.julesn.uabrewwarehouse.domain.Position;
import com.julesn.uabrewwarehouse.persistence.positions.PositionRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class PositionRepositoryCustomImpl implements PositionRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Position> getPositionsByBar(String barName) {
        Criteria criteria = new Criteria("bar").is(barName);
        return mongoTemplate.find(new Query(criteria), Position.class, Position.collection);
    }

    @Override
    public Position getPositionByName(String name) {
        Criteria criteria = new Criteria("name").is(name);
        return mongoTemplate.findOne(new Query(criteria), Position.class, Position.collection);
    }
}
