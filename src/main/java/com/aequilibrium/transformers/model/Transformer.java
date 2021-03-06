package com.aequilibrium.transformers.model;

import static com.aequilibrium.transformers.util.Constants.MAX_SPEC_VALUE;
import static com.aequilibrium.transformers.util.Constants.MIN_SPEC_VALUE;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.aequilibrium.transformers.enums.TransformerType;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author valterfi
 *
 */
@Entity
public class Transformer implements Comparable<Transformer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "name must not be null")
	private String name;
	
	@Enumerated(EnumType.STRING)
	private TransformerType transformerType;
	
	@NotNull(message = "strength must not be null")
	@Range(min=MIN_SPEC_VALUE, max=MAX_SPEC_VALUE, message = "strength must be between " + MIN_SPEC_VALUE +" and " + MAX_SPEC_VALUE)
	private Integer strength;
	
	@NotNull(message = "intelligence must not be null")
	@Range(min=MIN_SPEC_VALUE, max=MAX_SPEC_VALUE, message = "intelligence must be between " + MIN_SPEC_VALUE +" and " + MAX_SPEC_VALUE)
	private Integer intelligence;
	
	@NotNull(message = "speed must not be null")
	@Range(min=MIN_SPEC_VALUE, max=MAX_SPEC_VALUE, message = "speed must be between " + MIN_SPEC_VALUE +" and " + MAX_SPEC_VALUE)
	private Integer speed;
	
	@NotNull(message = "endurance must not be null")
	@Range(min=MIN_SPEC_VALUE, max=MAX_SPEC_VALUE, message = "endurance must be between " + MIN_SPEC_VALUE +" and " + MAX_SPEC_VALUE)
	private Integer endurance;
	
	@NotNull(message = "rank must not be null")
	@Range(min=MIN_SPEC_VALUE, max=MAX_SPEC_VALUE, message = "rank must be between " + MIN_SPEC_VALUE +" and " + MAX_SPEC_VALUE)
	private Integer rank;
	
	@NotNull(message = "courage must not be null")
	@Range(min=MIN_SPEC_VALUE, max=MAX_SPEC_VALUE, message = "courage must be between " + MIN_SPEC_VALUE +" and " + MAX_SPEC_VALUE)
	private Integer courage;
	
	@NotNull(message = "firepower must not be null")
	@Range(min=MIN_SPEC_VALUE, max=MAX_SPEC_VALUE, message = "firepower must be between " + MIN_SPEC_VALUE +" and " + MAX_SPEC_VALUE)
	private Integer firepower;
	
	@NotNull(message = "skill must not be null") 
	@Range(min=MIN_SPEC_VALUE, max=MAX_SPEC_VALUE, message = "skill must be between " + MIN_SPEC_VALUE +" and " + MAX_SPEC_VALUE)
	private Integer skill;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "autobot", cascade = CascadeType.REMOVE)
	private List<BattleResult> battleResultsAutobot;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "decepticon", cascade = CascadeType.REMOVE)
	private List<BattleResult> battleResultsDecepticons;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "winner", cascade = CascadeType.REMOVE)
	private List<BattleResult> battleResultsWinner;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "loser", cascade = CascadeType.REMOVE)
	private List<BattleResult> battleResultsLoser;
 	
	public Transformer() {
		
	}
	
	public static Transformer builder() {
		return new Transformer();
	}
	
	public Transformer withTransformerType(TransformerType transformerType) {
		this.setTransformerType(transformerType);  
		return this;
	}
	
	public Transformer withName(String name) {
		this.setName(name);
		return this;
	}
	
	public Transformer withStrength(int strength) {
		this.setStrength(strength);
		return this;
	}
	
	public Transformer withIntelligence(int intelligence) {
		this.setIntelligence(intelligence);
		return this;
	}
	
	public Transformer withSpeed(int speed) {
		this.setSpeed(speed);
		return this;
	}
	
	public Transformer withEndurance(int endurance) {
		this.setEndurance(endurance);
		return this;
	}
	
	public Transformer withRank(int rank) {
		this.setRank(rank);
		return this;
	}
	
	public Transformer withCourage(int courage) {
		this.setCourage(courage);
		return this;
	}
	
	public Transformer withFirepower(int firepower) {
		this.setFirepower(firepower);
		return this;
	}
	
	public Transformer withSkill(int skill) {
		this.setSkill(skill);
		return this;
	}
	
	public Transformer mapTo(Transformer transformer) {
		this.setName(transformer.getName());
		this.setTransformerType(transformer.getTransformerType());
		this.setStrength(transformer.getStrength());
		this.setIntelligence(transformer.getIntelligence());
		this.setSpeed(transformer.getSpeed());
		this.setEndurance(transformer.getEndurance());
		this.setRank(transformer.getRank());
		this.setCourage(transformer.getCourage());
		this.setFirepower(transformer.getFirepower());
		this.setSkill(transformer.getSkill());
		return this;
	}
	
	public int overallRating() {
		return this.strength + this.intelligence + this.speed + this.endurance + this.firepower;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public TransformerType getTransformerType() {
		return transformerType;
	}

	public void setTransformerType(TransformerType transformerType) {
		this.transformerType = transformerType;
	}

	public Integer getStrength() {
		return strength;
	}

	public void setStrength(Integer strength) {
		this.strength = strength;
	}

	public Integer getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(Integer intelligence) {
		this.intelligence = intelligence;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getEndurance() {
		return endurance;
	}

	public void setEndurance(Integer endurance) {
		this.endurance = endurance;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getCourage() {
		return courage;
	}

	public void setCourage(Integer courage) {
		this.courage = courage;
	}

	public Integer getFirepower() {
		return firepower;
	}

	public void setFirepower(Integer firepower) {
		this.firepower = firepower;
	}

	public Integer getSkill() {
		return skill;
	}

	public void setSkill(Integer skill) {
		this.skill = skill;
	}
	
	public List<BattleResult> getBattleResultsWinner() {
		return battleResultsWinner;
	}

	public void setBattleResultsWinner(List<BattleResult> battleResultsWinner) {
		this.battleResultsWinner = battleResultsWinner;
	}

	public List<BattleResult> getBattleResultsLoser() {
		return battleResultsLoser;
	}

	public void setBattleResultsLoser(List<BattleResult> battleResultsLoser) {
		this.battleResultsLoser = battleResultsLoser;
	}

	@Override
	public String toString() {
		return "Transformer [id=" + id + ", name=" + name + ", transformerType=" + transformerType + ", strength="
				+ strength + ", intelligence=" + intelligence + ", speed=" + speed + ", endurance=" + endurance
				+ ", rank=" + rank + ", courage=" + courage + ", firepower=" + firepower + ", skill=" + skill + "]";
	}

	@Override
	public int compareTo(Transformer transformer) {
		if (this.getRank() < transformer.getRank()) {
			return 1;
		} else if (this.getRank() > transformer.getRank()) {
			return -1;
		}
		return 0;
	}

	public List<BattleResult> getBattleResultsAutobot() {
		return battleResultsAutobot;
	}

	public void setBattleResultsAutobot(List<BattleResult> battleResultsAutobot) {
		this.battleResultsAutobot = battleResultsAutobot;
	}

	public List<BattleResult> getBattleResultsDecepticons() {
		return battleResultsDecepticons;
	}

	public void setBattleResultsDecepticons(List<BattleResult> battleResultsDecepticons) {
		this.battleResultsDecepticons = battleResultsDecepticons;
	}

}
