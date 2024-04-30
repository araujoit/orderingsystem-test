package br.com.bluesburguer.orderingsystem.test.infra.sqs;

import br.com.bluesburguer.orderingsystem.order.domain.OrderStatusUpdated;

public interface OrderStatusUpdatedEventPublisher {

	void publishEvent(OrderStatusUpdated event);
}
