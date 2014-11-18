package com.example.mywork.util;

import com.google.gson.Gson;

/**
 * 
 * @author wf
 * 
 * @param <T>
 */
public class JsonUtils<T> {
	/**
	 * 把Json串转换为对象
	 * @param jsonstr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T JsonStrToObject(String jsonstr) {
		Gson gson = new Gson();
		T t = (T) gson.fromJson(jsonstr, this.getClass());
		return t;
	}

	/**
	 * 把对象转换为Json串
	 * @return
	 */
	public String ObjectToJsonStr() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
