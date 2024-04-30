package br.com.bluesburguer.orderingsystem.test.utils;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import br.com.bluesburguer.orderingsystem.test.OrderingsystemTest;

@SpringBootTest(classes = OrderingsystemTest.class)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles({ "test" })
public class BaseIntegrationTest {

}
