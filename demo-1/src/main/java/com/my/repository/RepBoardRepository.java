	package com.my.repository;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.vo.RepBoard;

public interface RepBoardRepository {
	/**
	 * 글을 추가한다.
	 * 추가된 글번호를 반환한다.
	 * @param rb 글
	 * @throws AddException
	 */
	int insert(RepBoard rb) throws AddException;	
	/**
	 * 총 게시글 수를 반환한다.
	 * @return 게시글 개수
	 * @throws FindException
	 */
	int selectCount() throws FindException;
	/**
	 * 모든 글을 검색한다.
	 * @return
	 * @throws FindException
	 */
	List<RepBoard> selectAll() throws FindException;
	/**
	 * 페이지에 해당하는 글을 검색한다.
	 * @param currentPage
	 * @param cntPerPage
	 * @return
	 * @throws FindException
	 */
	List<RepBoard> selectAll(int currentPage, int cntPerPage) throws FindException;
	/**
	 * 글번호에 해당하는 글을 검색한다.
	 * @param boardNo 글번호
	 * @return
	 * @throws FindException
	 */
	RepBoard selectByBoardNo(int boardNo) throws FindException;
	/**
	 * 조회수 1 증가
	 * @param boardNo 글번호
	 * @throws FindException
	 * @throws ModifyException 
	 */
	void updateViewCnt(int boardNo) throws FindException, ModifyException;
	/**
	 * 글제목 글내용 수정
	 * @param rb
	 * @throws ModifyException
	 */
	void update(RepBoard rb) throws ModifyException;
	/**
	 * 글번호에 해당하는 글 삭제
	 * @param boardNo
	 * @throws RemoveExcepion
	 */
	void delete(int boardNo) throws RemoveException;
	

}
