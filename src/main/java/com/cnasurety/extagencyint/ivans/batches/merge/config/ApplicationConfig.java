package com.cnasurety.extagencyint.ivans.batches.merge.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("gcp.cna.sur.extagcy.ivans.bond.notify")
@PropertySources(value = {@PropertySource(value = "classpath:application.properties")})
public class ApplicationConfig { 
	String topicname;
	
	String subscriptionname;

	public String getTopicname() {
		return topicname;
	}

	public void setTopicname(String topicname) {
		this.topicname = topicname;
	}

	public String getSubscriptionname() {
		return subscriptionname;
	}

	public void setSubscriptionname(String subscriptionname) {
		this.subscriptionname = subscriptionname;
	}

	
	

}
