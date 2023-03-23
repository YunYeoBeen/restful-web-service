package com.example.restfulwebservice.helloWorld;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class HelloWorldController {

    private final MessageSource messageSource;

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

    // requestheader에 accept-language가 담아서 보낸다.
    // 만약 영어권이라면 아까 resources에 만든 파일인 messages_en이 적용되고
    // 프랑스권이면 messages_fn
    // 한국권이면 messages가 적용된다.
    @GetMapping(path="/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("greeting.messages",null,locale);

    }


}
