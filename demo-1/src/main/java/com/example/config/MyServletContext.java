package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = {"control", "advice"})
@EnableWebMvc
public class MyServletContext implements WebMvcConfigurer {

	
	@Bean
	CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver r = new CommonsMultipartResolver();
		//r.setUploadTempDir(null);
		r.setMaxUploadSize(1024*100);
		r.setMaxUploadSizePerFile(1024*100);
		
		return r;
	}
	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
//		WebMvcConfigurer.super.addCorsMappings(registry);

//		registry.addMapping("/**").allowCredentials(true).allowedOrigins("*");
		registry.addMapping("/**").allowCredentials(true).allowedOrigins("http://localhost:9999").allowedMethods("GET", "POST", "PUT", "DELETE");
		registry.addMapping("/**").allowCredentials(true).allowedOrigins("http://192.168.2.20:5500").allowedMethods("GET", "POST", "PUT", "DELETE");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/html/**").addResourceLocations("classpath:/static/html/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
	}
	//경로를 html 별칭 . 찾아올 경로

	@Bean
	ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
			resolver.setPrefix("/WEB-INF/view/");
			resolver.setSuffix(".jsp");
			return resolver;
	}

	
	
	
	
}
