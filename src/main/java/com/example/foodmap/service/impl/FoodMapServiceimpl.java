package com.example.foodmap.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.foodmap.entity.Food;
import com.example.foodmap.entity.Food_Id;
import com.example.foodmap.entity.Store;
import com.example.foodmap.repository.FoodDao;
import com.example.foodmap.repository.StoreDao;
import com.example.foodmap.service.ifs.FoodMapService;
import com.example.foodmap.vo.FoodMapRes;

@Service
public class FoodMapServiceimpl implements FoodMapService {

	@Autowired
	private FoodDao foodDao;

	@Autowired
	private StoreDao storeDao;

	// 1.寫入商店資訊
	@Override
	public Store setStore(String city, String store) {
		if (storeDao.existsById(store)) { 											// 使用existsById 判斷資料庫中是否已存在相同的store
			return null;															// 若沒有則回傳null
		}
		Store sto = new Store(city, store);											// 設定store有變數city和store
		return storeDao.save(sto); 													// 使用storeDao的save方法存回sto 並回傳
	}

	// 1-1.修改商店資料
	@Override
	public Store changeInfoForStore(String city, String store, String newStore) {
//		Store store1 = new Store();													// 設定變數store1為一個新的Store
//		Optional<Store> storeOp = storeDao.findById(store);							// 設定Store資料型態的Optional(storeOp)存放透過storeDao的findById方法取得的store
//		store1 = storeOp.get();														// store1存放透過storeOp的get方法取出的東西
		Store store1 = storeDao.findById(store).get(); 								// 前面三行的濃縮結果，所以store1裡面放的是findById中取出的store
		storeDao.delete(store1);													// 使用storeDao中的delete方法刪除store1中的資料

		if (store1 == null) {														// 判斷store1是否為null
			return null;															// 若是則回傳null
		}

		if (StringUtils.hasText(city)) {											// 判斷變數city是否有值
			store1.setCity(city);													// 若有，則將值設定進store1的city
		}

		if (StringUtils.hasText(newStore)) {										// 判斷變數newStore是否有值
			store1.setStore(newStore);												// 若有，則將值設定進store1的newStore
			List<Food> foodList = foodDao.findByStore(store); 						// 設定Food資型態的List(foodList)存放透過foodDao的findByStire中取出相同店名的store

			for (Food food1 : foodList) { 											// 設定Food資料型態的變數food1，比對與foodList中相同店名的store並放入food1中
				foodDao.delete(food1);												// 使用foodDao的delete方法刪除放在food1中的store
				food1.setStore(newStore);											// 設定新的店名為變數newStore
				foodDao.save(food1);												// 使用foodDao的save方法存回food1
			}
		}
		storeDao.save(store1);														// 使用storeDao的save方法存回store1
		return store1;																// 回傳結果store1
	}

	// 2.寫入餐點資訊
	@Override
	public Food setMeals(String store, String meals, int mealsPrice, int mealsAssess) {
		Food_Id food_id = new Food_Id(store, meals);								// 設定Food_Id有變數store和meals

		// 如果找不到store就不能繼續往下做
		if (store.isEmpty()) { 														// 使用isEmpty 判斷store內有沒有店家
			return null; 															// 若沒有則回傳null
		}

		if (foodDao.existsById(food_id)) { 											// 判斷是否已有相同的store或是meals
			return null; 															// 若沒有則回傳null
		}

		Food food = new Food(store, meals, mealsPrice, mealsAssess); 				// 新增餐點評價時候 店家的評價更動
		foodDao.save(food);															// 透過foodDao的save方法存回food
		List<Food> foodList = foodDao.findByStore(store); 							// 設定Food資料型態的List(foodList)存放透過foodDao的findByStore方法取得的store
		double totalAssess = 0; 													// 設定一個為0的餐點總評價變數
		int count = 0; 																// 設定一個為0的品項變數
		for (Food food1 : foodList) {												// 設定變數food1存放符合條件的store
			totalAssess += food1.getMealsAssess(); 									// 從food1中取得的MealsAssess放進totalAssess並做餐點評價的加總
			count++;																// 有MealsAssess放入totalAssess就+1
		}

		double storeAssess = totalAssess / count; 									// 設定變數storAssess存放totalAssess÷count後的值(餐點總評價)
		storeAssess = Math.round(totalAssess / count * 10.0) / 10.0; 				// 變數storeAssess透過Math的round方法取四捨五入至小數點後 1 位的值

		Optional<Store> storeOp = storeDao.findById(store); 						// 設定Store資料型態的Optional(storeOp)存放透過storeDao的findById方法取得的store
		storeOp.get().setStoreAssess(storeAssess);									// 使用storeOp的get方法的setStoreAssess方法設定變數storeAssess
		storeDao.save(storeOp.get());												// 使用storeDao的save方法存回storeOp的get方法取得的東西
		return food;
	}


