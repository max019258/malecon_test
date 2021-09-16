package com.gachidata.db;

import org.junit.Before;
import org.junit.Test;

import com.gachidata.db.*;
import com.gachidata.user.*;

public class DaoTest {
	
	private DbConn daoconnection;
	private User user;
	
	@Before 
	public void setup() {
		daoconnection=new DbConn();
		user=new User();
	}
	
	
	
	  @Test public void testName() throws Exception {
	  
		  	daoconnection.insert(user); 
	  }
	 
	
	@Test
	public void join() throws Exception {
		daoconnection.select();
	}

}
