package com.julesn.uabrewwarehouse.controllers;

import com.julesn.uabrewwarehouse.domain.Position;
import com.julesn.uabrewwarehouse.dtos.MenuPosition;
import com.julesn.uabrewwarehouse.services.PositionService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/positions")
public class PositionsController {

    @Setter(onMethod_ = {@Autowired})
    private PositionService positionService;

    @Setter(onMethod_ = {@Autowired})
    private ModelMapper modelMapper;

    @PostMapping("/{bar}")
    ResponseEntity addPosition(@PathVariable("bar") String bar, @RequestBody MenuPosition menuPosition) {
        var position = modelMapper.map(menuPosition, Position.class);
        position.setBar(bar);
        return positionService.save(position) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{bar}/{name}")
    ResponseEntity updatePosition(@PathVariable("bar") String bar, @PathVariable("name") String name, @RequestBody MenuPosition menuPosition) {
        var position = modelMapper.map(menuPosition, Position.class);
        position.setBar(bar);
        return positionService.update(position, name) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{bar}/{name}")
    ResponseEntity deletePosition(@PathVariable("bar") String bar, @PathVariable("name") String name) {
        return positionService.delete(bar, name) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
