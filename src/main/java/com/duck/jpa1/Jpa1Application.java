package com.duck.jpa1;


import com.duck.jpa1.bootstrap.BootStrapData;
import com.duck.jpa1.services.TestService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Jpa1Application {


	public static void main(String[] args) {
		SpringApplication.run(Jpa1Application.class, args);

		// ApplicationContext applicationContext = 
		// 		new AnnotationConfigApplicationContext(Jpa1Application.class);

		// try {
		// BootStrapData bootStrapData = applicationContext.getBean(BootStrapData.class);
		// String[] args2 = {};
		// bootStrapData.run(args2);

		// TestService testService = applicationContext.getBean(TestService.class);
		// testService.test();
		// } catch(Exception e) {
		// 	e.printStackTrace();
		// }


	}


}
