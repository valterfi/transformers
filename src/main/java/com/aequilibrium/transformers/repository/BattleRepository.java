package com.aequilibrium.transformers.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aequilibrium.transformers.enums.BattleStatus;
import com.aequilibrium.transformers.model.Battle;

/**
 * @author valterfi
 *
 */
@Repository
public interface BattleRepository extends CrudRepository<Battle, Long> {
	
	List<Battle> findByBattleStatus(BattleStatus battleStatus);
	
	List<Battle> findByBattleStatusNot(BattleStatus battleStatus);
	
	List<Battle> findByBattleStatusIn(List<BattleStatus> battleStatus);

}
