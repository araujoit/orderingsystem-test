package br.com.bluesburguer.orderingsystem.test.interfaces.api;

import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesburguer.orderingsystem.order.domain.events.OrderDelivered;
import br.com.bluesburguer.orderingsystem.order.domain.events.OrderDelivering;
import br.com.bluesburguer.orderingsystem.test.infra.client.sqs.OrderDeliveredEventPublisherImpl;
import br.com.bluesburguer.orderingsystem.test.infra.client.sqs.OrderDeliveringEventPublisherImpl;
import br.com.bluesburguer.orderingsystem.test.interfaces.OrderClient;
import br.com.bluesburguer.orderingsystem.test.interfaces.dto.OrderDto;
import br.com.bluesburguer.orderingsystem.test.interfaces.dto.OrderItemDto;
import br.com.bluesburguer.orderingsystem.test.interfaces.dto.OrderRequest;
import br.com.bluesburguer.orderingsystem.test.interfaces.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/event/delivery")
@RequiredArgsConstructor
public class DeliveryRestResource {
	
	private final OrderClient client;
	
	private final OrderDeliveringEventPublisherImpl orderDeliveringEventPublisher;
	
	private final OrderDeliveredEventPublisherImpl orderDeliveredEventPublisher;
	
	@PutMapping("/IN_PROGRESS")
	public String orderInProgressAtDelivery() {
		var order = recoverOrCreateOrder();
		log.info("Considerando pedido: {}", order);
		
		return orderDeliveringEventPublisher.publish(OrderDelivering.builder().orderId(order.getId()).build())
				.orElseThrow();
	}
	
	@PutMapping("DONE")
	public String orderDoneAtDelivery() {
		var order = recoverOrCreateOrder();
		log.info("Considerando pedido: {}", order);
		
		return orderDeliveredEventPublisher.publish(OrderDelivered.builder().orderId(order.getId()).build())
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
