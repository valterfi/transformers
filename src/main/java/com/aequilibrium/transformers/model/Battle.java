package com.aequilibrium.transformers.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.aequilibrium.transformers.enums.BattleStatus;

@Entity
public class Battle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private BattleStatus battleStatus = BattleStatus.CREATED;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "battle")
	private List<BattleResult> battleResults;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<BattleResult> getBattleResults() {
		return battleResults;
	}

	public void setBattleResult(List<BattleResult> battleResults) {
		this.battleResults = battleResults;
	}

	public BattleStatus getBattleStatus() {
		return battleStatus;
	}

	public void setBattleStatus(BattleStatus battleStatus) {
		this.battleStatus = battleStatus;
	}

}
