package kirill.task.api.controller;

import kirill.task.api.config.JwtConfig;
import kirill.task.api.model.SigninRequest;
import kirill.task.api.model.User;
import kirill.task.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthConrtoller {

    private UserService userService;

    @Autowired
    public AuthConrtoller(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<String> home(Authentication authentication){
        String role = authentication.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toList()).get(0);
        String name = authentication.getName();
        return new ResponseEntity<>(name + " с ролью : " + role,HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody SigninRequest signinRequest){
        String token = userService.verify(signinRequest);
        if(token!=null){
            return new ResponseEntity<>(token, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Не удалось авторизоваться",HttpStatus.UNAUTHORIZED);
        }
    }

}
