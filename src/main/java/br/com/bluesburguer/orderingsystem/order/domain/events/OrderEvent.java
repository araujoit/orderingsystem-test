package br.com.bluesburguer.orderingsystem.order.domain.events;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public abstract class OrderEvent implements Serializable {
	
	private static final long serialVersionUID = -3527498526085380774L;
	
	@NonNull
	protected Long orderId;
}
