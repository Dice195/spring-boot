package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.TestRepository;

@Service
public class TestService {
	
	@Autowired
	private TestRepository repository;
		public String test() {
			System.out.println("in test service");
			return repository.test();
		}
	
}
