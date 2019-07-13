package com.sirotenkod.test.usersmanage.dto;

import com.sirotenkod.test.usersmanage.utils.sheet.annotation.SheetColumn;

import javax.validation.constraints.NotBlank;

public class UserDTO {
    private Long id;

    @NotBlank
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

    @SheetColumn(index = 0)
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    @SheetColumn(index = 1)
    public void setAge(Integer age) {
        this.age = age;
    }
}
