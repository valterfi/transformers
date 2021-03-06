package com.aequilibrium.transformers.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aequilibrium.transformers.model.BattleResult;

/**
 * @author valterfi
 *
 */
@Repository
public interface BattleResultRepository extends CrudRepository<BattleResult, Long> {

}
