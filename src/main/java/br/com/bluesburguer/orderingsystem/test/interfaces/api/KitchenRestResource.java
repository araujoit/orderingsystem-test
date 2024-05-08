package br.com.bluesburguer.orderingsystem.test.interfaces.api;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesburguer.orderingsystem.order.domain.events.OrderInProduction;
import br.com.bluesburguer.orderingsystem.order.domain.events.OrderPaid;
import br.com.bluesburguer.orderingsystem.order.domain.events.OrderProduced;
import br.com.bluesburguer.orderingsystem.test.infra.client.sqs.OrderInProgressEventPublisherImpl;
import br.com.bluesburguer.orderingsystem.test.infra.client.sqs.OrderPaidEventPublisherImpl;
import br.com.bluesburguer.orderingsystem.test.infra.client.sqs.OrderProducedEventPublisherImpl;
import br.com.bluesburguer.orderingsystem.test.interfaces.OrderClient;
import br.com.bluesburguer.orderingsystem.test.interfaces.dto.OrderDto;
import br.com.bluesburguer.orderingsystem.test.interfaces.dto.OrderItemDto;
import br.com.bluesburguer.orderingsystem.test.interfaces.dto.OrderRequest;
import br.com.bluesburguer.orderingsystem.test.interfaces.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/event/kitchen")
@RequiredArgsConstructor
public class KitchenRestResource {
	
	private final OrderClient client;
	
	private final OrderPaidEventPublisherImpl orderPaidEventPublisher;
	
	private final OrderInProgressEventPublisherImpl orderInProgressEventPublisher;
	
	private final OrderProducedEventPublisherImpl orderProducedEventPublisher;

	@PostMapping
	public String createNewOrder() {
		var order = recoverOrCreateOrder();
		log.info("Considerando pedido: {}", order);
		
		return orderPaidEventPublisher.publish(OrderPaid.builder().orderId(order.getId()).build())
				.orElseThrow();
			
	}
	
	@PutMapping("/IN_PROGRESS")
	public String orderInProgressAtKitchen() {
		var order = recoverOrCreateOrder();
		log.info("Considerando pedido: {}", order);
		
		return orderInProgressEventPublisher.publish(OrderInProduction.builder().orderId(order.getId()).build())
				.orElseThrow();
	}
	
	@PutMapping("DONE")
	public String orderDoneAtKitchen() {
		var order = recoverOrCreateOrder();
		log.info("Considerando pedido: {}", order);
		
		return orderProducedEventPublisher.publish(OrderProduced.builder().orderId(order.getId()).build())
				.orElseThrow();
	}
	
	private OrderDto recoverOrCreateOrder() {
		var user = new UserDto();
		user.setCpf("376.497.438-96");
		var item1 = OrderItemDto.builder()
				.id(1L)
				.quantity(1)
				.build();
		
		try {
			return client.getById(1L);
		} catch(Exception e) {
			// supressed
		}
		
		var item2 = OrderItemDto.builder()
				.id(2L)
				.quantity(3)
				.build();
		var newOrder = OrderRequest.builder()
				.user(user)
				.items(List.of(item1, item2))
				.build();
		
		return client.createNewOrder(newOrder);
	}
}
