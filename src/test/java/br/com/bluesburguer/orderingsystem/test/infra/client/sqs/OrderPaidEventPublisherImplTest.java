package br.com.bluesburguer.orderingsystem.test.infra.client.sqs;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.bluesburguer.orderingsystem.order.domain.events.OrderPaid;
import br.com.bluesburguer.orderingsystem.test.infra.client.sqs.OrderPaidEventPublisherImpl;
import br.com.bluesburguer.orderingsystem.test.utils.BaseIntegrationTest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class OrderPaidEventPublisherImplTest extends BaseIntegrationTest {
	
	private final OrderPaidEventPublisherImpl orderPaidEventPublisher;

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
