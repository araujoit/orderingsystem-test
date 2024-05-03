package br.com.bluesburguer.orderingsystem.test.interfaces.api;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/event/message")
@RequiredArgsConstructor
public class TestMessageRestResource {
//    private final String queueUrl = "http://localhost:4566/000000000000/SQS_DEMO_QUEUE.fifo";
    private final String queueUrl = "https://sqs.us-east-1.amazonaws.com/637423186279/SQS_DEMO_QUEUE.fifo";

    private final AmazonSQS amazonSQS;
	
	@PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
	public void publicOrderPending(@RequestBody String message) {
        SendMessageRequest sendMessageRequest = null;
        try {
        	var groupId = alphanumericId();
    		var deduplicationId = alphanumericId();
    		
            sendMessageRequest = new SendMessageRequest().withQueueUrl(this.queueUrl)
                    .withMessageBody(message)
                    .withMessageGroupId(groupId)
                    .withMessageDeduplicationId(deduplicationId);
            amazonSQS.sendMessage(sendMessageRequest);
            log.info("Event has been published in SQS({}): {}", this.queueUrl, message);
        } catch (Exception e) {
        	log.error("Exception ocurred while pushing event to sqs : {} and stacktrace ; {}", e.getMessage(), e);
        }
    }
	
	private String alphanumericId() {
		return RandomStringUtils.randomAlphanumeric(10);
	}
}
