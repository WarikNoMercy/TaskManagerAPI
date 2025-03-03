package kirill.task.api.service;

import kirill.task.api.model.User;
import kirill.task.api.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void userService_findByUsername(){
        User mockUser = new User();
        mockUser.setUsername("user");

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(mockUser));

        Optional<User> user = userService.findByUsername("user");

        Assertions.assertThat(user.get().getUsername()).isEqualTo("user");
    }

    @Test
    public void userService_getAllWorkers(){
        List<User> mockList = new ArrayList<>();
        User user = new User();
        user.setRole("ROLE_EMPLOYEE");
        mockList.add(user);

        when(userRepository.findEmployees()).thenReturn(mockList);

        List<User> userList = userService.getWorkers();

        Assertions.assertThat(userList.size()).isEqualTo(1);
        Assertions.assertThat(userList.get(0).getRole()).isEqualTo("ROLE_EMPLOYEE");
    }

}
