package com.julesn.uabrewwarehouse.persistence.positions;

import com.julesn.uabrewwarehouse.domain.Position;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends MongoRepository<Position, String>, PositionRepositoryCustom {
}
