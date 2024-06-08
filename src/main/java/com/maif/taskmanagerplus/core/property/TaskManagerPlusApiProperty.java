package com.maif.taskmanagerplus.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("taskmanagerplus")
public class TaskManagerPlusApiProperty {

	private String allowedOrigin = "http://localhost:8080";

	private final Security security = new Security();

	public Security getSecurity() {
		return security;
	}

	public String getAllowedOrigin() {
		return allowedOrigin;
	}

	public void setAllowedOrigin(String allowedOrigin) {
		this.allowedOrigin = allowedOrigin;
	}

	public static class Security {

		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}

	}

}

