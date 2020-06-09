package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private String department;

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
