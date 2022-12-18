package com.my.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.vo.OrderInfo;
import com.my.vo.OrderLine;

import mybatis.OrderMapper;

@Repository("orderRepository")
public class OrderRepositoryOracle implements OrderRepository{

//	@Autowired
//	private DataSource ds;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	private void insertInfo(SqlSession session,OrderInfo info) throws Exception{
	
//			SqlSession session = null;
//			session = sqlSessionFactory.openSession();
//			session.insert("com.my.mybatis.OrderMapper.insertInfo",info.getOrderC().getId());
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			mapper.insertInfo(info.getOrderC().getId());
	}

	private void insertLines(SqlSession session,List<OrderLine> lines) throws Exception{
		for(OrderLine line: lines) {
//			session.insert("com.my.mybatis.OrderMapper.insertLine",line);
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			mapper.insertLine(line);
			
		}
	
	}
	
	@Override
	@Transactional(rollbackFor = AddException.class )
	public void insert(OrderInfo info) throws AddException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			//트랜잭션 처리는 AOP 활용하여 선언적 트랜잭션 
			
			//주문기본정보추가
			insertInfo(session, info);
			//주문상세정보추가
			insertLines(session, info.getLines());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}finally {
			if (session != null)
				session.close();
		}
	}
	
	@Override
	public List<OrderInfo> selectById(String orderId) throws FindException {
		List<OrderInfo> infos = new ArrayList<>();
		
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
//			infos = session.selectList("com.my.mybatis.OrderMapper.selectById",orderId);
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			infos = mapper.selectById(orderId);
			
			return infos;
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
}

	
}