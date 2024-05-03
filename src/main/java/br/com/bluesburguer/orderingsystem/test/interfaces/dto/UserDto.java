package br.com.bluesburguer.orderingsystem.test.interfaces.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7005512004367912352L;

	private Long id;
	
	private String cpf;
	
	private String email;
}
