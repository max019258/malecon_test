package com.gachidata.db;

import java.util.HashMap;
import java.util.Map;
import com.gachidata.user.*;

public class Db {
	private static Map<String,User> users=new HashMap<String,User>(); //¸Ê »ı¼º
	
	public static Map<String, User> getUsers() {
		return users;
	}

	public static void addUser(User user) {
		users.put(user.getName(),user);
	}
	
}
