package com.task.basketball.service;

import com.task.basketball.entity.Player;
import com.task.basketball.exceptions.PlayerNotFoundException;
import com.task.basketball.exceptions.TeamIsFullException;
import com.task.basketball.model.CreatePlayerDto;
import com.task.basketball.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @InjectMocks
    private PlayerService playerService;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private PositionService positionService;


    @Test
    void canGetAllPlayers() {
        playerService.findAll();
        verify(playerRepository).findAll();
    }

    @Test
    void canAddPlayer() {
        CreatePlayerDto createPlayerDto = CreatePlayerDto.builder().firstName("Test-Name")
                .lastName("Test-LastName")
                .positionShortName("PG").build();

        playerService.addPlayer(createPlayerDto);

        ArgumentCaptor<Player> playerArgumentCaptor = ArgumentCaptor.forClass(Player.class);
        verify(playerRepository).save(playerArgumentCaptor.capture());

        Player capturedPlayer = playerArgumentCaptor.getValue();
        assertThat(capturedPlayer.getFirstName()).isEqualTo(createPlayerDto.getFirstName());

    }

    @Test
    void whenIsTeamFullShouldThrowException(){
        CreatePlayerDto createPlayerDto = CreatePlayerDto.builder().firstName("Test-Name")
                .lastName("Test-LastName")
                .positionShortName("PG").build();


        given((playerRepository.count())).willReturn(Long.valueOf(12));


        assertThatThrownBy(() ->playerService.addPlayer(createPlayerDto))
                .isInstanceOf(TeamIsFullException.class)
                .hasMessageContaining("Takım Dolduğu İçin Bu Takıma Ekleme Yapılamaz");
    }




    @Test
    void getById() {
        playerService.getById(24);
        verify(playerRepository).findById(24);
    }

    @Test
    void deleteById() {
        Player player = Player.builder().id(1).build();
        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.of(player)).thenReturn(null);
        playerService.deleteById(1);

        Mockito.verify(playerRepository,times(1)).delete(player);
    }

    @Test
    void whenCantFindPlayerShouldThrowException(){
        assertThatThrownBy(() ->playerService.deleteById(1))
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessageContaining("Bu ID'e sahip Oyuncu Bulunamadı");
    }

}