package com.boufoussmed.springboot.repository;

import com.boufoussmed.springboot.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
