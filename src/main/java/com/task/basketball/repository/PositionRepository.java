package com.task.basketball.repository;

import com.task.basketball.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position,Integer> {
    Position getPositionByShortName(String shortName);
}
