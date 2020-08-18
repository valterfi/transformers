package com.aequilibrium.transformers.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.aequilibrium.transformers.enums.TransformerType;

@Entity
public class Transformer {
	
	private static final int MIN_VALUE = 1;

	private static final int MAX_VALUE = 10;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull(message = "name must not be null")
	private String name;
	
	@Enumerated(EnumType.STRING)
	private TransformerType transformerType;
	
	@NotNull(message = "strength must not be null")
	@Range(min=MIN_VALUE, max=MAX_VALUE, message = "strength must be between " + MIN_VALUE +" and " + MAX_VALUE)
	private Integer strength;
	
	@NotNull(message = "intelligence must not be null")
	@Range(min=MIN_VALUE, max=MAX_VALUE, message = "intelligence must be between " + MIN_VALUE +" and " + MAX_VALUE)
	private Integer intelligence;
	
	@NotNull(message = "speed must not be null")
	@Range(min=MIN_VALUE, max=MAX_VALUE, message = "speed must be between " + MIN_VALUE +" and " + MAX_VALUE)
	private Integer speed;
	
	@NotNull(message = "endurance must not be null")
	@Range(min=MIN_VALUE, max=MAX_VALUE, message = "endurance must be between " + MIN_VALUE +" and " + MAX_VALUE)
	private Integer endurance;
	
	@NotNull(message = "rank must not be null")
	@Range(min=MIN_VALUE, max=MAX_VALUE, message = "rank must be between " + MIN_VALUE +" and " + MAX_VALUE)
	private Integer rank;
	
	@NotNull(message = "courage must not be null")
	@Range(min=MIN_VALUE, max=MAX_VALUE, message = "courage must be between " + MIN_VALUE +" and " + MAX_VALUE)
	private Integer courage;
	
	@NotNull(message = "firepower must not be null")
	@Range(min=MIN_VALUE, max=MAX_VALUE, message = "firepower must be between " + MIN_VALUE +" and " + MAX_VALUE)
	private Integer firepower;
	
	@NotNull(message = "skill must not be null") 
	@Range(min=MIN_VALUE, max=MAX_VALUE, message = "skill must be between " + MIN_VALUE +" and " + MAX_VALUE)
	private Integer skill;
	
	public Transformer() {
		
	}
	
	public int overallRating() {
		return this.strength + this.intelligence + this.speed + this.endurance + this.firepower;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

}
