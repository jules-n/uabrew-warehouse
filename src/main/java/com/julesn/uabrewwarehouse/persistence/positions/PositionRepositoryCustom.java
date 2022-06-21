package com.julesn.uabrewwarehouse.persistence.positions;

import com.julesn.uabrewwarehouse.domain.Position;

import java.util.List;
import java.util.Map;

public interface PositionRepositoryCustom {

    List<Position> getPositionsByBar(String barName);
    Position getPositionByCriteria(Map<String, Object> map);
    boolean deleteByCriteria(Map<String, Object> map);
    boolean findPositionInComponents(String bar, String name);
    boolean update(Position position, String name);
}
