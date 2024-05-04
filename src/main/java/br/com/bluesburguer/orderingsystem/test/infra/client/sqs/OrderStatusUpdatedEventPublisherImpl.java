package br.com.bluesburguer.orderingsystem.test.infra.client.sqs;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bluesburguer.orderingsystem.order.domain.OrderStatusUpdated;
import br.com.bluesburguer.orderingsystem.test.infra.sqs.OrderStatusUpdatedEventPublisher;
import lombok.extern.slf4j.Slf4j;

@Deprecated(forRemoval = true, since = "2024-05-03 21:30:00")
@Slf4j
@Service
@Profile({"production", "dev", "test"})
public class OrderStatusUpdatedEventPublisherImpl implements OrderStatusUpdatedEventPublisher {
	
	@Value("${cloud.aws.queue.url:none}")
    private String queueUrl;

    private final AmazonSQS amazonSQS;

    private final ObjectMapper objectMapper;
    
    public OrderStatusUpdatedEventPublisherImpl(AmazonSQS amazonSQS, ObjectMapper objectMapper) {
    	this.amazonSQS = amazonSQS;
    	this.objectMapper = objectMapper;
    }

    public void publishEvent(OrderStatusUpdated event) {
        SendMessageRequest sendMessageRequest = null;
        try {
        	var groupId = alphanumericId();
    		var deduplicationId = alphanumericId();
    		
            sendMessageRequest = new SendMessageRequest().withQueueUrl(this.queueUrl)
                    .withMessageBody(objectMapper.writeValueAsString(event))
                    .withMessageGroupId(groupId)
                    .withMessageDeduplicationId(deduplicationId);
            amazonSQS.sendMessage(sendMessageRequest);
            log.info("Event has been published in SQS({}) with id {}", this.queueUrl, event.getId());
        } catch (JsonProcessingException e) {
        	log.error("JsonProcessingException e : {} and stacktrace : {}", e.getMessage(), e);
        } catch (Exception e) {
        	log.error("Exception ocurred while pushing event to sqs : {} and stacktrace ; {}", e.getMessage(), e);
        }
    }
    
    private String alphanumericId() {
		return RandomStringUtils.randomAlphanumeric(10);
	}
}
