package com.example.foodmap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "food")
@IdClass(FoodId.class)
public class Food {

	@Id
	@Column(name = "store")
	private String storeName; // 店家名稱

	@Id
	@Column(name = "meals")
	private String mealsName; // 餐點

	@Column(name = "meals_price")
	private int mealsPrice; // 餐點價格

	@Column(name = "meals_assess")
	private int mealsAssess; // 餐點評價

	public Food() {

	}

	public Food(String storeName, String mealsName, int mealsPrice, int mealsAssess) {
		this.storeName = storeName;
		this.mealsName = mealsName;
		this.mealsPrice = mealsPrice;
		this.mealsAssess = mealsAssess;
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
