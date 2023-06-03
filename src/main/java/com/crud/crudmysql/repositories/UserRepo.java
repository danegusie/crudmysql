package com.crud.crudmysql.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.crudmysql.entities.user;

public interface UserRepo extends JpaRepository<user, Long> {
    

}
