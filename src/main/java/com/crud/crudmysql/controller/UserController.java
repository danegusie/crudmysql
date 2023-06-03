package com.crud.crudmysql.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.crudmysql.dto.UserDto;
import com.crud.crudmysql.entities.user;
import com.crud.crudmysql.repositories.UserRepo;
import com.crud.crudmysql.services.UserService;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;


@Data
@RestController
public class UserController {

    @Autowired
    UserRepo userRepo;

    // @Autowired
    // UserService userService;

    @Bean
	public UserService userService(){
		return new UserService();
	}
    
    @GetMapping("/allusers")
    public List<UserDto> getUser(){

        List<user> users =  userRepo.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for (user user : users) {
            userDtoList.add( userService().convertToUserDto(user)  ) ;         
        }
        return userDtoList;
    }

    @PostMapping("/adduser")
    public user addUser(@RequestBody UserDto newUserDto){
        // user user =  new user(1,"ww","ww");
        // user user2 = new user();
        // user.setFirstName("daniel");
        // user.setLastName("negusie");

        user user = userService().convertToUser(newUserDto) ;
        user.setAge(Date.valueOf("2020-10-10"));
        return userRepo.save(user);
    }
}
