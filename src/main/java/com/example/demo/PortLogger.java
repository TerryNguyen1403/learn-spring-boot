package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PortLogger {

	private static final Logger log = LoggerFactory.getLogger(PortLogger.class);

	// Lấy giá trị server.port; nếu không có thì mặc định 8080
	@Value("${server.port:8080}")
	private int port;

	@EventListener(ApplicationReadyEvent.class)
	public void logPortWhenReady() {
		log.info("Ứng dụng đang lắng nghe tại cổng: {}", port);
	}
}
