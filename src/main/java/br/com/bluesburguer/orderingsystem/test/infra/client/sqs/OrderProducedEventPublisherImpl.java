package br.com.bluesburguer.orderingsystem.test.infra.client.sqs;

import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bluesburguer.orderingsystem.order.domain.events.OrderPaid;
import lombok.ToString;

@ToString(callSuper = true)
@Service
public class OrderProducedEventPublisherImpl extends OrderEventPublisherImpl<OrderPaid> {

	protected OrderProducedEventPublisherImpl(AmazonSQS amazonSQS, ObjectMapper objectMapper) {
		super("order-produced-queue.fifo", amazonSQS, objectMapper);
	}
}
