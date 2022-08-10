package dislinkt.accountservice.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.access.prepost.PreAuthorize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/test")
public class TestController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Value("${server.port}")
	private String port;

	@PreAuthorize("hasRole('ROLE_AGENT')")
	@GetMapping("/agents-endpoint")
	public ResponseEntity<String> agent() {
		logger.info("Agent's endpoint on account...");
		return ResponseEntity.ok("AGENT'S ENDPOINT ON ACCOUNT");
	}

	@GetMapping()
	public ResponseEntity<String> test() {
		logger.info("test...");
		return ResponseEntity.ok("test");
	}
}
