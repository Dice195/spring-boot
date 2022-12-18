package com.my.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.repository.OrderRepository;
import com.my.vo.OrderInfo;

@Service
public class OrderService {

	
	@Autowired
	@Qualifier("orderRepository")
	private OrderRepository repository;
	
	public OrderService() {}
	public OrderRepository getRepository() {
		return repository;
	}
	public void setRepository(OrderRepository repository) {
		this.repository = repository;
	}
	
	public void addOrder(OrderInfo info) throws AddException{
		repository.insert(info);
	}
	
	public List<OrderInfo>	findById(String orderId) throws FindException{
		return repository.selectById(orderId);
	}
	
	
	
}