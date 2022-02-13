package com.task.basketball.service;

import com.task.basketball.entity.Player;
import com.task.basketball.entity.Position;
import com.task.basketball.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public Position getByShortName(String shortName){
        return positionRepository.getPositionByShortName(shortName);
    }

}
