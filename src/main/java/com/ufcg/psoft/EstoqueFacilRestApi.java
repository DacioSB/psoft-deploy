package com.ufcg.psoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.ufcg.psoft"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
public class EstoqueFacilRestApi {

	public static void main(String[] args) {
		SpringApplication.run(EstoqueFacilRestApi.class, args);
	}
}
