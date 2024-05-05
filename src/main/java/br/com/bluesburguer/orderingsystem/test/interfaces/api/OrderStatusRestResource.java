package br.com.bluesburguer.orderingsystem.test.interfaces.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesburguer.orderingsystem.order.domain.Fase;
import br.com.bluesburguer.orderingsystem.order.domain.OrderStatusUpdated;
import br.com.bluesburguer.orderingsystem.order.domain.Status;
import br.com.bluesburguer.orderingsystem.order.domain.Step;
import br.com.bluesburguer.orderingsystem.test.infra.sqs.OrderStatusUpdatedEventPublisher;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/event/order-status")
@RequiredArgsConstructor
public class OrderStatusRestResource {
	
	@Autowired
	private OrderStatusUpdatedEventPublisher publisher;
	
	@PostMapping("/order/pending")
	public void publicOrderPending() {
		publishEvent(Step.ORDER, Fase.PENDING);		
	}
	
	@PostMapping("/order/in_progress")
	public void publicOrderInProgress() {
		publishEvent(Step.ORDER, Fase.IN_PROGRESS);		
	}
	
	@PostMapping("/kitchen/pending")
	public void publicKitchenPending() {				
		publishEvent(Step.KITCHEN, Fase.PENDING);
	}
	
	@PostMapping("/kitchen/in_progress")
	public void publicKitchenInProgress() {				
		publishEvent(Step.KITCHEN, Fase.IN_PROGRESS);
	}
	
	@PostMapping("/delivery/pending")
	public void publicDeliveryPending() {				
		publishEvent(Step.DELIVERY, Fase.PENDING);
	}
	
	@PostMapping("/delivery/in_progress")
	public void publicDeliveryInProgress() {				
		publishEvent(Step.DELIVERY, Fase.IN_PROGRESS);
	}

	private void publishEvent(Step step, Fase fase) {
		var id = UUID.randomUUID();
		var status = new Status(step, fase);
		publisher.publishEvent(new OrderStatusUpdated(id, status));
	}
}
