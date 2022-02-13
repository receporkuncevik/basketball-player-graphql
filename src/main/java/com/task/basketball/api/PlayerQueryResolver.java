package com.task.basketball.api;

import com.task.basketball.entity.Player;
import com.task.basketball.service.PlayerService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PlayerQueryResolver implements GraphQLQueryResolver {

    private final PlayerService playerService;

    public List<Player> getPlayers(){
        return playerService.findAll();
    }

    public Optional<Player> getPlayer(Integer id){
        return playerService.getById(id);
    }

}
