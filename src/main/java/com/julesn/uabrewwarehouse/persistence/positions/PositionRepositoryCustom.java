package com.julesn.uabrewwarehouse.persistence.positions;

import com.julesn.uabrewwarehouse.domain.Position;

import java.util.List;

public interface PositionRepositoryCustom {

    List<Position> getPositionsByBar(String barName);
    Position getPositionByName(String name);
}
