package com.aequilibrium.transformers.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.aequilibrium.transformers.enums.BattleStatus;
import com.aequilibrium.transformers.enums.TransformerType;

@Entity
public class Battle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private BattleStatus battleStatus = BattleStatus.CREATED;
	
	@Enumerated(EnumType.STRING)
	private TransformerType winningTransformerType;
	
	private String winningAutobots = "";
	
	private Integer winningAutobotsSize = 0;
	
	private String survivorsAutobots = "";
	
	private String winningDecepticons = "";
	
	private Integer winningDecepticonsSize = 0;
	
	private String survivorsDecepticons = "";
	
	private Boolean allDestroyed = false;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "battle")
	private Set<BattleResult> battleResults = new HashSet<BattleResult>(0);
	
	@ManyToMany
	private Set<Transformer> transformers = new HashSet<Transformer>(0);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BattleStatus getBattleStatus() {
		return battleStatus;
	}

	public void setBattleStatus(BattleStatus battleStatus) {
		this.battleStatus = battleStatus;
	}

	public Set<BattleResult> getBattleResults() {
		return battleResults;
	}

	public void setBattleResults(Set<BattleResult> battleResults) {
		this.battleResults = battleResults;
	}

	public Set<Transformer> getTransformers() {
		return transformers;
	}

	public void setTransformers(Set<Transformer> transformers) {
		this.transformers = transformers;
	}

	public String getWinningAutobots() {
		return winningAutobots;
	}

	public void setWinningAutobots(String winningAutobots) {
		this.winningAutobots = winningAutobots;
	}

	public String getWinningDecepticons() {
		return winningDecepticons;
	}

	public void setWinningDecepticons(String winningDecepticons) {
		this.winningDecepticons = winningDecepticons;
	}

	public TransformerType getWinningTransformerType() {
		return winningTransformerType;
	}

	public void setWinningTransformerType(TransformerType winningTransformerType) {
		this.winningTransformerType = winningTransformerType;
	}

	public Boolean getAllDestroyed() {
		return allDestroyed;
	}

	public void setAllDestroyed(Boolean allDestroyed) {
		this.allDestroyed = allDestroyed;
	}

	public String getSurvivorsAutobots() {
		return survivorsAutobots;
	}

	public void setSurvivorsAutobots(String survivorsAutobots) {
		this.survivorsAutobots = survivorsAutobots;
	}

	public String getSurvivorsDecepticons() {
		return survivorsDecepticons;
	}

	public void setSurvivorsDecepticons(String survivorsDecepticons) {
		this.survivorsDecepticons = survivorsDecepticons;
	}

	public Integer getWinningAutobotsSize() {
		return winningAutobotsSize;
	}

	public void setWinningAutobotsSize(Integer winningAutobotsSize) {
		this.winningAutobotsSize = winningAutobotsSize;
	}

	public Integer getWinningDecepticonsSize() {
		return winningDecepticonsSize;
	}

	public void setWinningDecepticonsSize(Integer winningDecepticonsSize) {
		this.winningDecepticonsSize = winningDecepticonsSize;
	}

}
