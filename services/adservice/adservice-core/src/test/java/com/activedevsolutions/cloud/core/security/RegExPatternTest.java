package com.activedevsolutions.cloud.core.security;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegExPatternTest {

	@Test
	public void testDoesPatternMatchPlain() {
		//"^[a-zA-Z0-9_-]*$"
		
		String pattern = "^/v1.0/acl/groups$";
		String expected = "/v1.0/acl/groups";
		
		boolean result = RegExPattern.INSTANCE.doesPatternMatch(pattern, expected);
		assertTrue(result);
		
		expected = "/v1.0/acl/groups1";
		result = RegExPattern.INSTANCE.doesPatternMatch(pattern, expected);
		assertTrue(!result);
	}

	@Test
	public void testDoesPatternMatchRegex() {
		//"^[a-zA-Z0-9_-]*$"
		
		String pattern = "/v1.0/acl/groups/([a-zA-Z0-9_-]*)/roles";
		String expected = "/v1.0/acl/groups/123/roles";
		
		boolean result = RegExPattern.INSTANCE.doesPatternMatch(pattern, expected);
		assertTrue(result);
		
		expected = "/v1.0/acl/groups/roles";
		result = RegExPattern.INSTANCE.doesPatternMatch(pattern, expected);
		assertTrue(!result);
	}

}
