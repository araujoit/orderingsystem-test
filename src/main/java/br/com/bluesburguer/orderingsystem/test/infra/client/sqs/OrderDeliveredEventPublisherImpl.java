package br.com.bluesburguer.orderingsystem.test.infra.client.sqs;

import org.springframework.stereotype.Service;

import br.com.bluesburguer.orderingsystem.order.domain.events.OrderDelivered;
import lombok.ToString;

@ToString(callSuper = true)
@Service
public class OrderDeliveredEventPublisherImpl extends OrderEventPublisherImpl<OrderDelivered> {

	protected OrderDeliveredEventPublisherImpl() {
		super("order-delivered.fifo");
	}
}
