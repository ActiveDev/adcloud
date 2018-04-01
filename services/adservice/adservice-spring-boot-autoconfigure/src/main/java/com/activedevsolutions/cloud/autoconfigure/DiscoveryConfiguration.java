package com.activedevsolutions.cloud.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEurekaClient
@ConditionalOnExpression("${spring.cloud.config.discovery.enabled:false}")
public class DiscoveryConfiguration {
	//TODO Is this even necessary
	//TODO Look at using Configuration properties for all of these auto configurations
}
