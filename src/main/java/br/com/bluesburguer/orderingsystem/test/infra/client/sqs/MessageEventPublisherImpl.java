package br.com.bluesburguer.orderingsystem.test.infra.client.sqs;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import br.com.bluesburguer.orderingsystem.test.infra.sqs.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageEventPublisherImpl implements EventPublisher<String> {
	
	@Value("${cloud.aws.sqs.queue.messages}")
	private String queueUrl;
	
	private final AmazonSQS amazonSQS;

	@Override
	public Optional<String> publish(String message) {
		log.info("Event publishing message {} in SQS queue {}", message, this.queueUrl);
		
        SendMessageRequest sendMessageRequest = null;
        try {
        	var groupId = alphanumericId();
    		var deduplicationId = alphanumericId();
    		
            sendMessageRequest = new SendMessageRequest()
            		.withQueueUrl(this.queueUrl)
                    .withMessageBody(message)
                    .withMessageGroupId(groupId)
                    .withMessageDeduplicationId(deduplicationId);
            var response = amazonSQS.sendMessage(sendMessageRequest);
            return Optional.of(response.getMessageId());
        } catch (Exception e) {
        	log.error("Exception ocurred while pushing event to sqs : {} and stacktrace ; {}", e.getMessage(), e);
        }
		return Optional.empty();
	}
	
	private String alphanumericId() {
		return RandomStringUtils.randomAlphanumeric(10);
	}

}
