package com.activedevsolutions.cloud.rbac.autoconfigure;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.activedevsolutions.cloud.core.security.RegExPattern;
import com.activedevsolutions.cloud.core.security.model.Permission;

/**
 * Provides functionality for security such as checking access to a
 * resource against a permission.
 *
 */
public class Security {
	private static final String DELIMITER = ".";

	private RestTemplate restTemplate;
	private String rbacUrl;
    
	
	public Security(RestTemplate restTemplate, String rbacUrl) {
		this.restTemplate = restTemplate;
		this.rbacUrl = rbacUrl;
	}

    /**
     * Determines if a user has access by looking up the permissions and
     * comparing it to the endpoint being accessed.
     * 
     * @param principal is the user
     * @param uri is the endpoint to be accessed
     * @param method is the http method
     * @return boolean indicating if the user has access
     */
	public boolean checkAccess(String principal, String uri, String method) {
		List<Permission> permissions = getPermissionForUser(principal);
		
		String requestedPermission = getResourcePermission(uri, method);
		for (Permission permission : permissions) {
			boolean matches = RegExPattern.INSTANCE.doesPatternMatch(permission.getEndpoint(), requestedPermission);
			if (matches) {
				return true;
			} // end if
		} // end for
		
		return false;
	}
	
	/**
	 * Standard way to get the permission format to compare.
	 * 
	 * @param uri
	 * @param method
	 * @return
	 */
	public final String getResourcePermission(String uri, String method) {
		return uri + DELIMITER + method;
	}
	
	/**
	 * Gets a list of permissions for the user from the acl service.
	 * 
	 * @param user is the user to get the permission list for
	 * @return List<Permission> list of permissions for the specific user
	 */
	private List<Permission> getPermissionForUser(String user) {
		//TODO RestTemplate is not Eureka aware. This should work: "http://acl-service/permissions/{userId}"
		
		String url = rbacUrl + user;
        ResponseEntity<List<Permission>> exchange =
                this.restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Permission>>() {
                        });
        
		return exchange.getBody();
	}
}
