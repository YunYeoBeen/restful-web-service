package com.example.restfulwebservice.user;

import com.example.restfulwebservice.Exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> findOne(@PathVariable("id") int id){
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
        // HATEOAS

        // static method로 WebMvcLinkBuilder.method를 불러오면 좀 더 간결한 코드 작성 가능
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(
                methodOn(this.getClass()).retrieveAllUsers()
        );
        // all-users라는 uri값과 연결
        entityModel.add(linkTo.withRel("all-users"));
        return entityModel;

    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user){
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
