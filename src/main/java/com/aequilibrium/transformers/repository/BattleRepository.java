package com.aequilibrium.transformers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aequilibrium.transformers.enums.BattleStatus;
import com.aequilibrium.transformers.model.Battle;

@Repository
public interface BattleRepository extends CrudRepository<Battle, Long> {
	
	List<Battle> findByBattleStatus(BattleStatus battleStatus);
	
	List<Battle> findByBattleStatusNot(BattleStatus battleStatus);
	
	@Query(value = "SELECT * FROM BATTLE WHERE BATTLE_STATUS = 'RUNNING' OR BATTLE_STATUS = 'FINISHED'", nativeQuery = true)
	List<Battle> findRunningOrFinishedBattles();

}
