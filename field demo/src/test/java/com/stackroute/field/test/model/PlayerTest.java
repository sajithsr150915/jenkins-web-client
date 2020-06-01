package com.stackroute.field.test.model;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import com.stackroute.field.model.Player;

public class PlayerTest {

	private Player player;

	@Before
	public void setUp() throws Exception {
		player = new Player();
		player.setPlayerId(1);
		player.setTeam(null);
		player.setCreatedAt(new Date());

	}

	@Test
	public void Beantest() {
		new BeanTester().testBean(Player.class);

	}

}
