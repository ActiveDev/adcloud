package com.activedevsolutions.rbac.mockidm;

import org.junit.Test;
import org.springframework.security.crypto.codec.Base64;

public class JWTParseTest {
	private String header = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
	private String body = "eyJleHAiOjE1MjE1OTI3MzgsInVzZXJfbmFtZSI6ImJlbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfTUFOQUdFUlMiLCJST0xFX0RFVkVMT1BFUlMiXSwianRpIjoiNzMzMmYwYjEtYjUzMC00NDQyLTk0YjctMzU4NDRmYmY5YzRkIiwiY2xpZW50X2lkIjoibW9jayIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ";
	
	@Test
	public void test() {
		byte[] headerBytes = Base64.decode(header.getBytes());
		System.out.println("Header:\n" + new String(headerBytes));
		
		byte[] bodyBytes = Base64.decode(body.getBytes());
		System.out.println("Content:\n" + new String(bodyBytes));
	}
}
