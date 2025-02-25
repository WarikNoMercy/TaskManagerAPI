package kirill.task.api.service;

import kirill.task.api.config.JwtConfig;
import kirill.task.api.model.MyUserDetails;
import kirill.task.api.model.SigninRequest;
import kirill.task.api.model.User;
import kirill.task.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private AuthenticationManager authManager;
    private JwtConfig jwtConfig;

    @Autowired
    public UserService (UserRepository userRepository, PasswordEncoder encoder, AuthenticationManager authManager, JwtConfig jwtConfig){
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtConfig = jwtConfig;
    }


    public boolean containsUser(String username){
        return userRepository.findByUsername(username).isPresent();
    }

    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }



    public List<User> getWorkers() {
        return userRepository.findEmployees();
    }

    public String verify(SigninRequest signinRequest){
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
        if(authentication.isAuthenticated()){
            User user = findByUsername(signinRequest.getUsername()).get();
            return jwtConfig.generateToken(user);
        }else{
            return null;
        }
    }
}
