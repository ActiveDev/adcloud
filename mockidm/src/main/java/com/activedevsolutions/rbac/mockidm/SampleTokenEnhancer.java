package com.activedevsolutions.rbac.mockidm;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * Demonstrates how a token can be enhanced.
 *
 */
public class SampleTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("company", "ACME");        
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        
        return accessToken;
    }
}
