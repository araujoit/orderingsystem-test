package br.com.bluesburguer.orderingsystem.test.infra.client.sqs;

import org.springframework.stereotype.Service;

import br.com.bluesburguer.orderingsystem.order.domain.events.OrderInProduction;
import lombok.ToString;

@ToString(callSuper = true)
@Service
public class OrderInProgressEventPublisherImpl extends OrderEventPublisherImpl<OrderInProduction> {

	protected OrderInProgressEventPublisherImpl() {
		super("order-in-production.fifo");
	}
}
