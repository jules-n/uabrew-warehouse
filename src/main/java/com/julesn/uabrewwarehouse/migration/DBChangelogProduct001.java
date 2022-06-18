package com.julesn.uabrewwarehouse.migration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.julesn.uabrewwarehouse.domain.Ingredient;
import com.julesn.uabrewwarehouse.domain.Position;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

import java.util.HashMap;

@ChangeLog
public class DBChangelogProduct001 {

    @ChangeSet(order = "001", id = "creating indexes for products", author = "jules-n")
    public void insertProductsIndexes(MongoDatabase mongo) {
        var collection = mongo.getCollection(Position.collection);
        collection.createIndex(
                new BasicDBObject(
                        new HashMap(){{
                            put("bar", 1);
                            put("name", 1);
                        }}), new IndexOptions().unique(true));
        collection = mongo.getCollection(Ingredient.collection);
        collection.createIndex(
                new BasicDBObject(
                        new HashMap(){{
                            put("bar", 1);
                            put("name", 1);
                        }}), new IndexOptions().unique(true));
    }

}
