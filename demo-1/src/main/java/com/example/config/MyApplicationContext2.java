package com.example.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"com.my.repository","com.my.service"})
@MapperScan(basePackages = {"mybatis"})
@EnableTransactionManagement

public class MyApplicationContext2 {
	
	@Bean
	com.zaxxer.hikari.HikariConfig hikariConfig() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");		//spy내부구조를 본다.
		config.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@127.0.0.1:1521:XE");
		config.setUsername("hr");
		config.setPassword("hr");
		config.setMinimumIdle(2);  				//초기커넥션을 몇개를 만들것인가.
		return config;
	}
	@Bean
	DataSource dataSoruce() {
		return new HikariDataSource(hikariConfig());
	}
	
	@Bean
	SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactory= new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSoruce());
		org.springframework.core.io.Resource resource = new ClassPathResource("mybatis.xml");
		sqlSessionFactory.setConfigLocation(resource);
		return (SqlSessionFactory)sqlSessionFactory.getObject();
	}

	@Bean 
	DataSourceTransactionManager txManager() {
		DataSourceTransactionManager tx = new DataSourceTransactionManager(dataSoruce());
		return tx;
	}

}
