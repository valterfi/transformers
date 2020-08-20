package com.aequilibrium.transformers.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author valterfi
 *
 * Represents the unique outcome of the battle between two transformers
 */
@Entity
public class BattleResult {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer battleOrder;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autobot")
	private Transformer autobot;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "decepticon")
	private Transformer decepticon;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "winnerId")
	private Transformer winner;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loserId")
	private Transformer loser;
	
	private boolean hasWinner = false;
	
	private boolean destroyAll = false;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "battle")
	private Battle battle;
	
	private String ruleApplied = "";

	public static BattleResult builder() {
		return new BattleResult();
	}

	public BattleResult() {
		
	}
	
	public BattleResult withBattleOrder(Integer battleOrder) {
		this.setBattleOrder(battleOrder);
		return this;
	}
	
	public BattleResult withAutobot(Transformer autobot) {
		this.setAutobot(autobot);
		return this;
	}
	
	public BattleResult withDecepticon(Transformer decepticon) {
		this.setDecepticon(decepticon);
		return this;
	}

	public BattleResult withWinner(Transformer winner) {
		if (winner != null) {
			setHasWinner(true);
		}
		this.setWinner(winner);
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
	
	public BattleResult withLoser(Transformer loser) {
		this.setLoser(loser);
		return this;
	}
	
	public BattleResult withRuleApplied(String ruleApplied) {
		this.setRuleApplied(ruleApplied);
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

//	@Override
//	public String toString() {
//		return "Battle #" + this.battleOrder + " \n"
//				+ this.getAutobot().toString() + " \n"
//				+ this.getDecepticon().toString() + "\n"
//				+ "The winner is: " + (this.hasWinner ? this.winner.toString() : "[No winner]");
//	}

	public String getRuleApplied() {
		return ruleApplied;
	}

	public void setRuleApplied(String ruleApplied) {
		this.ruleApplied = ruleApplied;
	}

	public Transformer getAutobot() {
		return autobot;
	}

	public void setAutobot(Transformer autobot) {
		this.autobot = autobot;
	}

	public Transformer getDecepticon() {
		return decepticon;
	}

	public void setDecepticon(Transformer decepticon) {
		this.decepticon = decepticon;
	}

}
