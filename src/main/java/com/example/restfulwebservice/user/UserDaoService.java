package com.example.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

// 여기에는 서비스 로직 작성
@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private int usersCount = 3;
    // 초기값 생성
    static{
        users.add(new User(1, "윤여빈",new Date()));
        users.add(new User(2, "이경란",new Date()));
        users.add(new User(3, "윤석영",new Date()));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User newUser) {
        if(newUser.getId() == null ){
            newUser.setId(++usersCount);
        }
        users.add(newUser);
        return newUser;
    }
    public User findOne(int id){
        for(User user:users){
            if(user.getId()==id) return user;
        }
        return null;
    }

    public User deleteOne(int id){
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            User user = iterator.next();
            if(user.getId() == id){
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
