package br.com.bluesburguer.orderingsystem.test.infra.client.fake;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import br.com.bluesburguer.orderingsystem.order.domain.OrderStatusUpdated;
import br.com.bluesburguer.orderingsystem.test.infra.sqs.OrderStatusUpdatedEventPublisher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Profile({"fake"})
public class OrderStatusUpdateEventPublisherFake implements OrderStatusUpdatedEventPublisher {
	
	@Override
	public void publishEvent(OrderStatusUpdated event) {
		log.info("Publishing event {}", event);
	}

}
