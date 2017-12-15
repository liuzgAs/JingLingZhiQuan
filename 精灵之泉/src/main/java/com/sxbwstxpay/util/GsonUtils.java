package com.sxbwstxpay.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public final class GsonUtils {

	public static <T> T parseJSON(String json, Class<T> clazz) {
		Gson gson = new Gson();
		T info = gson.fromJson(json, clazz);
		return info;
	}
	
	/**
	 * Type type = new  TypeToken<ArrayList<TypeInfo>>(){}.getType();
	   <br>Type所在的包：java.lang.reflect
	   <br>TypeToken所在的包：com.google.gson.reflect.TypeToken	
	 * @param type
	 * @return
	 */
	public static <T> T parseJSONArray(String jsonArr, Type type) {
		Gson gson = new Gson();
		T infos = gson.fromJson(jsonArr, type);
		return infos;
	}

	public static String parseObject(Object object) {
		Gson gson = new Gson();
		String json = gson.toJson(object);
		return json;
	}
	
	private GsonUtils(){}

	public static HashMap<String,String> Json2Map(String json){
		HashMap<String,String> map = new Gson().fromJson(json, new TypeToken<HashMap<String,String>>(){}.getType());
		return map;
	}
}
