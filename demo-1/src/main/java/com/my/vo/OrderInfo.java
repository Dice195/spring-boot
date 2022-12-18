package com.my.vo;
import java.util.Date;
import java.util.List;



public class OrderInfo {
	private int orderNo;
//	private String orderId; ???
	private Customer orderC;
	private Date orderDt;
	private List<OrderLine> lines;			//주문상세정보 
	@Override
	public String toString() {
		return "OrderInfo [orderNo=" + orderNo + ", orderC=" + orderC + ", orderDt=" + orderDt + ", lines=" + lines
				+ "]";
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public Customer getOrderC() {
		return orderC;
	}
	public void setOrderC(Customer orderC) {
		this.orderC = orderC;
	}
	public Date getOrderDt() {
		return orderDt;
	}
	public void setOrderDt(Date orderDt) {
		this.orderDt = orderDt;
	}
	public List<OrderLine> getLines() {
		return lines;
	}
	public void setLines(List<OrderLine> lines) {
		this.lines = lines;
	}
	
	
}	
