package com.example.springMVCApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringMvcAppApplicationTests {

	@Test
	void contextLoads() {
	}


	class Calculator{
		public int add(int a, int b){return a + b;}
	}
}
