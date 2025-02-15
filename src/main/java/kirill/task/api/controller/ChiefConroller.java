package kirill.task.api.controller;

import jakarta.annotation.PostConstruct;
import kirill.task.api.model.User;
import kirill.task.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chief")
public class ChiefConroller {

    private UserService userService;

    @Autowired
    public ChiefConroller(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/add-user")
    //@PreAuthorize("hasRole('CHIEF')")
    public ResponseEntity<String> addUser(@RequestBody User user){
        if(userService.containsUser(user.getUsername())){
            return new ResponseEntity<>("Пользователь: " + user.getUsername() + " уже существует.", HttpStatus.BAD_REQUEST);
        }else{
            userService.addUser(user);
            return new ResponseEntity<>("Пользователь: " + user.getUsername() + " успешно создан", HttpStatus.CREATED);
        }
    }
}
