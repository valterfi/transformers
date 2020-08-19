package com.aequilibrium.transformers.dto;

import com.aequilibrium.transformers.model.Transformer;

/**
 * @author valterfi
 *
 */
public class BattleDetailDTO {
	
	private Integer battleOrder;
	
	private Integer autobotOverallRating;
	
	private Transformer autobot;
	
	private Integer decepticonOverallRating;
	
	private Transformer decepticon;
	
	private String rule;
	
	private boolean hasWinner;
	
	private Transformer winner;
	
	public static BattleDetailDTO builder() {
		return new BattleDetailDTO();
	}
	
	public BattleDetailDTO withBattleOrder(Integer battleOrder) {
		this.setBattleOrder(battleOrder);
		return this;
	}
	
	public BattleDetailDTO withAutobotOverallRating(Integer autobotOverallRating) {
		this.setAutobotOverallRating(autobotOverallRating);
		return this;
	}
	
	public BattleDetailDTO withDecepticonOverallRating(Integer decepticonOverallRating) {
		this.setDecepticonOverallRating(decepticonOverallRating);
		return this;
	}
	
	public BattleDetailDTO withAutobot(Transformer autobot) {
		this.setAutobot(autobot);
		return this;
	}
	
	public BattleDetailDTO withDecepticon(Transformer decepticon) {
		this.setDecepticon(decepticon);
		return this;
	}
	
	public BattleDetailDTO withRule(String rule) {
		this.setRule(rule);
		return this;
	}
	
	public BattleDetailDTO withHasWinner(boolean hasWinner) {
		this.setHasWinner(hasWinner);
		return this;
	}
	
	public BattleDetailDTO withWinner(Transformer winner) {
		this.setWinner(winner);
		return this;
	}

	public Integer getBattleOrder() {
		return battleOrder;
	}

	public void setBattleOrder(Integer battleOrder) {
		this.battleOrder = battleOrder;
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

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public boolean isHasWinner() {
		return hasWinner;
	}

	public void setHasWinner(boolean hasWinner) {
		this.hasWinner = hasWinner;
	}

	public Transformer getWinner() {
		return winner;
	}

	public void setWinner(Transformer winner) {
		this.winner = winner;
	}

	public Integer getAutobotOverallRating() {
		return autobotOverallRating;
	}

	public void setAutobotOverallRating(Integer autobotOverallRating) {
		this.autobotOverallRating = autobotOverallRating;
	}

	public Integer getDecepticonOverallRating() {
		return decepticonOverallRating;
	}

	public void setDecepticonOverallRating(Integer decepticonOverallRating) {
		this.decepticonOverallRating = decepticonOverallRating;
	}

}
