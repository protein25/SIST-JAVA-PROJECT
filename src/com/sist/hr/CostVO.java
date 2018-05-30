package com.sist.hr;

public class CostVO {
	private String priceGroup=null;
	private int cost=0;
	
	public CostVO() {
		// TODO Auto-generated constructor stub
	}
	public CostVO(String priceGroup,int cost) {
		this.priceGroup=priceGroup;
		this.cost=cost;
	}
	public String getPriceGroup() {
		return priceGroup;
	}
	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}
	
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	@Override
	public String toString() {
		return "CostVO [priceGroup=" + priceGroup + ", cost=" + cost + "]";
	}
	
	

}
