package br.com.bluesburguer.orderingsystem.test.infra.client.sqs;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.bluesburguer.orderingsystem.order.domain.events.OrderPaid;
import br.com.bluesburguer.orderingsystem.test.infra.client.sqs.OrderPaidEventPublisher;
import br.com.bluesburguer.orderingsystem.test.utils.BaseIntegrationTest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class OrderPaidEventPublisherTest extends BaseIntegrationTest {
	
	private final OrderPaidEventPublisher orderPaidEventPublisher;

	@Test
	void shouldPublishEvent() throws JsonProcessingException, InterruptedException {
		for (int i = 1; i <= 10; i++) {
			var order = OrderPaid.builder()
					.orderId(1L)
					.build();
			orderPaidEventPublisher.publish(order);
		}
	}
}
