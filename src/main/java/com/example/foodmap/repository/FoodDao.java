package com.example.foodmap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmap.entity.Food;
import com.example.foodmap.entity.Food_Id;

@Repository
public interface FoodDao extends JpaRepository<Food, Food_Id> {

	public List<Food> findByStore(String store);
	
	public int findMealsAssessByStore(int mealsAssess);

	public List<Food> findByStoreAndMealsAssessGreaterThanEqualOrderByMealsAssessDesc(String store,int mealsAssess);
	
}



//public List<Food> findByMealsAssess(int mealsAssess);

//public List<Food> findByMeals(String meals);

//public List<Food> findByMealsAssessGreaterThanEqual(int mealsAssess);
