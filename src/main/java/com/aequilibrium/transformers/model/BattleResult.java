package com.aequilibrium.transformers.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class BattleResult {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer battleOrder;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winnerId")
	private Transformer winner;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loserId")
	private Transformer loser;
	
	private boolean hasWinner = false;
	
	private boolean destroyAll = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battleId")
	private Battle battle;

	public static BattleResult builder() {
		return new BattleResult();
	}

	public BattleResult() {
		
	}
	
	public BattleResult withBattleOrder(Integer battleOrder) {
		this.setBattleOrder(battleOrder);
		return this;
	}

	public BattleResult withWinner(Transformer winner) {
		if (winner != null) {
			setHasWinner(true);
		}
		this.setWinner(winner);
		return this;
	}
	
	public BattleResult withLoser(Transformer loser) {
		this.setLoser(loser);
		return this;
	}
	
	public BattleResult shouldDestroyAll() {
		this.setDestroyAll(true);
		return this;
	}
	
	public BattleResult withBattle(Battle battle) {
		this.setBattle(battle);
		return this;
	}

	public Transformer getWinner() {
		return winner;
	}

	public void setWinner(Transformer winner) {
		if (winner != null) {
			setHasWinner(true);
		}
		this.winner = winner;
	}

	public Transformer getLoser() {
		return loser;
	}

	public void setLoser(Transformer loser) {
		this.loser = loser;
	}

	public boolean isDestroyAll() {
		return destroyAll;
	}

	public void setDestroyAll(boolean destroyAll) {
		this.destroyAll = destroyAll;
	}

	public boolean hasWinner() {
		return hasWinner;
	}

	public void setHasWinner(boolean hasWinner) {
		this.hasWinner = hasWinner;
	}

	public Battle getBattle() {
		return battle;
	}

	public void setBattle(Battle battle) {
		this.battle = battle;
	}

	public Integer getBattleOrder() {
		return battleOrder;
	}

	public void setBattleOrder(Integer battleOrder) {
		this.battleOrder = battleOrder;
	}

}
