package com.example.foodmap.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FoodId implements Serializable {

	private String storeName;

	private String mealsName;

	public FoodId() {

	}

	public FoodId(String storeName, String meals) {
		this.storeName = storeName;
		this.mealsName = meals;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getMealsName() {
		return mealsName;
	}

	public void setMealsName(String mealsName) {
		this.mealsName = mealsName;
	}

}