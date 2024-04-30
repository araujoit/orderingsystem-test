package br.com.bluesburguer.orderingsystem.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.bluesburguer.orderingsystem.test.utils.BaseIntegrationTest;

class OrderingsystemTestIntegrationTests extends BaseIntegrationTest {

	@Test
	void context() {
		assertThat(super.hashCode()).isNotZero();
	}
}
