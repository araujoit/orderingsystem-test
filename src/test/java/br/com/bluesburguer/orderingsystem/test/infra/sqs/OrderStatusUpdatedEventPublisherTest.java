package br.com.bluesburguer.orderingsystem.test.infra.sqs;

import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.bluesburguer.orderingsystem.order.domain.Fase;
import br.com.bluesburguer.orderingsystem.order.domain.OrderStatusUpdated;
import br.com.bluesburguer.orderingsystem.order.domain.Status;
import br.com.bluesburguer.orderingsystem.order.domain.Step;
import br.com.bluesburguer.orderingsystem.test.infra.client.sqs.OrderStatusUpdatedEventPublisherImpl;
import br.com.bluesburguer.orderingsystem.test.utils.BaseIntegrationTest;

class OrderStatusUpdatedEventPublisherTest extends BaseIntegrationTest {
	
	@Autowired
	private OrderStatusUpdatedEventPublisherImpl publisher;

	@Test
	void shouldPublishEvent() throws JsonProcessingException, InterruptedException {
		for (int i = 1; i <= 10; i++) {
			var step = new RandomEnumGenerator<Step>(Step.class).randomEnum();
			var fase = new RandomEnumGenerator<Fase>(Fase.class).randomEnum();
			
			var status = new Status(step, fase);
			var event = new OrderStatusUpdated(UUID.randomUUID(), status);
			publisher.publishEvent(event);
		}
	}
	
	private class RandomEnumGenerator<T extends Enum<T>> {
	    private static final Random RANDOM = new Random();
	    private final T[] values;

	    public RandomEnumGenerator(Class<T> e) {
	        values = e.getEnumConstants();
	    }

	    public T randomEnum() {
	        return values[RANDOM.nextInt(values.length)];
	    }
	}
}
