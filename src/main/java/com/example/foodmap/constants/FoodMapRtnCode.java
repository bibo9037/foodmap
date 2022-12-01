package com.example.foodmap.constants;

public enum FoodMapRtnCode {
	//成功訊息
	SUCCESSFUL("200", "Successful !!"),
	
	//不得為空、null、0的訊息
	CITY_OR_STORE_CANNOT_BE_EMPTY("400","City or store cannot be empty!!"),
	STORE_CANNOT_BE_EMPTY("400","Store cannot be empty!!"),
	MEALS_CANNOT_BE_EMPTY("400","Meals cannot be empty!!"),
	CHANG_STORE_CANNOT_BE_NULL("400","Change store cannot be null!!"),
	CHANG_MEALS_CANNOT_BE_NULL("400","Change meals cannot be null!!"),
	PRICE_CANNOT_BE_LESS_THAN_ZERO("400","Price cannot be less than zero!!"),
	
	//查詢品項已存在的訊息
	STORE_OR_MEALS_ALREADY_EXISTED("400","Store or meals already existed!!"),
	
	//查詢品項不存在的訊息
	THE_STORE_DOES_NOT_EXIST("400","The store does not exist!!"),
	STORE_OR_MEALS_NOT_EXISTED("400","Store or meals not existed!!"),
	
	//失敗的訊息
	CANCEL_CHANGES("400","Cancel changes!"),
	FIND_THE_STORE_FAILURE("400","Find the store fail!!"),
	ADD_ASSESS_FAILURE("400","Add assess fail!!"),
	CANNOT_FIND_THE_CITY("400","Cannot find the city!!"),
	SEARCH_ASSESS_ONLY_1_TO_5("400","Search assess only 1 to 5!!"),
	;


	private String code;

	private String message;

	private FoodMapRtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
