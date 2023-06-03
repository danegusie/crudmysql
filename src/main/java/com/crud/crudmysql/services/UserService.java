package com.crud.crudmysql.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.crud.crudmysql.dto.UserDto;
import com.crud.crudmysql.entities.user;
import com.crud.crudmysql.repositories.UserRepo;


public class UserService {
    
    @Autowired
    UserRepo userRepo;
    // @Autowired
    // ModelMapper modelMapper;

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }


    @GetMapping("/allusers")
    public List<user> getUser(){
        // Iterable<user> userList =  userRepo.findAll();

        List<user> users =  userRepo.findAll();

        return users;
    }

    @PostMapping("/adduser")
    public user addUser(user newUser){
        // user user =  new user(1,"ww","ww");
        // user user2 = new user();
        // user.setFirstName("daniel");
        // user.setLastName("negusie");
        return userRepo.save(newUser);
    }

    public UserDto convertToUserDto(user user){

        // modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        UserDto userDto = new UserDto();
        userDto = modelMapper().map(user, UserDto.class);
        return userDto;
    }
    public user convertToUser(UserDto userDto){

        // modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper().map(userDto, user.class);
 
     }
}
