package com.example.foodmap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.foodmap.constants.FoodMapRtnCode;
import com.example.foodmap.entity.Food;
import com.example.foodmap.entity.Store;
import com.example.foodmap.service.ifs.FoodMapService;
import com.example.foodmap.vo.FoodMapReq;
import com.example.foodmap.vo.FoodMapRes;

@RestController
public class FoodMapController {

	@Autowired
	private FoodMapService foodMapService;

	// 1.寫入店家資料
	@PostMapping(value = "/api/setStore")
	public FoodMapRes setStore(@RequestBody FoodMapReq req) {
		//判斷城市或店家資料在建立時是否為空的值																		//Postman:
		if(!StringUtils.hasText(req.getCity())) {															//"city":"",
			return new FoodMapRes(FoodMapRtnCode.CITY_CANNOT_BE_EMPTY.getMessage()); //城市不可為空				//"store":""
		} else if(!StringUtils.hasText(req.getStore())) {
			return new FoodMapRes(FoodMapRtnCode.STORE_CANNOT_BE_EMPTY.getMessage());//店家不可為空
		}		
		
		// 判斷資料無誤直接存入 store
		Store store = foodMapService.setStore(req.getCity(), req.getStore());
		// 存入資料時判斷店家是否已存在
		if (store == null) {
			return new FoodMapRes(FoodMapRtnCode.STORE_OR_MEALS_ALREADY_EXISTED.getMessage());	//店家或餐點已經存在
		}
		
		// 資料存入成功並回傳訊息: SUCCESSFUL
		FoodMapRes res = new FoodMapRes(store,FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;
	}
	
	// 2.寫入餐點評價
	@PostMapping(value = "/api/setMeals")																
	public FoodMapRes setMeals(@RequestBody FoodMapReq req) {											
																										//Postman:
		 //判斷餐點評價是否小於1或大於5																		//"store":"",
		if(req.getMealsAssess() < 1 || req.getMealsAssess() > 5 ) {										//"meals":"",
			return new FoodMapRes(FoodMapRtnCode.ADD_ASSESS_FAILURE.getMessage()); //新增餐點評價失敗		//"meals_price":,
		   }														           							//"meals_assess":
		
		//判斷餐點價格是否小於0
		if(req.getMealsPrice() <= 0 ) {            
			return new FoodMapRes(FoodMapRtnCode.PRICE_CANNOT_BE_LESS_THAN_ZERO.getMessage()); //價格不可小於0
		   }
		
		// 判斷資料輸入是否正確
		FoodMapRes checkres = checkMap(req);
		if (checkres != null) {
			return checkres;
		}
		
		// 判斷資料無誤直接存入 food
		Food food = foodMapService.setMeals(req.getStore(), req.getMeals(), req.getMealsPrice(), req.getMealsAssess());
		// 存入資料時判斷餐點是否已存在
		if (food == null) {
			return new FoodMapRes(FoodMapRtnCode.STORE_OR_MEALS_ALREADY_EXISTED.getMessage()); //店家或餐點已經存在
		} 
		
		// 資料存入成功並回傳訊息: SUCCESSFUL
		FoodMapRes res = new FoodMapRes(food, FoodMapRtnCode.SUCCESSFUL.getMessage());
			return res;
	}
	
	// 主要判斷對象是PK
	private FoodMapRes checkMap(FoodMapReq req) { 	
		// 判斷使用者輸入的店家名稱是否為空的
		if (!StringUtils.hasText(req.getStore())) { 					
			return new FoodMapRes(FoodMapRtnCode.STORE_CANNOT_BE_EMPTY.getMessage()); //店家不能是空的
		// 判斷使用者輸入的餐點名稱是否為空的
		} else if (!StringUtils.hasText(req.getMeals())) { 			
			return new FoodMapRes(FoodMapRtnCode.MEALS_CANNOT_BE_EMPTY.getMessage()); //餐點不能是空的
		}
		return null;
	}


	
	// 3.搜尋特定城市找出所有店家，可以限制顯示筆數(0或null表示顯示全部店家)
	@PostMapping(value = "/api/findStoreByCity")
	public List<FoodMapRes> findStoreByCity(@RequestBody FoodMapReq req) {
		return foodMapService.findStoreByCity(req.getCity(),req.getNum());
	}

	// 4.搜尋店家評價幾等(含)以上並依評價高低排序
	@PostMapping(value = "/api/findByStoreAssessGreaterThanEqual")
	public List<Store> findByStoreAssessGreaterThanEqual(@RequestBody FoodMapReq req) {
		List<Store> store = foodMapService.findStoreAssess(req.getStoreAssess());
		return store;
	}
	
	// 5.搜尋店家評價幾等(含)以上與餐點評價幾等(含)以上 且顯示店家評價與餐點評價 與高低排序
	@PostMapping(value = "/api/findStoreAssessAndMealsAssess")
	public FoodMapRes findStoreAssessAndMealsAssess(@RequestBody FoodMapReq req) {
//		if(!StringUtils.hasText(req.getStore())) {
//			return new FoodMapRes(FoodMapRtnCode.FIND_THE_STORE_FAILURE.getMessage());
//		}		
		 FoodMapRes food = foodMapService.findStoreAssessAndMealsAssess(req.getStore(),req.getMealsAssess());
		return food;
	}
	
	
	
	//修改商店資料
	@PostMapping(value = "/api/changeInfoForStore")
	public FoodMapRes changeInfoForStore(@RequestBody FoodMapReq req) {
		
		//如果字串內沒有值 則會進入下一行 回傳失敗訊息
		if(!StringUtils.hasText(req.getStore())) {		
			return new FoodMapRes(FoodMapRtnCode.CHANG_STORE_CANNOT_BE_NULL.getMessage());
		}
		
		//如果城市與新店名不做修改則不做任何改變並進入下一行回傳訊息
		if(!StringUtils.hasText(req.getCity()) && !StringUtils.hasText(req.getNewStore())) {	
			return new FoodMapRes(FoodMapRtnCode.NOTHING_TO_UPDATE.getMessage());
		}
		Store store = foodMapService.changeInfoForStore(req.getCity(), req.getStore(), req.getNewStore());
		if(store == null) {
			return new FoodMapRes(FoodMapRtnCode.FIND_THE_STORE_FAILURE.getMessage());
		}
		
		FoodMapRes res = new FoodMapRes(store,"Change successful!");
		return res ;
	}
	
	
	//修改餐點資料
	@PostMapping(value = "/api/changeInfoforMeals")
	public FoodMapRes changeInfoforMeals(@RequestBody FoodMapReq req) {
		
		//如果字串內沒有值 則會進入下一行 回傳失敗訊息
		if(!StringUtils.hasText(req.getMeals()) || !StringUtils.hasText(req.getNewMeals())) {		
			return new FoodMapRes(FoodMapRtnCode.CHANG_MEALS_CANNOT_BE_NULL.getMessage());
		}
		
		//判斷餐點評價要不要修改(0=不修改；1~5=要修改且評分只能介於1~5之間)
		if(req.getMealsAssess() == 0 ) {
			return null;
		}else 
			if(req.getMealsAssess() < 1 || req.getMealsAssess() > 5) {//判斷要更新的餐點評價是否小於1或大於5
			return new FoodMapRes(FoodMapRtnCode.ADD_ASSESS_FAILURE.getMessage());//更新餐點評價失敗	
		}								           							
				
		//判斷餐點價格是否小於0
		if(req.getMealsPrice() == 0 ) {            
			return null;
		}else 
			if(req.getMealsPrice() < 0 ) {            
			return new FoodMapRes(FoodMapRtnCode.PRICE_CANNOT_BE_LESS_THAN_ZERO.getMessage()); 
		}
		
		Food food = foodMapService.changeInfoforMeals(req.getCity(), req.getMeals(), req.getNewMeals(), req.getMealsPrice(), req.getMealsAssess());
		
		FoodMapRes res = new FoodMapRes(food,"Change successful!");
		return res;		
	}
	
	
	
	
}
