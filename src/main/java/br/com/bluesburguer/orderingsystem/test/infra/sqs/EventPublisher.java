package br.com.bluesburguer.orderingsystem.test.infra.sqs;

import java.util.Optional;

public interface EventPublisher<T> {

	Optional<String> publish(T event);
}
