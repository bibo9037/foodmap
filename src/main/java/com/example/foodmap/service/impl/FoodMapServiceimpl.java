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
		if(storeDao.existsById(store)) {
			return null;
		}
		Store st = new Store(city, store);
		return storeDao.save(st);
	}

	
	// 2.寫入餐點資訊
	@Override
	public Food setMeals(String store, String meals, int mealsPrice, int mealsAssess) {
		Food_Id food_id = new Food_Id(store, meals);
		if (foodDao.existsById(food_id)) {
			return null;
		}
		Food food = new Food(store, meals, mealsPrice, mealsAssess);	//新增餐點評價時候 店家的評價更動
		foodDao.save(food);
		List<Food> fm = foodDao.findByStore(store);
		double totalAssess = 0 ;
		int count = 0 ;
		for(Food s : fm) {
			totalAssess += s.getMealsAssess();
			count++;
		}
		
		double storeAssess = totalAssess/count;
		storeAssess = Math.round(totalAssess/count * 10.0) / 10.0;		//四捨五入至小數點後 1 位
		
		Optional<Store> st = storeDao.findById(store);
		st.get().setStoreAssess(storeAssess);
		storeDao.save(st.get());
			return food;	
		}

	
	// 3.搜尋特定城市找出所有店家，可以限制顯示筆數(0或null表示顯示全部店家)
	@Override
	public List<FoodMapRes> findStoreByCity(String city, int num) {
		List<FoodMapRes> resList = new ArrayList<FoodMapRes>();
		List<Store> storeList = storeDao.findByCity(city); //存放相同城市的商店
		  //資料型態 變數名稱 : 比對對象
		for(Store store : storeList)  {
			List<Food> foodList = foodDao.findByStore(store.getStore());
			FoodMapRes res = new FoodMapRes();
			res.setMealsList(foodList); 								//把foodList的資料set進res的Mealslist裡
			res.setCity(store.getCity());
		}
		return null;
	}

	
	// 4.搜尋店家評價幾等(含)以上並依評價高低排序
	@Override
	public List<Store> findStoreAssess(double storeAssess) {
		List<Store> store = storeDao.findByStoreAssessGreaterThanEqual(storeAssess);
		return store;
	}

	
	// 5.搜尋店家評價幾等(含)以上與餐點評價幾等(含)以上 且顯示店家評價與餐點評價 與高低排序
	@Override
	public FoodMapRes findStoreAssessAndMealsAssess(String store, int mealsAssess) {
		FoodMapRes res = new FoodMapRes();
		List<Store> st = storeDao.findByStore(store);
		List<Food> fo = foodDao.findByMealsAssess(mealsAssess);
		List<Store> pa = new ArrayList<>();

		for (Store assess : st) {
			pa.addAll(storeDao.findByStoreAssess(assess.getStoreAssess()));
		}
		res.setMealsList(fo);
		res.setStoreList(st); 
		return res;
	}

	
	//修改商店資料
	@Override
	public Store changeInfoForStore(String city, String store, String newStore) {
//		Store store1 = new Store();
//		Optional<Store> storeOp =  storeDao.findById(store);
//		store1 = storeOp.get();
		Store store1 = storeDao.findById(store).get();		//前面三行的濃縮結果
		storeDao.delete(store1);
		if(store1 == null) {
			return null;
		}
		
		if(StringUtils.hasText(city)) {
			store1.setCity(city);	
		}
		
		if(StringUtils.hasText(newStore)) {
			store1.setStore(newStore);
			List<Food> foodList = foodDao.findByStore(store);		//取出相同店名的store
			
			for(Food food1 : foodList) {
				foodDao.delete(food1);
				food1.setStore(newStore);		//設定新的店名進去
				foodDao.save(food1);
			}
		}		
		storeDao.save(store1);
		return store1;
	}

	
	//修改餐點資料
	@Override
	public Food changeInfoforMeals(String store, String meals, String newMeals, int mealsPrice, int mealsAssess) {
		Food_Id foodId = new Food_Id(store, meals);
//		Optional<Food> foodOp = foodDao.findById(foodId);			//建立food並存放資料庫中撈到的餐點資料
//		Food food1 = foodOp.get();									//撈到的餐點會存放在這裡
		Food food1 = foodDao.findById(foodId).get();
//		foodDao.delete(food1);
		//接著判斷要修改的餐點存不存在
//		if(food1 == null) {		
//			return null;
//		}

		
		if (foodDao.existsById(foodId)) {
			return null;
		}
		
		if(StringUtils.hasText(newMeals)) {		//如果newMeals判斷有輸入值，表示需要修改餐點	
			food1.setMeals(newMeals);
		}
		
//		if(mealsPrice > 0 ) {					//如果MealsPrice不等於0(值判斷為空的值)，代表不修改
//			food1.setMealsPrice(mealsPrice);
//		}
			
			
		if(mealsAssess > 0 ) {
			food1.setMealsAssess(mealsAssess);
			List<Food> fm = foodDao.findByStore(store);
			double totalAssess = 0 ;
			int count = 0 ;
			for(Food s : fm) {
				totalAssess += s.getMealsAssess();
				count++;
			}
		
			double storeAssess = (totalAssess + mealsAssess) / (count + 1);
			storeAssess = Math.round(totalAssess/count * 10.0) / 10.0;		//四捨五入至小數點後 1 位
		
			Optional<Store> st = storeDao.findById(store);
			st.get().setStoreAssess(storeAssess);
			storeDao.save(st.get());
		}
		
		return foodDao.save(food1);
	}
}