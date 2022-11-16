package com.example.foodmap;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.foodmap.entity.Food;
//import com.example.foodmap.entity.Store;
import com.example.foodmap.repository.FoodDao;
//import com.example.foodmap.repository.StoreDao;

@SpringBootTest
public class FoodMaptest {

//	@Autowired
//	private StoreDao storeDao;
	
	@Autowired
	private FoodDao foodDao;
	
	@Test
	public void findByCity() {
//		List<Store> findByCity = storeDao.findByCity("�x�n��");
//		System.out.println(findByCity);
		 List<Food> foodList =  foodDao.findByStoreAndMealsAssessGreaterThanEqualOrderByMealsAssessDesc("�n�Y", 3);
		 for(Food food : foodList ) {
			 System.out.printf("�ө��W��:%s  �\�I�W��:%s  ����:%d  �\�I����:%d %n",food.getStore(),food.getMeals(),food.getMealsPrice(),food.getMealsAssess());
		 }
		
	}
//	
//	@Test
//	public void findByMealsInfo() {
//		List<Food> food = foodDao.findByMealsInfo("�]�߿�","����", 30, 5);
//		System.out.println(food);
//	}
	
	
}
