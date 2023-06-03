package com.crud.crudmysql.entities;



import java.sql.Date;

import io.micrometer.common.lang.Nullable;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
@NamedNativeQuery(name="user.findAll_named", query="select u.Id, u.FirstName from user", resultSetMapping = "Mapping.userDTO")

@Getter
@Setter
@Entity
public class user {
    
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;

    @Column
    private String FirstName;
    
    @Column
    @Nonnull
    private String LastName;

    // @ManyToOne
    // @JoinColumn
    @Column
    private Date Age;

    public user(){}
    public user(int id, String firstName, String lastName, Date age){
        this.Id = id; this.FirstName = firstName; this.LastName = lastName; this.Age = age;
    }

    // public int getId() {
    //     return this.id;
    // }

    // public void setId(int id) {
    //     this.id = id;
    // }

    // public String getFirstName() {
    //     return this.FirstName;
    // }

    // public void setFirstName(String FirstName) {
    //     this.FirstName = FirstName;
    // }

    // public String getLastName() {
    //     return this.LastName;
    // }

    // public void setLastName(String LastName) {
    //     this.LastName = LastName;
    // }

}
