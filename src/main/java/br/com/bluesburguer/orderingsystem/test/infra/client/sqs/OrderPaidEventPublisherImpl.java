package br.com.bluesburguer.orderingsystem.test.infra.client.sqs;

import org.springframework.stereotype.Service;

import br.com.bluesburguer.orderingsystem.order.domain.events.OrderPaid;
import lombok.ToString;

@ToString(callSuper = true)
@Service
public class OrderPaidEventPublisherImpl extends OrderEventPublisherImpl<OrderPaid> {

	protected OrderPaidEventPublisherImpl() {
		super("order-paid.fifo");
	}
}
