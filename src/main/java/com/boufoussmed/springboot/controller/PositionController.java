package com.boufoussmed.springboot.controller;

import com.boufoussmed.springboot.exception.ResourceNotFoundException;
import com.boufoussmed.springboot.model.Position;
import com.boufoussmed.springboot.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@RequestMapping("/api/v1/")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PositionController {

    @Autowired
    private PositionRepository positionRepository;

    @GetMapping("/positions")
    public List<Position> getPositions() {
        return positionRepository.findAll();
    }

    @PostMapping("/positions")
    public Position addPosition(@RequestBody(required = true) Position position) {
        return this.positionRepository.save(position);
    }

 /*   @GetMapping("/positions/{id}")
    public Position getPositionById(@PathVariable("id") Long id){
        return positionRepository.findById(id).get();
    }*/

    @GetMapping("/positions/{id}")
    public ResponseEntity<Position> getEmployeeById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(positionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Position not exist : " + id
        )));
    }

    @PutMapping("/positions/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable("id") Long id, @RequestBody Position position) {

        ResponseEntity<Position> rep = ResponseEntity.ok(positionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Position not exist : " + id
        )));

        Position pos = rep.getBody();

        pos.setPositionRank(position.getPositionRank());
        pos.setDiploma(position.getDiploma());
        pos.setName(position.getName());
        pos.setSalary(position.getSalary());

        return ResponseEntity.ok(positionRepository.save(pos));
    }

    @DeleteMapping("positions/{id}")
    public ResponseEntity<Map<String,Boolean>> deletePosition(@PathVariable("id") Long id){
        Map<String, Boolean> deleted = new HashMap<String, Boolean>();
        if(id!=null && positionRepository.findById(id).isPresent()){
            positionRepository.deleteById(id);
            deleted.put("deleted",Boolean.TRUE);
        }else{
            deleted.put("deleted",Boolean.FALSE);
        }
        return ResponseEntity.ok(deleted);
    }
}
