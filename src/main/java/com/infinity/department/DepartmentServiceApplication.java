package com.infinity.department;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

//@EnableFeignClients
@SpringBootApplication
@RefreshScope
public class DepartmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepartmentServiceApplication.class, args);
	}
//	@Bean(name = "output")
//	public MessageChannel outputChannel() {
//		return new DirectWithAttributesChannel();
//	}

//	@Bean
//	DepartmentService departmentService() {
//		DepartmentService departmentService = new DepartmentService();
//		departmentService.add(new Department(1L, "Development"));
//		departmentService.add(new Department(1L, "Operations"));
//		departmentService.add(new Department(2L, "Development"));
//		departmentService.add(new Department(2L, "Operations"));
//		return departmentService;
//	}
//@Bean
//public Function<String, String> kafkaTest() {
//	return value -> value.toUpperCase();
//}
}
