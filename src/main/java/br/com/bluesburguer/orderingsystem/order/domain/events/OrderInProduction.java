package br.com.bluesburguer.orderingsystem.order.domain.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderInProduction extends OrderEvent {

	private static final long serialVersionUID = 7702500048926979660L;

	@Override
	public String toString() {
		return this.getClass().getName() + "(" + super.getOrderId() + ")";
	}
}
