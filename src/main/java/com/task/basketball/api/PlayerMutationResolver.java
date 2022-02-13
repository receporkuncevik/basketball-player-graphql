package com.task.basketball.api;

import com.task.basketball.entity.Player;
import com.task.basketball.model.CreatePlayerDto;
import com.task.basketball.service.PlayerService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;


@RequiredArgsConstructor
@Component
@Validated
public class PlayerMutationResolver implements GraphQLMutationResolver {
    private final PlayerService playerService;

    public Player createPlayer(@Valid CreatePlayerDto playerDto){
        return playerService.addPlayer(playerDto);
    }

    public Integer deletePlayer(Integer id){
        playerService.deleteById(id);
        return 1;
    }

}
