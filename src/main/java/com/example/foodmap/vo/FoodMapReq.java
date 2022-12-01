package com.example.foodmap.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodMapReq {

	@JsonProperty("city")
	private String city;
	
	@JsonProperty("store")
	private String storeName;

	@JsonProperty("store_assess")
	private double storeAssess;

	@JsonProperty("meals")
	private String mealsName;

	@JsonProperty("meals_price")
	private int mealsPrice;

	@JsonProperty("meals_assess")
	private int mealsAssess;
	
	private int num;

	private String newStoreName;
	
	private String newMealsName;
	
	public FoodMapReq() {

	}
	
	public FoodMapReq(String storeName, double storeAssess, String mealsName, int mealsPrice, int mealsAssess) {
		this.storeName = storeName;
		this.storeAssess = storeAssess;
		this.mealsName = mealsName;
		this.mealsPrice = mealsPrice;
		this.mealsAssess = mealsAssess;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public double getStoreAssess() {
		return storeAssess;
	}

	public void setStoreAssess(double storeAssess) {
		this.storeAssess = storeAssess;
	}

	public String getMealsName() {
		return mealsName;
	}

	public void setMealsName(String mealsName) {
		this.mealsName = mealsName;
	}

	public int getMealsPrice() {
		return mealsPrice;
	}

	public void setMealsPrice(int mealsPrice) {
		this.mealsPrice = mealsPrice;
	}

	public int getMealsAssess() {
		return mealsAssess;
	}

	public void setMealsAssess(int mealsAssess) {
		this.mealsAssess = mealsAssess;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getNewStoreName() {
		return newStoreName;
	}

	public void setNewStoreName(String newStoreName) {
		this.newStoreName = newStoreName;
	}

	public String getNewMealsName() {
		return newMealsName;
	}

	public void setNewMealsName(String newMealsName) {
		this.newMealsName = newMealsName;
	}
	
}
