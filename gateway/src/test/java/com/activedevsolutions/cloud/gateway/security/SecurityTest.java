package com.activedevsolutions.cloud.gateway.security;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityTest {
	@Autowired
	private Security security;
	
	@Test
	public void testCheckAccess() {
		boolean result = security.checkAccess("root@email.com", "uri", "GET");
		assertTrue(result);
	}

}
