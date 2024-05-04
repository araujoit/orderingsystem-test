package br.com.bluesburguer.orderingsystem.test.interfaces.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesburguer.orderingsystem.test.infra.client.sqs.MessageEventPublisherImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/event/message")
@RequiredArgsConstructor
public class TestMessageRestResource {
    private final MessageEventPublisherImpl eventPublisher;
	
	@PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
	public void publicOrderPending(@RequestBody String message) {
		eventPublisher.publish(message).ifPresent(messageId -> 
			log.info("Message {} published with id {} in SQS queue", message, messageId));
    }
}
