package com.my.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.dto.PageBean;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.repository.RepBoardRepository;
import com.my.vo.RepBoard;

@Service
public class RepBoardService {

	@Autowired
	private RepBoardRepository repository;
	public RepBoardService() {}
	public RepBoardService(RepBoardRepository repository) {
		this.repository = repository;
	}
	public RepBoardRepository getRepository() {
		return repository;
	}
	public void setRepository(RepBoardRepository repository) {
		this.repository = repository;
	}

	/**
	 * 게시글을 쓴다.
	 * @param rb
	 * @throws AddException
	 */
	public int writeBoard(RepBoard rb) throws AddException {
		rb.setParentNo(0);		//원글쓰기 
		return repository.insert(rb);
	}
	/**
	 * 답글을 쓴다.
	 * @param rb 답글
	 * @throws AddException 부모글번호가 없는 경우나 답글쓰기 실패된 경우 예외 발생한다.
	 */
	public int writeReply(RepBoard rb) throws AddException {
		if(rb.getParentNo() == 0) {		//부모글번호가 없는경우
			throw new AddException("부모 글번호가 없습니다.");
		}
		return repository.insert(rb);
	}
	/**
	 * 전체 게시글 목록을 반환한다..
	 * @return 
	 * @throws FindException
	 */
	public List<RepBoard> findAll() throws FindException{
		return repository.selectAll();
	}
	/**
	 * 전체 게시글 목록을 반환한다.
	 * @param currentPage 검색할 페이지
	 * @param cntPerPage 페이지별 보여줄 목록수 
	 * @return
	 * @throws FindException 
	 */
	public List<RepBoard> findAll(int currentPage, int cntPerPage) throws FindException {
		return repository.selectAll(currentPage,cntPerPage);
	}
	/**
	 * 페이지에 해당하는 페이지빈 정보를 반환한다.
	 * @param currentPage
	 * @return 페이지빈 페이지빈에는 게시글목록, 총페이지수, 페이지그룹이 시작페이지,끝페이지정보가 모두 담겨있다. 
	 * @throws FindException
	 */
	public PageBean<RepBoard> getPageBean(int currentPage) throws FindException{
		List<RepBoard> list = findAll(currentPage, PageBean.CNT_PER_PAGE);
		int totalCnt = repository.selectCount();
		PageBean<RepBoard> pb = new PageBean<>(currentPage, list, totalCnt);
		return pb;
	}
	/**
	 * 게시글 상제 조회한다.
	 * 조회수 1증가한다.
	 * @param boardNo 글번호
	 * @return
	 * @throws FindException
	 * @throws ModifyException 
	 */
	public RepBoard findByBoardNo(int boardNo) throws FindException, ModifyException {
		
		try {
		repository.updateViewCnt(boardNo);
		} catch (FindException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());		
		}
		return repository.selectByBoardNo(boardNo);
	}
	/**
	 * 게시슬 수정한다 게시글 제목, 게시글 내용을 변경한다.
	 * @param rb
	 * @throws ModifyException
	 */
	public void modify(RepBoard rb) throws ModifyException {
		repository.update(rb);
	}
	/**
	 * 게시글 삭제한다. 게시글의 모든 답글(답글과 답답글들)을 삭제한다.
	 * @param boarNo
	 * @throws RemoveException
	 */
	public void remove(int boardNo) throws RemoveException {
		repository.delete(boardNo);
	}
}
