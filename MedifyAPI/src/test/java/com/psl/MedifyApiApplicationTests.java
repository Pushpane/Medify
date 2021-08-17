package com.psl;

import com.psl.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@SpringBootTest
@ContextConfiguration(classes = MedifyApiApplication.class, loader = AnnotationConfigContextLoader.class)

class MedifyApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
