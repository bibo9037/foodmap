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
import com.example.foodmap.vo.FoodMapResponseList;

@RestController
public class FoodMapController {

	@Autowired
	private FoodMapService foodMapService;

	// 1.寫入店家資料
	@PostMapping(value = "/api/setStore")
	public FoodMapRes setStore(@RequestBody FoodMapReq req) {
		//判斷城市或店家資料在建立時是否為空的值																		
		if(!StringUtils.hasText(req.getCity())) {															
			return new FoodMapRes(FoodMapRtnCode.CITY_CANNOT_BE_EMPTY.getMessage()); //若req.getCity為空值，回傳訊息:設定的城市不可為空				
		} else if(!StringUtils.hasText(req.getStore())) {
			return new FoodMapRes(FoodMapRtnCode.STORE_CANNOT_BE_EMPTY.getMessage());//若req.getStore為空值，回傳訊息:設定的店家不可為空
		}		
		
		// 判斷資料無誤直接存入變數 store
		Store store = foodMapService.setStore(req.getCity(), req.getStore());
		// 存入資料時判斷店家是否已存在
		if (store == null) {
			return new FoodMapRes(FoodMapRtnCode.STORE_OR_MEALS_ALREADY_EXISTED.getMessage());	//若資料已存在，回傳訊息:設定的店家或餐點已經存在
		}
		
		// 資料存入成功並回傳訊息: SUCCESSFUL
		FoodMapRes res = new FoodMapRes(store,FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;
	}
	
	// 1-1.修改商店資料
		@PostMapping(value = "/api/changeInfoForStore")
		public FoodMapRes changeInfoForStore(@RequestBody FoodMapReq req) {
			
			//如果字串內沒有值 則會進入下一行 回傳失敗訊息
			if(!StringUtils.hasText(req.getStore())) {		
				return new FoodMapRes(FoodMapRtnCode.CHANG_STORE_CANNOT_BE_NULL.getMessage());
			}
			
			//如果城市與新店名不做修改則不做任何改變並進入下一行回傳訊息
			if(!StringUtils.hasText(req.getCity()) && !StringUtils.hasText(req.getNewStore())) {	
				return new FoodMapRes(FoodMapRtnCode.NOTHING_HAPPENED.getMessage());
			}
			
			Store store = foodMapService.changeInfoForStore(req.getCity(), req.getStore(), req.getNewStore());
			if(store == null) {
				return new FoodMapRes(FoodMapRtnCode.FIND_THE_STORE_FAILURE.getMessage());
			}
			
			FoodMapRes res = new FoodMapRes(store,"Change successful!");
			return res ;
		}
		
	
	// 2.寫入餐點評價
	@PostMapping(value = "/api/setMeals")																
	public FoodMapRes setMeals(@RequestBody FoodMapReq req) {											
																										
		//判斷寫入餐點的店家是否存在
		if(!StringUtils.hasText(req.getCity())) {															
			return new FoodMapRes (FoodMapRtnCode.THE_STORE_DOES_NOT_EXIST.getMessage());
		}
												
		 //判斷餐點評價是否小於1或大於5																		
		if(req.getMealsAssess() < 1 || req.getMealsAssess() > 5 ) {										
			return new FoodMapRes(FoodMapRtnCode.ADD_ASSESS_FAILURE.getMessage()); //新增餐點評價失敗		
		   }														           							
		
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
	
	// 2-2.修改餐點資料
	@PostMapping(value = "/api/changeInfoforMeals")
	public FoodMapRes changeInfoforMeals(@RequestBody FoodMapReq req) {
				
		//如果字串內沒有值 則會進入下一行 回傳失敗訊息
		if(!StringUtils.hasText(req.getMeals()) || !StringUtils.hasText(req.getStore())) {		
			return new FoodMapRes(FoodMapRtnCode.CHANG_MEALS_CANNOT_BE_NULL.getMessage());
		}
		
		//判斷餐點評價要不要修改(0=不修改；1~5=要修改且評分只能介於1~5之間)
		if(req.getMealsAssess() < 0 || req.getMealsAssess() > 5) {//判斷要更新的餐點評價是否小於1或大於5
			return new FoodMapRes(FoodMapRtnCode.ADD_ASSESS_FAILURE.getMessage());//更新餐點評價失敗	
		}								           							
				
		//判斷餐點價格是否小於0 
		if(req.getMealsPrice() < 0 ) {            
			return new FoodMapRes(FoodMapRtnCode.PRICE_CANNOT_BE_LESS_THAN_ZERO.getMessage()); 
		}
		
		Food food = foodMapService.changeInfoforMeals(req.getStore(), req.getMeals(), req.getNewMeals(), req.getMealsPrice(), req.getMealsAssess());
		if(food == null) {
			return new FoodMapRes(FoodMapRtnCode.STORE_OR_MEALS_ALREADY_EXISTED.getMessage());
		}
		
		FoodMapRes res = new FoodMapRes(food,"Change successful!");
		return res;		
	}
	
	
	// 3.搜尋特定城市找出所有店家，可以限制顯示筆數(0或null表示顯示全部店家)
	@PostMapping(value = "/api/findStoreByCity")
	public FoodMapResponseList findStoreByCity(@RequestBody FoodMapReq req) {
		
		//防呆 1.city不能為空值
		if(!StringUtils.hasText(req.getCity())) {															
			return new FoodMapResponseList(FoodMapRtnCode.CITY_CANNOT_BE_EMPTY.getMessage());
		}
		FoodMapResponseList classResList = new FoodMapResponseList();
		List<FoodMapRes> resList = foodMapService.findStoreByCity(req.getCity(), req.getNum());
		
		//防呆 2.判斷城市是否存在，若回null則回傳找不到該城市的訊息
		if(resList == null) {		
			return new FoodMapResponseList(FoodMapRtnCode.CANNOT_FIND_THE_CITY.getMessage());
		}
		
		classResList.setResList(resList);
		classResList.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());
		
		return classResList;
	}

	// 4.搜尋店家評價幾等(含)以上並依評價高低排序
	@PostMapping(value = "/api/findByStoreAssessGreaterThanEqual")
	public FoodMapRes findByStoreAssessGreaterThanEqual(@RequestBody FoodMapReq req) {
		
		//設置判斷店家的評價設定在1~5之間的防呆
		if(req.getStoreAssess() < 1 || req.getStoreAssess() > 5) {									//判斷要查詢的店家評價是否小於0或大於5
			return new FoodMapRes(FoodMapRtnCode.SEARCH_ASSESS_ONLY_1_TO_5.getMessage());			//因為搜尋的店家評價不在範圍內	
		}
		FoodMapRes res = new FoodMapRes();	
		List<FoodMapRes> assessList = foodMapService.findStoreAssess(req.getStoreAssess());
		res.setAssessList(assessList);
		res.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;
	}
	
	// 5.搜尋店家評價幾等(含)以上與餐點評價幾等(含)以上 且顯示店家評價與餐點評價 與高低排序
	@PostMapping(value = "/api/findStoreAssessAndMealsAssess")
	public FoodMapRes findStoreAssessAndMealsAssess(@RequestBody FoodMapReq req) {
		
		//設置判斷店家的評價設定在1~5之間的防呆
		if(req.getStoreAssess() < 1 || req.getStoreAssess() > 5) {									//判斷要查詢的店家評價是否小於0或大於5
			return new FoodMapRes(FoodMapRtnCode.SEARCH_ASSESS_ONLY_1_TO_5.getMessage());			//因為搜尋的店家評價不在範圍內	
		}
		
		//設置判斷餐點的評價設定在1~5之間的防呆
		if(req.getMealsAssess() < 1 || req.getMealsAssess() > 5) {									//判斷要查詢的餐點評價是否小於0或大於5
			return new FoodMapRes(FoodMapRtnCode.SEARCH_ASSESS_ONLY_1_TO_5.getMessage());			//因為搜尋的餐點評價不在範圍內	
		}
		
		FoodMapRes res = new FoodMapRes();	
		List<FoodMapRes> assess = foodMapService.findStoreAssessAndMealsAssess(req.getStoreAssess(),req.getMealsAssess());
		res.setAssessList(assess);
		res.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;
	}
}
