package br.com.bluesburguer.orderingsystem.test;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import br.com.bluesburguer.orderingsystem.order.domain.Fase;
import br.com.bluesburguer.orderingsystem.order.domain.OrderStatusUpdated;
import br.com.bluesburguer.orderingsystem.order.domain.Status;
import br.com.bluesburguer.orderingsystem.order.domain.Step;
import br.com.bluesburguer.orderingsystem.test.infra.sqs.OrderStatusUpdatedEventPublisher;

/** 
 * 
*/
@SpringBootApplication
@EnableDiscoveryClient
public class OrderingsystemTest {
	
	@Autowired
	private OrderStatusUpdatedEventPublisher publisher;

	public static void main(String[] args) {
		SpringApplication.run(OrderingsystemTest.class, args);
	}

	@PostConstruct
	public void init() {
		publishEvent(Step.ORDER, Fase.PENDING);
		publishEvent(Step.ORDER, Fase.IN_PROGRESS);
		publishEvent(Step.KITCHEN, Fase.PENDING);
		publishEvent(Step.KITCHEN, Fase.IN_PROGRESS);
		publishEvent(Step.DELIVERY, Fase.PENDING);
		publishEvent(Step.DELIVERY, Fase.IN_PROGRESS);
	}
	
	private void publishEvent(Step step, Fase fase) {
		var id = UUID.randomUUID();
		var status = new Status(step, fase);
		publisher.publishEvent(new OrderStatusUpdated(id, status));
	}
}
