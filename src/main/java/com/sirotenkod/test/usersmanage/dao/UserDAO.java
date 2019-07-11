package com.sirotenkod.test.usersmanage.dao;

import javax.persistence.*;

@Entity
public class UserDAO {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
