package br.com.bluesburguer.orderingsystem.order.domain.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderPaid extends OrderEvent {

	private static final long serialVersionUID = 7702500048926979660L;

}
