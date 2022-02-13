package com.task.basketball.service;

import com.task.basketball.entity.Player;
import com.task.basketball.exceptions.PlayerNotFoundException;
import com.task.basketball.exceptions.TeamIsFullException;
import com.task.basketball.model.CreatePlayerDto;
import com.task.basketball.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PositionService positionService;

    public List<Player> findAll(){
        return playerRepository.findAll();
    }

    public Player addPlayer(CreatePlayerDto playerDto){
        var teamSize = playerRepository.count();
        if(teamSize < 12){
            var newPlayer = playerDtoToEntity(playerDto);
            return playerRepository.save(newPlayer);
        } else{
            throw  new TeamIsFullException("Takım Dolduğu İçin Bu Takıma Ekleme Yapılamaz");
        }
    }

    public Optional<Player> getById(Integer id){
        return playerRepository.findById(id);
    }

    public void deleteById(Integer id){
        var player = playerRepository.findById(id);
        if(player.isPresent()){
            playerRepository.delete(player.get());
        } else{
            throw new PlayerNotFoundException("Bu ID'e sahip Oyuncu Bulunamadı");
        }
    }


    private Player playerDtoToEntity(CreatePlayerDto playerDto){
        return Player.builder().firstName(playerDto.getFirstName())
                .lastName(playerDto.getLastName())
                .position(positionService.getByShortName(playerDto.getPositionShortName())).build();
    }



}
