package br.com.bluesburguer.orderingsystem.test.infra.client.sqs;

import org.springframework.stereotype.Service;

import br.com.bluesburguer.orderingsystem.order.domain.events.OrderProduced;
import lombok.ToString;

@ToString(callSuper = true)
@Service
public class OrderProducedEventPublisherImpl extends OrderEventPublisherImpl<OrderProduced> {

	protected OrderProducedEventPublisherImpl() {
		super("order-produced.fifo");
	}
}
