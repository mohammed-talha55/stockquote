package com.talha.stockquotes.pojo;

public class StockDataRequest {

	private String stockQuote;
	private String companyName;
	private String price;
	private String currency;

	public String getStockQuote() {
		return stockQuote;
	}

	public void setStockQuote(String stockQuote) {
		this.stockQuote = stockQuote;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
