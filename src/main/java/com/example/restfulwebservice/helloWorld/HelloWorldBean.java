package com.example.restfulwebservice.helloWorld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
// default생성자를 만들고 싶다면 아래 어노테이션 사용
@NoArgsConstructor
public class HelloWorldBean {
    private String message;


}
