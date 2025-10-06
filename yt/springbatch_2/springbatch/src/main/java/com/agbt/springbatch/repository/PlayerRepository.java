package com.agbt.springbatch.repository;

import com.agbt.springbatch.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
