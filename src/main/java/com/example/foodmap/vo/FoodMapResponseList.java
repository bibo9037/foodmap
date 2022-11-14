package com.example.foodmap.vo;

public class FoodMapResponseList {

	private String city;

	private String store;

	private double storeAssess;

	public FoodMapResponseList() {

	}

	public FoodMapResponseList(String city, String store, double storeAssess) {
		this.city = city;
		this.store = store;
		this.storeAssess = storeAssess;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public double getStoreAssess() {
		return storeAssess;
	}

	public void setStoreAssess(double storeAssess) {
		this.storeAssess = storeAssess;
	}

}
