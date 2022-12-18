package com.example.repository;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.vo.RepBoard;

@Repository
public class TestRepository {
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	//마이바티스용 스프링빈(jdbc 드라이버필요, Spring-jdbc라이브러리, mybatis라이브러리,mybatis Spring라이브러리,log4jdbc)
//	private DataSource dataSource;	//hikariCp 스프링빈 SimpleDriverDataSource
	
	public String test() {
		SqlSession session = null;
		session = sqlSessionFactory.openSession();
		RepBoard rb = session.selectOne("com.my.mybatis.RepBoardMapper.selectByBoardNo", 48);

		String boardTitle = rb.getBoardTitle();
		return "TestRepository빈의 test()메서드입니다 게시글번호(17)의 제목:" + boardTitle;

	}
	
	
}
