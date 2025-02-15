package kirill.task.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApiConrtoller {



    @GetMapping("/")
    public ResponseEntity<String> home(Authentication authentication){
        String role = authentication.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toList()).get(0);
        String name = authentication.getName();
        return new ResponseEntity<>(name + " с ролью : " + role,HttpStatus.OK);
    }

}
