package com.sparta.ticketauction.global.util;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class UrlUtil {

	public String getCurrentServerUrl(HttpServletRequest request) {
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();

		String protocol = request.getScheme();
		StringBuilder sb = new StringBuilder();
		sb.append(protocol);
		sb.append("://");
		sb.append(serverName);
		sb.append(serverPort);
		return sb.toString();
	}
}
