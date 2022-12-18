package com.my.vo;


public class OrderLine {
	private int orderNo;
//	private String ordertprodNo;//???
	private Product orderp;
	private int orderQuantity;
	@Override
	public String toString() {
		return "OrderLine [orderNo=" + orderNo + ", orderp=" + orderp + ", orderQuantity=" + orderQuantity + "]";
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public Product getOrderp() {
		return orderp;
	}
	public void setOrderp(Product orderp) {
		this.orderp = orderp;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	
}
