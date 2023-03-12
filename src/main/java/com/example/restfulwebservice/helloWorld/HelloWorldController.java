package com.example.restfulwebservice.helloWorld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    //GET
    // /hello-world (endpoint)
    // client로부터 url 요청이 들어오면 server단에서 처리한 후 다시 client한테 반환


    // RequestMapping(method) 리퀘스트맵핑으로 할거면 이렇게 해야한다.
    @GetMapping(path="/hello-world")
    public String helloWorld() {
        return "Hello World";
    }


    // 객체로 return을 하니깐 json 형태로 return이 나온다.
    @GetMapping(path="/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("윤여빈");
    }

    @GetMapping(path="/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable("name") String name) {
        return new HelloWorldBean(String.format("윤여빈 %s",name));
    }


}
