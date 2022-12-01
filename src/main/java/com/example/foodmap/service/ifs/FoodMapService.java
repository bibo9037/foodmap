package com.example.foodmap.service.ifs;

import java.util.List;

import com.example.foodmap.entity.Food;
import com.example.foodmap.entity.Store;
import com.example.foodmap.vo.FoodMapRes;

public interface FoodMapService {

	// 1.寫入店家評價
	public Store setStore(String city, String storeName);

	// 1-1.修改商店資料 //Info可以代替DB內多欄位的資料
	public Store changeInfoForStore(String city, String storeName, String newStoreName);

	// 2.寫入餐點評價
	public Food setMeals(String storeName, String mealsName, int mealsPrice, int mealsAssess);

	// 2-2.修改餐點資料
	public Food changeInfoForMeals(String storeName, String mealsName, String newMealsName, int MealsPrice, int MealsAssess);

	// 3.搜尋特定城市找出所有店家，可以限制顯示筆數(0或null表示顯示全部店家)
	public List<FoodMapRes> findStoreByCity(String city, int num);

	// 4.搜尋店家評價幾等(含)以上並依評價高低排序
	public List<FoodMapRes> findStoreAssess(double storeAssess);

	// 5.搜尋店家評價幾等(含)以上與餐點評價幾等(含)以上 且顯示店家評價與餐點評價 與高低排序
	public List<FoodMapRes> findStoreAssessAndMealsAssess(double storeAssess, int mealsAssess);

}
