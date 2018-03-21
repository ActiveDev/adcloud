package com.activedevsolutions.cloud.gateway.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.activedevsolutions.rbac.autoconfigure.RBACAuthMethod;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Authorization against an ACL. 
 *
 */
public final class RBACAuthFilter extends ZuulFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(RBACAuthFilter.class);
	
	@Value("${rbac.authorization.method}")
    private String rbacMethod;
		
	/**
	 * {@inheritDoc}.
	 * 
	 * Compares the url and http method being accessed to the user's ACL
	 * to determine if the user has access to the resource.
	 */
	@Override
	public Object run() {
		LOGGER.debug("[START] run");
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		
		// Ensure a principal was passed in
		if (request.getUserPrincipal() == null) {
			setFailedRequest("Security context is missing. Unauthorized.", 401);
			return null;
		}

		String principal = request.getUserPrincipal().getName();
	    String path = request.getServletPath();
	    String method = request.getMethod();

	    //TODO Implement
	    boolean authorized = true;
	    
	    if (!authorized) {
	    	setFailedRequest("User does not have permission to the resource.", 403);
	    }

		LOGGER.debug("[END] run");
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldFilter() {
		return (rbacMethod !=null && rbacMethod.equals(RBACAuthMethod.FILTER.getName()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String filterType() {
		return "pre";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int filterOrder() {
		return 1;
	}

	/**
	 * Reports an error message through a zuul response.
	 * 
	 * @param body is the contents of the response
	 * @param code is the HTTP return code
	 */
	private void setFailedRequest(String body, int code) {
		RequestContext requestContext = RequestContext.getCurrentContext();
	    requestContext.setResponseStatusCode(code);
	    
	    if (requestContext.getResponseBody() == null) {
	        requestContext.setResponseBody(body);
	        requestContext.setSendZuulResponse(false);
	    } // end if
	}
}
