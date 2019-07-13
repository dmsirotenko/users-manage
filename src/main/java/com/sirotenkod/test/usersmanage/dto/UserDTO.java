package com.sirotenkod.test.usersmanage.dto;

import com.sirotenkod.test.usersmanage.utils.sheet.annotation.SheetColumn;

import javax.validation.constraints.NotBlank;

public class UserDTO {
    private Long id;

    @NotBlank
    @SheetColumn(index = 0)
    private String name;

    @SheetColumn(index = 1)
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
