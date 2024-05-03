package br.com.bluesburguer.orderingsystem.test.interfaces.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class OrderRequest implements Serializable {

	private static final long serialVersionUID = 5840689967738151657L;

	@NonNull
	@NotNull
	private UserDto user;
	
	@Default
	private List<OrderItemDto> items = new ArrayList<>();
}