	// 2-2.修改餐點資料
	@Override
	public Food changeInfoforMeals(String store, String meals, String newMeals, int mealsPrice, int mealsAssess) {
		Food_Id food_Id = new Food_Id(store, meals);								// 設定Food_Id有變數store和meals
		Optional<Food> foodOp = foodDao.findById(food_Id);							// 建立food並存放資料庫中撈到的餐點資料 設定Food資料型態的Optional(foodOp)存放透過foodDao的findById方法取得的food_Id

		// 接著判斷要修改的餐點存不存在
		if (foodOp.orElse(null) == null) { 											// 當Optional的內容物判斷為null時，回傳null
			return null;
		}
		Food food1 = foodOp.get(); 													// 設定Food資料型態的food1存放foodOp的get方法取得的東西
		foodDao.delete(food1); 														// 使用foodDao的delete方法刪除food1中原本的東西

		if (StringUtils.hasText(newMeals)) { 										// 判斷newMeals是否有值，有-->表示需要修改meals；沒有-->不需修改meals
			food1.setMeals(newMeals); 												// 若有，則將值設定進food1的newMeals(存入新修改的餐點名稱)
		}

		if (mealsPrice > 0) { 														// 判斷mealsPrice的值是否大於0，大於0-->存入；若為0則表示不須修改
			food1.setMealsPrice(mealsPrice);										// 若值大於0，則將值設定進food1的mealsPrice(覆蓋原本的mealsPrice)
		}

		if (mealsAssess > 0) {														// 判斷mealsAssess的值是否大於0-->存入；若為0則表示不須修改
			food1.setMealsAssess(mealsAssess);										// 若值大於0，則將值設定進food1的mealsAssess(覆蓋原本的mealsAssess)
			List<Food> foodList = foodDao.findByStore(store);						// 設定Food資料型態的List(foodList)存放透過foodDao的findByStore方法取得的store
			double totalAssess = 0;													// 設定一個為0的餐點總評價變數
			int count = 0;															// 設定一個為0的品項變數
			for (Food food : foodList) {											// 設定變數food存放符合條件的store
				totalAssess += food.getMealsAssess();								// 從food中取得的MealsAssess放進totalAssessu並做餐點評價的加總
				count++;															// 有MealsAssess放入totalAssess就+1
			}

			double storeAssess = (totalAssess + mealsAssess) / (count + 1);			// 設定變數storAssess存放totalAssess加上新增的mealsAssess(若有修改評價才加)÷count(若有修改評價才加1)後的值(餐點總評價)
			storeAssess = Math.round(storeAssess * 10.0) / 10.0; 					// 變數storeAssess為透過Math的round方法取四捨五入至小數點後 1 位的值

			Optional<Store> storeOp = storeDao.findById(store);						// 設定Store資料型態的Optional(storeOp)存放透過storeDao的findById方法取得的store
			Store store1 = storeOp.get();											// 設定Store資料型態的stroe1存放storeOp的get方法取得的東西
			store1.setStoreAssess(storeAssess);										// 將變數storeAssess設定進store1的StoreAssess
			storeDao.save(store1);													// 使用storeDao的save方法存回store1
		}

		return foodDao.save(food1);													// 回傳food1的結果並存回
	}

