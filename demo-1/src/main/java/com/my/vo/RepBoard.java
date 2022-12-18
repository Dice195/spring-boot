package com.my.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class RepBoard {
	private int level;
	private int boardNo;
	private int parentNo;
	private String boardTitle;
	private Customer boardC;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	//json 형태로 응답할때만 사용되는 어노테이션
	private Date boardDt;
	private int boardViewCnt; 
	private String boardContent;
	
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public int getParentNo() {
		return parentNo;
	}
	public void setParentNo(int parentNo) {
		this.parentNo = parentNo;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public Customer getBoardC() {
		return boardC;
	}
	public void setBoardC(Customer boardC) {
		this.boardC = boardC;
	}
	public java.util.Date getBoardDt() {
		return boardDt;
	}
	public void setBoardDt(java.util.Date boardDt) {
		this.boardDt = boardDt;
	}
	public int getBoardViewCnt() {
		return boardViewCnt;
	}
	public void setBoardViewCnt(int boardViewCnt) {
		this.boardViewCnt = boardViewCnt;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public RepBoard(int level, int boardNo, int parentNo, String boardTitle, Customer boardC, Date boardDt,
			int boardViewCnt, String boardContent) {
		super();
		this.level = level;
		this.boardNo = boardNo;
		this.parentNo = parentNo;
		this.boardTitle = boardTitle;
		this.boardC = boardC;
		this.boardDt = boardDt;
		this.boardViewCnt = boardViewCnt;
		this.boardContent = boardContent;
	}
	
	
	public RepBoard() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "RepBoard [level=" + level + ", boardNo=" + boardNo + ", parentNo=" + parentNo + ", boardTitle="
				+ boardTitle + ", boardC=" + boardC + ", boardDt=" + boardDt + ", boardViewCnt=" + boardViewCnt
				+ ", boardContent=" + boardContent + "]";
	}
	
}

