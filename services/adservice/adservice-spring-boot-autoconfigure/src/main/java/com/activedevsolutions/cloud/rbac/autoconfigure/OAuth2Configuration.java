package com.activedevsolutions.cloud.rbac.autoconfigure;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;

/**
 * Configures OAuth2 authorization if security is enabled. This will enforce the
 * use of a JWT with a valid key.
 * 
 * As an option, it will also perform RBAC authorization based on granted authorities
 * contained within the JWT.
 *
 */
@Configuration
@EnableResourceServer
@EnableWebSecurity
@ConditionalOnExpression("${adservice.security.enabled:false}")
@EnableConfigurationProperties(OAuth2ConfigProperties.class)
public class OAuth2Configuration extends ResourceServerConfigurerAdapter {
    @Autowired
    private Environment environment;

    @Autowired
    private OAuth2ConfigProperties props;
    
	@Value("${adservice.rbac.authorization.url:'http://localhost:9001/acl/v1.0/security/users/'}")
	private String rbacUrl;

    @Bean
    public RestTemplate restTemplate() {
    	return new RestTemplate();
    }
    
    @Bean
    public Security security() {
    	return new Security(restTemplate(), rbacUrl);
    }
    
    /**
     * Sets up a decision manager that will use a GrantedAuthorityVoter
     * to determine authorization. This should only be enabled if the
     * JWT coming from the authentication server will contain granted authorities.
     * 
     * @return AccessDecisionManager
     */
    @Bean
    @ConditionalOnExpression("'${adservice.rbac.authorization.method:none}'=='granted-auth'")
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = 
        		Arrays.asList(new AuthenticatedVoter(), new GrantedAuthorityVoter());
        
        return new UnanimousBased(decisionVoters);
    }
    
    /**
     * Configures security to enforce OAuth for every endpoint.
     * 
     * {@inheritDoc}
     */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		String rbacMethod = environment.getProperty("adservice.rbac.authorization.method");
		if (rbacMethod != null && rbacMethod.equals(RBACAuthMethod.GRANTED_AUTH.getName())) {
			http
				.authorizeRequests()
				.anyRequest().fullyAuthenticated()
				.accessDecisionManager(accessDecisionManager())
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				;
		}
		else {
			http
				.authorizeRequests()
				.anyRequest().fullyAuthenticated()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				;
		} // end if
	}
    	
    /**
     * Sets up the jwt with the key. I believe this is already done
     * through the oauth2 autoconfigure. More research needs to be done.
     * 
     * @return JwtAccessTokenConverter
     */
    @Bean
    @ConditionalOnMissingBean
    public JwtAccessTokenConverter accessTokenConverter() {
    	//TODO Research the oauth2 autoconfigure
    	String key = environment.getProperty("security.oauth2.resource.jwt.key-value");
    	JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(key);
        
        //TODO Use proper security for JWT
        //converter.setVerifierKey(key);

        return converter;
    }

    /**
     * Sets up the JWT store
     * 
     * @return TokenStore
     */
    @Bean
    @ConditionalOnMissingBean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
    
    /**
     * Token services.
     * 
     * @return DefaultTokenServices
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
    
    /**
     * {@inheritDoc}
     */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(tokenServices());
	}
}