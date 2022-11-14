package com.example.foodmap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "food")
@IdClass(Food_Id.class)
public class Food {

	@Id
	@Column(name = "store")
	private String store; // 店家名稱

	@Id
	@Column(name = "meals")
	private String meals; // 餐點

	@Column(name = "meals_price")
	private int mealsPrice; // 餐點價格

	@Column(name = "meals_assess")
	private int mealsAssess; // 餐點評價

	public Food() {

	}

	public Food(String store, String meals, int mealsPrice, int mealsAssess) {
		this.store = store;
		this.meals = meals;
		this.mealsPrice = mealsPrice;
		this.mealsAssess = mealsAssess;
	}
	

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
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

}
