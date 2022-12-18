package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.repository.TestRepository;


//@ComponentScan(basePackages = {"com.example","com.example.repository","service","config"})
@SpringBootApplication
public class Demo1Application {

	//@Bean 스프링 빈 설정 가능 
	
	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
		System.out.println(getBean(com.example.repository.TestRepository.class));
		System.out.println(getBean(com.example.repository.TestRepository.class));
		System.out.println(getBean(com.example.service.TestService.class));
		
		Object bean = 
				getBean(com.example.repository.TestRepository.class);
		TestRepository repository = (TestRepository)bean;
		System.out.println(repository.test());

	}
	public static Object getBean(Class<?> classType) {
	    ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
	    return applicationContext.getBean(classType); //스프링컨테이너의 클래스이름으로 빈찾기 
	    
	}

}
