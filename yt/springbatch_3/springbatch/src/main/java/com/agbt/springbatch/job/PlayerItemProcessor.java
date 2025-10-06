package com.agbt.springbatch.job;

import com.agbt.springbatch.model.Player;
import org.springframework.batch.item.ItemProcessor;


public class PlayerItemProcessor implements ItemProcessor<Player, Player> {

    @Override
    public Player process(Player player) throws Exception {
        if (player.getPosition().equalsIgnoreCase("forward")) {
            return player;   //ritorna solo i Player con forward in position
        } else {
            return null;     // gli altri vengono scartati
        }
    }
}
