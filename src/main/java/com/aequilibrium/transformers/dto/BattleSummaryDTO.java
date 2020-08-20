package com.aequilibrium.transformers.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author valterfi
 *
 * DTO responsible to aggregate the main information of the status of all the battles of the last war.
 */
public class BattleSummaryDTO {
	
	private Integer battlesNumber = 0;
	
	private String battleStatus = "";
	
	private String winningTeam = "";
	
	private String message = "";
	
	private String losingTeamSurvivors = "";
	
	private List<BattleDetailDTO> details = new ArrayList<BattleDetailDTO>();
	
	public static BattleSummaryDTO builder() {
		return new BattleSummaryDTO();
	}
	
	public BattleSummaryDTO withBattlesNumber(Integer battlesNumber) {
		this.setBattlesNumber(battlesNumber);
		return this;
	}
	
	public BattleSummaryDTO withBattleStatus(String battleStatus) {
		this.setBattleStatus(battleStatus);
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
	
	public BattleSummaryDTO withLosingTeamSurvivors(String losingTeamSurvivors) {
		this.setLosingTeamSurvivors(losingTeamSurvivors);
		return this;
	}
	
	public BattleSummaryDTO withDetails(List<BattleDetailDTO> details) {
		this.setDetails(details);
		return this;
	}

	public Integer getBattlesNumber() {
		return battlesNumber;
	}

	public void setBattlesNumber(Integer battlesNumber) {
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

	public String getLosingTeamSurvivors() {
		return losingTeamSurvivors;
	}

	public void setLosingTeamSurvivors(String losingTeamSurvivors) {
		this.losingTeamSurvivors = losingTeamSurvivors;
	}

	public String getBattleStatus() {
		return battleStatus;
	}

	public void setBattleStatus(String battleStatus) {
		this.battleStatus = battleStatus;
	}

	public List<BattleDetailDTO> getDetails() {
		return details;
	}

	public void setDetails(List<BattleDetailDTO> details) {
		this.details = details;
	}

}
