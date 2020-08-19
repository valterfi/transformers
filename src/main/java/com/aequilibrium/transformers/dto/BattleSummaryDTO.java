package com.aequilibrium.transformers.dto;

import java.util.List;

public class BattleSummaryDTO {
	
	private Long battlesNumber;
	
	private String winningTeam;
	
	private String message;
	
	private List<String> losingTeamSurvivors;
	
	public static BattleSummaryDTO builder() {
		return new BattleSummaryDTO();
	}
	
	public BattleSummaryDTO withBattlesNumber(Long battlesNumber) {
		this.setBattlesNumber(battlesNumber);
		return this;
	}
	
	public BattleSummaryDTO withWinningTeam(String winningTeam) {
		this.setWinningTeam(winningTeam);
		return this;
	}
	
	public BattleSummaryDTO withMessage(String message) {
		this.setMessage(message);
		return this;
	}
	
	public BattleSummaryDTO withLosingTeamSurvivors(List<String> losingTeamSurvivors) {
		this.setLosingTeamSurvivors(losingTeamSurvivors);
		return this;
	}

	public Long getBattlesNumber() {
		return battlesNumber;
	}

	public void setBattlesNumber(Long battlesNumber) {
		this.battlesNumber = battlesNumber;
	}

	public String getWinningTeam() {
		return winningTeam;
	}

	public void setWinningTeam(String winningTeam) {
		this.winningTeam = winningTeam;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getLosingTeamSurvivors() {
		return losingTeamSurvivors;
	}

	public void setLosingTeamSurvivors(List<String> losingTeamSurvivors) {
		this.losingTeamSurvivors = losingTeamSurvivors;
	}

}
