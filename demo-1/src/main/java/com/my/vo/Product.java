package com.my.vo;



public class Product {
		private String prodNo;
		private String prodName;
		private int prodPrice;
		private String prodInfo;
			
		public Product() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Product(String prodNo, String prodName, int prodPrice, String prodInfo) {
			super();
			this.prodNo = prodNo;
			this.prodName = prodName;
			this.prodPrice = prodPrice;
			this.prodInfo = prodInfo;
		}

		@Override
		public String toString() {
			return "Product [prodNo=" + prodNo + ", prodName=" + prodName + ", prodPrice=" + prodPrice + ", prodInfo="
					+ prodInfo + "]";
		}

		public String getProdNo() {
			return prodNo;
		}

		public void setProdNo(String prodNo) {
			this.prodNo = prodNo;
		}

		public String getProdName() {
			return prodName;
		}

		public void setProdName(String prodName) {
			this.prodName = prodName;
		}

		public int getProdPrice() {
			return prodPrice;
		}

		public void setProdPrice(int prodPrice) {
			this.prodPrice = prodPrice;
		}

		public String getProdInfo() {
			return prodInfo;
		}

		public void setProdInfo(String prodInfo) {
			this.prodInfo = prodInfo;
		}
		
		
}
