package br.com.bluesburguer.orderingsystem.test.infra.client.sqs;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bluesburguer.orderingsystem.order.domain.events.OrderEvent;
import br.com.bluesburguer.orderingsystem.test.infra.sqs.OrderEventPublisher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public abstract class OrderEventPublisherImpl<T extends OrderEvent> implements OrderEventPublisher<T> {
	
	@Value("${cloud.aws.queue.host}")
    private String queueHost;
    
    private final String queueName;

    private final AmazonSQS amazonSQS;

    private final ObjectMapper objectMapper;
    
    protected OrderEventPublisherImpl(AmazonSQS amazonSQS, ObjectMapper objectMapper, String queueName) {
    	this.amazonSQS = amazonSQS;
    	this.objectMapper = objectMapper;
    	this.queueName = queueName;
    }

    @Override
    public Optional<String> publish(T event) {
        SendMessageRequest sendMessageRequest = null;
        try {
        	var groupId = alphanumericId();
    		var deduplicationId = alphanumericId();
    		
    		var fullQueueUrl = buildQueueUrl();
            sendMessageRequest = new SendMessageRequest().withQueueUrl(fullQueueUrl)
                    .withMessageBody(objectMapper.writeValueAsString(event))
                    .withMessageGroupId(groupId)
                    .withMessageDeduplicationId(deduplicationId);
            var result = amazonSQS.sendMessage(sendMessageRequest);
            var messageId = result.getMessageId();
            log.info("Evento {} foi publicado no SQS(fila {}) com o id {}", event, fullQueueUrl, messageId);
            return Optional.ofNullable(messageId);
        } catch (JsonProcessingException e) {
        	log.error("JsonProcessingException e : {} and stacktrace : {}", e.getMessage(), e);
        } catch (Exception e) {
        	log.error("Exception ocurred while pushing event to sqs : {} and stacktrace ; {}", e.getMessage(), e);
        }
        return Optional.empty();
    }
    
    private String buildQueueUrl() {
    	return String.join("/", this.queueHost, this.queueName);
    }
    
    private String alphanumericId() {
		return RandomStringUtils.randomAlphanumeric(10);
	}
}
