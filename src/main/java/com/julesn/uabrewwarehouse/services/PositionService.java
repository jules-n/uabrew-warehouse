package com.julesn.uabrewwarehouse.services;

import com.julesn.uabrewwarehouse.domain.Position;

public interface PositionService {

    boolean save(Position position);

    boolean update(Position position, String name);

    boolean delete(String bar, String name);
}
