package com.stackroute.field.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "team")
public class Team {
	

	public Team() {

	}

	public Team(Integer teamId, String teamName, String teamDescription, Date teamCreationDate,
			String teamCreatedBy, List<Player> players) {

		this.teamId = teamId;
		this.teamName = teamName;
		this.teamDescription = teamDescription;
		this.teamCreationDate = teamCreationDate;
		this.teamCreatedBy = teamCreatedBy;
		this.players = players;
	}

	@Id
	@Column(name = "team_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer teamId;
	
	@Column(name = "team_name")
	private String teamName;

	@Column(name = "team_created_by")
	private String teamCreatedBy;

	@Column(name = "team_creation_date")
	private Date teamCreationDate;

	@Column(name = "team_description")
	private String teamDescription;

	@JsonIgnore
	@OneToMany(mappedBy = "team")
	List<Player> players = new ArrayList<>();

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamCreatedBy() {
		return teamCreatedBy;
	}

	public void setTeamCreatedBy(String teamCreatedBy) {
		this.teamCreatedBy = teamCreatedBy;
	}

	public Date getTeamCreationDate() {
		return teamCreationDate;
	}

	public void setTeamCreationDate(Date teamCreationDate) {
		this.teamCreationDate = teamCreationDate;
	}

	public String getTeamDescription() {
		return teamDescription;
	}

	public void setTeamDescription(String teamDescription) {
		this.teamDescription = teamDescription;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	
	

}