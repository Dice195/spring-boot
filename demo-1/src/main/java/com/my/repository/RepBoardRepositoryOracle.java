package com.my.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.vo.RepBoard;

import mybatis.RepBoardMapper;

@Repository("repBoardRepository")
public class RepBoardRepositoryOracle implements RepBoardRepository {
	

	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	

	
	@Override
	public int insert(RepBoard rb) throws AddException {
		SqlSession session = null;
		
		try {
			session = sqlSessionFactory.openSession();
			//map 형태로 받는것 
//			Map<String,Object> map = new HashMap<>();
//			map.put("parentNo",rb.getParentNo());
//			map.put("boardTitle", rb.getBoardTitle());
//			map.put("boardC.id", rb.getBoardC().getId());
//			map.put("boardContent", rb.getBoardContent());
//			session.insert("com.my.mybatis.RepBoard.selectAll",map);
			
			//RepBoardMapper.xml 처리방법
//			session.insert("com.my.mybatis.RepBoardMapper.insert",rb);
//			return rb.getBoardNo(); 	
			
			//RepBoardMapper.java인터페이스 사용
			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
			mapper.insert(rb);					 
			return rb.getBoardNo();

			
		} catch (Exception e) {
			e.getStackTrace();
			throw new AddException(e.getMessage());
		} finally {
		if(session !=null) {
			session.close();
			}
		}
	}

	@Override
	public List<RepBoard> selectAll() throws FindException {
		List<RepBoard> list = new ArrayList<>();
		SqlSession session = null;
		try{
			session = sqlSessionFactory.openSession();
//			list = session.selectList("com.my.mybatis.RepBoard.selectAll");
//			return list;
			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
			list = mapper.selectAll();
			return list;
		} catch (Exception e) {
			e.getStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(session !=null) {
				session.close();				
			}
		}
	}

	@Override
	public List<RepBoard> selectAll(int currentPage, int cntPerPage) throws FindException {
		
		SqlSession session = null;
		try {
		session = sqlSessionFactory.openSession();	
		List<RepBoard> list = new ArrayList<>();
		int startRow = ((currentPage-1)*cntPerPage)+1;
		int endRow = currentPage * cntPerPage;
		Map<String, Integer> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
//		list = session.selectList("com.my.mybatis.RepBoardMapper.selectAllPage",map);
			
		RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
		list = mapper.selectAllPage(map);
		return list;
		} catch (Exception e) {
			e.getStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(session != null)	
			session.close();
		}
	}

	@Override
	public int selectCount() throws FindException {
			SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
//			return session.selectOne("com.my.mybatis.RepBoardMapper.selectCount");
			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
			return mapper.selectCount();

		} catch (Exception e) {
			e.getStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(session != null)
				session.close();
		}
	}
	
	@Override
	public RepBoard selectByBoardNo(int boardNo) throws FindException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
//			RepBoard rb = session.selectOne(
//					"com.my.mybatis.RepBoardMapper.selectByBoardNo", boardNo);
			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
			RepBoard rb = mapper.selectByBoardNo(boardNo);
			if (rb == null) {
				throw new FindException("글번호에 해당하는 글이 없습니다.");
			}
			return rb;
		} catch (Exception e) {
			e.getStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(session !=null) 
				session.close();		
		}
	}
	@Override
	public void updateViewCnt(int boardNo) throws ModifyException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
//			session.update("com.my.mybatis.RepBoardMapper.updateViewCnt",boardNo);		
			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
			mapper.updateViewCnt(boardNo);
		} catch (Exception e) {
			e.getStackTrace();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session !=null) 
				session.close();
		}
	}

	@Override
	public void update(RepBoard rb) throws ModifyException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			int rowcnt= session.update("com.my.mybatis.RepBoardMapper.update",rb);
//			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
			
			
//			int rowcnt = mapper.update(rb);
			if(rowcnt == 0) {
				throw new ModifyException("글번호가 없거나 작성자가 다른경우 수정할 수 없습니다");
			}

		} catch (Exception e) {
			e.getStackTrace();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session !=null) 
				session.close();
		}
	}

	@Override
	public void delete(int boardNo) throws RemoveException {
		SqlSession session = null;
		
		try {
			session = sqlSessionFactory.openSession();
//			session.delete("com.my.mybatis.RepBoardMapper.delete",boardNo);
			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
			mapper.delete(boardNo);
		} catch (Exception e) {
			e.getStackTrace();
			throw new RemoveException(e.getMessage());
		} finally {
			if(session !=null) 
			session.close();
		}
	}

}
