package com.example.restfulwebservice.user;

import com.example.restfulwebservice.Exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") int id){
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);

    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user){
        User newUser = userDaoService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") int id){
        User user = userDaoService.deleteOne(id);
        if(user == null) {
            throw new UserNotFoundException(String.format("[%s]는 삭제할 수 없습니다.",id));
        }
    }
}
