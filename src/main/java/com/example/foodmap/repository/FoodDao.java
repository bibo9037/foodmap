package com.example.foodmap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmap.entity.Food;
import com.example.foodmap.entity.Food_Id;

@Repository
public interface FoodDao extends JpaRepository<Food, Food_Id> {

	public List<Food> findByStore(String store);
	
//	public List<Food> findStoreAssessAndMealsAssessGreaterThan(double storeAssess, int mealsAssess);
	
	public int findMealsAssessByStore(int mealsAssess);
	
	public List<Food> findByMealsAssess(int mealsAssess);
	
}
