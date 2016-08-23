package com.wlh.wpd.service;

import org.springframework.stereotype.Component;

@Component
public class UserService {
	
	public UserService(){
        System.out.println("UserService Constructor...\n\n\n\n\n\n");
    }
    
    public void save(){
        System.out.println("save");
    }

}
