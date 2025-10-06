package com.agbt.springbatch.job;

import com.agbt.springbatch.model.Player;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PlayerFieldSetMapper implements FieldSetMapper<Player> {


    @Override
    public Player mapFieldSet(FieldSet fieldSet) throws BindException {
        Player player = new Player();
        player.setId(fieldSet.readInt(0));
        player.setFirstName(fieldSet.readString(1));
        player.setLastName(fieldSet.readString(2));
        player.setPosition(fieldSet.readString(4));
        player.setTeam(fieldSet.readString(5));

        return player;
    }
}
