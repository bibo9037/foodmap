package com.example.foodmap.constants;

public enum FoodMapRtnCode {
	SUCCESSFUL("200", "Add meals Successful !!"),
	ADD_STORE_FAILURE("400","Add store fail!!"),
	ADD_MEALS_FAILURE("400","Add meals fail!!"),
	ADD_ASSESS_FAILURE("400","Add assess fail!!"),
	STORE_OR_MEALS_ALREADY_EXISTED("400","Store or meals already existed!!"),
	PRICE_CANNOT_BE_LESS_THAN_ZERO("400","Price cannot be less than zero!!"),
	FIND_THE_STORE_FAILURE("400","Find the store fail!!"),
	FIND_THE_MEALS_FAILURE("400","Find the meals fail!!"),
	CITY_CANNOT_BE_EMPTY("400","City cannot be empty!!"),
	MEALS_CANNOT_BE_EMPTY("400","Meals cannot be empty!!"),
	STORE_CANNOT_BE_EMPTY("400","Store cannot be empty!!"),
	SEARCH_STORE_OR_MEALS_FAILURE("400","Search store or meals fail!!"),
	NOTHING_TO_UPDATE("400","城市與新店名皆沒有設定，不做任何改變!"),
	CHANG_STORE_CANNOT_BE_NULL("400","Change store cannot be null!!"),
	CHANG_MEALS_CANNOT_BE_NULL("400","Change meals cannot be null!!");


//	ADD_MEALS_FAILURE("500","Add meals already exists!!");

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
