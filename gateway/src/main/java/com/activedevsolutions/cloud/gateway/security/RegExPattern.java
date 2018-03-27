package com.activedevsolutions.cloud.gateway.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Caches regex patterns and performs matching.
 *
 */
public enum RegExPattern {
	INSTANCE;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegExPattern.class);
	
	private Map<String, Pattern> patternCache = new ConcurrentHashMap<>();

	/**
	 * Compiles a pattern only if it is not in the cache.
	 * 
	 * @param patternValue is the string to compile into a pattern
	 * @return Pattern holding the compiled pattern value
	 */
	public Pattern getRegExPattern(String patternValue) {
		return patternCache.computeIfAbsent(patternValue, 
				k -> Pattern.compile(patternValue));
	}
	
	/**
	 * Compares the pattern to the value.
	 * 
	 * @param pattern is the value with the regex
	 * @param expectedValue is the value to compare to
	 * @return boolean indicating if it matches
	 */
	public boolean doesPatternMatch(String pattern, String expectedValue) {
		LOGGER.debug("Attempting to match {} with {}", pattern, expectedValue);
		
		Pattern patternObj = getRegExPattern(pattern);
		Matcher matcher= patternObj.matcher(expectedValue);
		boolean result = matcher.find();
		
		LOGGER.debug("Match found. {}", result);
		
		return result;
	}
}
