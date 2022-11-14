package com.example.foodmap.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodMapReq {

	@JsonProperty("city")
	private String city;
	
	@JsonProperty("store")
	private String store;

	@JsonProperty("store_assess")
	private double storeAssess;

	@JsonProperty("meals")
	private String meals;

	@JsonProperty("meals_price")
	private int mealsPrice;

	@JsonProperty("meals_assess")
	private int mealsAssess;
	
	private int num;

	private String newStore;
	
	private String newMeals;
	
	public FoodMapReq() {

	}
	
	public FoodMapReq(String store, double storeAssess, String meals, int mealsPrice, int mealsAssess) {
		this.store = store;
		this.storeAssess = storeAssess;
		this.meals = meals;
		this.mealsPrice = mealsPrice;
		this.mealsAssess = mealsAssess;
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

	public String getMeals() {
		return meals;
	}

	public void setMeals(String meals) {
		this.meals = meals;
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

	public String getNewStore() {
		return newStore;
	}

	public void setNewStore(String newStore) {
		this.newStore = newStore;
	}

	public String getNewMeals() {
		return newMeals;
	}

	public void setNewMeals(String newMeals) {
		this.newMeals = newMeals;
	}
	
	

}
