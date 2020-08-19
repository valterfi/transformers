package com.aequilibrium.transformers.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aequilibrium.transformers.enums.BattleStatus;
import com.aequilibrium.transformers.model.Battle;

@Repository
public interface BattleRepository extends CrudRepository<Battle, Long> {
	
	List<Battle> findByBattleStatus(BattleStatus battleStatus);
	
	List<Battle> findByBattleStatusNot(BattleStatus battleStatus);
	
//	@Modifying
//	@Query(value = "UPDATE BATTLE SET BATTLE_STATUS = 'ARCHIVED' WHERE BATTLE_STATUS <> 'ARCHIVED'", nativeQuery = true)
//	void archiveBattles();

}
