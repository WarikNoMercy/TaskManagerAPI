package kirill.task.api.service;

import kirill.task.api.model.MyUserDetails;
import kirill.task.api.model.User;
import kirill.task.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsImp implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsImp(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
