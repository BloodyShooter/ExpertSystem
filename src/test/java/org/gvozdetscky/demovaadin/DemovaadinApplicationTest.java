package org.gvozdetscky.demovaadin;

import org.gvozdetscky.demovaadin.lab1.server.Lab1;
import org.junit.Test;

public class DemovaadinApplicationTest {

	@Test
	public void testEvklid() {
		Lab1 lab1 = new Lab1();

		lab1.generateRecord();

		lab1.run(Lab1.EVCLID_MOD);

	}

	@Test
	public void testManc() {
		Lab1 lab1 = new Lab1();

		lab1.generateRecord();

		lab1.run(Lab1.MANCHTAN_MOD);
	}
}