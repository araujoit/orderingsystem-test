package br.com.bluesburguer.orderingsystem.test.infra.sqs;

import java.util.Optional;

import br.com.bluesburguer.orderingsystem.order.domain.events.OrderEvent;

public interface OrderEventPublisher<T extends OrderEvent> {

	Optional<String> publish(T event);
}
