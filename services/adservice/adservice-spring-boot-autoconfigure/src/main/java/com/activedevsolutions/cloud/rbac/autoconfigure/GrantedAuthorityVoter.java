package com.activedevsolutions.cloud.rbac.autoconfigure;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Matches granted authorities to endpoints and http methods.
 * 
 * Example: /service/resources.POST
 *
 */
public class GrantedAuthorityVoter implements AccessDecisionVoter<Object> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GrantedAuthorityVoter.class);
	
	// Delimiter separating uri from method
	public static final String DELIMITER = ".";

	/**
	 * Parses the Granted Authority to get the expected resource and method
	 * and compares it to the actual resource and method.
	 * 
	 * Resource is the portion of the url that defines the endpoint.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		int result = ACCESS_DENIED;

		String requestAuthority = getRequestedAuthority();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		LOGGER.debug("Authorities: {}", authorities);
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equals(requestAuthority)) {
				result = ACCESS_GRANTED;
				LOGGER.debug("Granted Authority found.");
				break;
			} // end if
		} // end for

		return result;
	}

	/**
	 * Generates an authority string based on the uri and method of the request.
	 * 
	 * @return String containing a delimited string of resource.method
	 */
	private String getRequestedAuthority() {
		ServletRequestAttributes servletRequestAttr = 
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String resource = servletRequestAttr.getRequest().getRequestURI();
		String method = servletRequestAttr.getRequest().getMethod();
		
		String requestedAuthority = resource + DELIMITER + method;
		LOGGER.debug("Requested: {}", requestedAuthority);
		
		return requestedAuthority;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
