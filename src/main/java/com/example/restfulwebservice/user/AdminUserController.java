package com.example.restfulwebservice.user;

import com.example.restfulwebservice.Exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {
    private final UserDaoService userDaoService;

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = userDaoService.findAll();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "password");

        // UserInfo는 JsonFilter 어노테이션이 붙은 클래스에서 선언한 이름을 의미
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filterProvider);
        return mapping;
    }

    //    @GetMapping(value = "/users/{id}/",params = "version=1")
//    @GetMapping("/v1/users/{id}")
    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    public MappingJacksonValue findOneV1(@PathVariable("id") int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filterProvider);
        return mapping;
    }

    //    @GetMapping("/v2/users/{id}")
//    @GetMapping(value="/users/{id}/", params = "version=2")
    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    public MappingJacksonValue findOneV2(@PathVariable("id") int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // user값들을 UserV2로 옮긴다.
        // BeanUtils를 사용 -> spring에서 제공하는 기능 중 하나로 bean들 간의 기능
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filterProvider);
        return mapping;
    }


}
