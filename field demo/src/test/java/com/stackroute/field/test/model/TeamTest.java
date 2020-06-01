package com.stackroute.field.test.model;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import com.stackroute.field.model.Team;

public class TeamTest {

	private Team team;

	@Before
	public void setUp() throws Exception {
		team = new Team();
		team.setTeamId(1);
		team.setTeamName("Testing");
		team.setTeamDescription("All about testing application");
		team.setTeamCreatedBy("Jh0n123");
		team.setTeamCreationDate(new Date());
		team.setPlayers(null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void Beantest() {
		new BeanTester().testBean(Team.class);

	}

}