	// 3.搜尋特定城市找出所有店家，可以限制顯示筆數(0或null表示顯示全部店家)
	@Override
	public List<FoodMapRes> findStoreByCity(String city, int num) {
		List<FoodMapRes> resList = new ArrayList<FoodMapRes>();						// 設定新的FoodMapRes資料型態的List(resList)
		List<Store> storeList = storeDao.findByCity(city);							// 設定Store資料型態的List(storeList)存放透過storeDao的findByCity方法取得的city
//		
		if (storeList.isEmpty()) { 													// 使用isEmpty判斷storeList內是否有該城市，如果沒有則回傳null
			return null;
		}

		if (num > storeList.size() || num == 0) {									// 設定變數num限制顯示筆數，如果要顯示的筆數大於(或等於0)storeList內的資料長度
			num = storeList.size(); 												// num則會自動轉為跟List長度一樣
		}
		storeList.subList(0, num);													// 使用storeList的subList方法設定要抓取List中的數量
		// 資料型態 變數名稱 : 比對對象
		for (Store store : storeList) {
			List<Food> foodList = foodDao.findByStore(store.getStore());			// 設定Food資料型態的List(foodList)存放透過foodDao的findByStore方法取得變數store中的store
			FoodMapRes res = new FoodMapRes();										// 設定新的FoodMapRes資料型態的res
			res.setMealsList(foodList); 											// 將foodList的資料設定進res的MealsList
			res.setStore(store);
			resList.add(res); 														// 將res的資料存入resList內
		}
		return resList;
	}

	// 4.搜尋店家評價幾等(含)以上並依評價高低排序
	@Override
	public List<FoodMapRes> findStoreAssess(double storeAssess) {
		List<FoodMapRes> resList = new ArrayList<FoodMapRes>();						// 設定FoodMapRes資料型態的List(resList)
		List<Store> storeList = storeDao.findByStoreAssessGreaterThanEqual(storeAssess);// 設定Store資料型態的List存放透過storeDao的方法取得storeAssess
		
		// 用foreach找到店家名稱，再取得該店家的所有餐點
		for (Store store : storeList) {
			List<Food> foodList = foodDao.findByStore(store.getStore());			// 設定Food資料型態的List(foodList)存放透過foodDao的findByStore方法取得變數store的store
			FoodMapRes res = new FoodMapRes();										// 設定新的FoodMapRes資料型態的res
			res.setStore(store);													// 將store的資料設定進res的Store
			res.setMealsList(foodList);												// 將foodList的資料設定進res的MealsList
			resList.add(res);														// 將res的資料存入resList
		}
		return resList;
	}

	// 5.搜尋店家評價幾等(含)以上與餐點評價幾等(含)以上 且顯示店家評價與餐點評價 與高低排序
	@Override
	public List<FoodMapRes> findStoreAssessAndMealsAssess(double storeAssess, int mealsAssess) {
		List<FoodMapRes> resList = new ArrayList<FoodMapRes>();						// 設定FoodMapRes資料型態的List(resList)

		List<Store> storeList = storeDao.findByStoreAssessGreaterThanEqual(storeAssess);// 設定Store資料型態的List(storeList)存放透過storeDao的方法取得storeAssess
		for (Store store : storeList) {
			List<Food> foodList = foodDao											// 設定Food資料型態的List(foodList)存放透過foodDao的方法取得變數store的store和mealsAssess
					.findByStoreAndMealsAssessGreaterThanEqualOrderByMealsAssessDesc(store.getStore(), mealsAssess);
			FoodMapRes res = new FoodMapRes();										// 設定新的FoodMapRes資料型態的res
			res.setMealsList(foodList);												// 將foodList的資料設定進res的MealsList
			res.setStore(store);													// 將store的資料設定進res的Store
			resList.add(res);														// 將res的資料存入resList
		}
		return resList;																// 回傳resList
	}

}